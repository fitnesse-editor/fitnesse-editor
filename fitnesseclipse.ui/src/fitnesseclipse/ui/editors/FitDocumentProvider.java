package fitnesseclipse.ui.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.slf4j.Logger;

import fitnesseclipse.logging.LoggerFactory;
import fitnesseclipse.ui.editors.syntaxrules.FitSourcePartitionScanner;

public class FitDocumentProvider extends FileDocumentProvider {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected IDocument createDocument(Object element) throws CoreException {
        IDocument document = super.createDocument(element);
        if (document != null) {
            IDocumentPartitioner partitioner = new FastPartitioner(new FitSourcePartitionScanner(), new String[] {
                    IDocument.DEFAULT_CONTENT_TYPE, FitSourcePartitionScanner.FIT_COMMENT,
                    FitSourcePartitionScanner.FIT_DEFINE, FitSourcePartitionScanner.FIT_INCLUDE,
                    FitSourcePartitionScanner.FIT_FIXTURE, FitSourcePartitionScanner.FIT_TABLE }) {

                @Override
                public void connect(IDocument document, boolean delayInitialization) {
                    super.connect(document, delayInitialization);
                    printPartitions(document);
                }

                public void printPartitions(IDocument document) {
                    if (logger.isTraceEnabled()) {
                        StringBuffer buffer = new StringBuffer();
                        ITypedRegion[] partitions = computePartitioning(0, document.getLength());
                        for (ITypedRegion partition : partitions) {
                            try {
                                buffer.append("Partition type: " + partition.getType() + ", offset: "
                                        + partition.getOffset() + ", length: " + partition.getLength());
                                buffer.append("\n");
                                buffer.append("Text:\n");
                                buffer.append(document.get(partition.getOffset(), partition.getLength()));
                                buffer.append("\n---------------------------\n\n\n");
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                        }
                        logger.trace(buffer.toString());
                    }
                }
            };
            partitioner.connect(document);
            document.setDocumentPartitioner(partitioner);
        }
        return document;
    }
}