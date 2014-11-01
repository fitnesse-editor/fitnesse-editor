package fitedit.ui.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import fitedit.ui.editors.syntaxrules.FitSourcePartitionScanner;

public class FitDocumentProvider extends FileDocumentProvider {

    @Override
    protected IDocument createDocument(Object element) throws CoreException {
        IDocument document = super.createDocument(element);
        if (document != null) {
            IDocumentPartitioner partitioner = new FastPartitioner(new FitSourcePartitionScanner(), new String[] {
                    IDocument.DEFAULT_CONTENT_TYPE, FitSourcePartitionScanner.FIT_COMMENT,
                    FitSourcePartitionScanner.FIT_INCLUDE, FitSourcePartitionScanner.FIT_TABLE,
                    FitSourcePartitionScanner.FIT_TABLE_HEADER }) {

                @Override
                public void connect(IDocument document, boolean delayInitialization) {
                    super.connect(document, delayInitialization);
                    printPartitions(document);
                }

                public void printPartitions(IDocument document) {
                    StringBuffer buffer = new StringBuffer();

                    ITypedRegion[] partitions = computePartitioning(0, document.getLength());
                    for (int i = 0; i < partitions.length; i++) {
                        try {
                            buffer.append("Partition type: " + partitions[i].getType() + ", offset: "
                                    + partitions[i].getOffset() + ", length: " + partitions[i].getLength());
                            buffer.append("\n");
                            buffer.append("Text:\n");
                            buffer.append(document.get(partitions[i].getOffset(), partitions[i].getLength()));
                            buffer.append("\n---------------------------\n\n\n");
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(buffer);
                }
            };
            partitioner.connect(document);
            document.setDocumentPartitioner(partitioner);
        }
        return document;
    }
}