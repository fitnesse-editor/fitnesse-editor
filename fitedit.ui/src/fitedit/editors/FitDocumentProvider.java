package fitedit.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import fitedit.editors.syntaxrules.FitSourcePartitionScanner;

public class FitDocumentProvider extends FileDocumentProvider {

    @Override
    protected IDocument createDocument(Object element) throws CoreException {
        IDocument document = super.createDocument(element);
        if (document != null) {
            IDocumentPartitioner partitioner = new FastPartitioner(new FitSourcePartitionScanner(), new String[] {
                    IDocument.DEFAULT_CONTENT_TYPE, FitSourcePartitionScanner.FIT_COMMENT,
                    FitSourcePartitionScanner.FIT_INCLUDE, FitSourcePartitionScanner.FIT_TABLE_HEADER });
            partitioner.connect(document);
            document.setDocumentPartitioner(partitioner);
        }
        return document;
    }
}