package fitedit.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import fitedit.editors.syntaxrules.FitSourcePartitionScanner;
import fitedit.ui.hyperlinks.ClassHyperlink;
import fitedit.ui.hyperlinks.PageHyperlink;

public class FitnesseLinkDetector extends AbstractHyperlinkDetector {

    @Override
    public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
        IDocument document = textViewer.getDocument();
        ITypedRegion partition = document.getDocumentPartitioner().getPartition(region.getOffset());

        IHyperlink hyperlink = null;
        try {
            if (FitSourcePartitionScanner.FIT_TABLE_HEADER.equals(partition.getType())) {
                String source = document.get(partition.getOffset(), partition.getLength());
                String fqdn = source.substring(2, source.length() - 1);
                hyperlink = new ClassHyperlink(fqdn, partition.getOffset() + 2, partition.getLength() - 3);
            } else if (FitSourcePartitionScanner.FIT_INCLUDE.equals(partition.getType())) {
                String source = document.get(partition.getOffset(), partition.getLength()).trim();
                int len = "!include ".length();
                String page = source.substring(len);
                hyperlink = new PageHyperlink(page, partition.getOffset() + len, partition.getLength() - len);
            }
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hyperlink == null ? null : new IHyperlink[] { hyperlink };
    }
}
