package fitedit.ui.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import fitedit.ui.editors.hyperlinks.ClassHyperlink;
import fitedit.ui.editors.hyperlinks.PageHyperlink;
import fitedit.ui.editors.syntaxrules.FitSourcePartitionScanner;

public class FitnesseLinkDetector extends AbstractHyperlinkDetector {

    @Override
    public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
        IDocument document = textViewer.getDocument();
        int regionOffset = region.getOffset();
        ITypedRegion partition = document.getDocumentPartitioner().getPartition(regionOffset);
        int partitionOffset = partition.getOffset();

        IHyperlink hyperlink = null;
        try {
            if (FitSourcePartitionScanner.FIT_TABLE.equals(partition.getType())) {
                String source = document.get(partitionOffset, partition.getLength());
                String extractFqdn = extractFqdn(source);

                int min = partitionOffset + 2;
                int max = partitionOffset + extractFqdn.length();
                if (withinRange(min, max, regionOffset)) {
                    // TODO check class exists
                    hyperlink = new ClassHyperlink(extractFqdn, min, extractFqdn.length());
                }
            } else if (FitSourcePartitionScanner.FIT_INCLUDE.equals(partition.getType())) {
                String source = document.get(partitionOffset, partition.getLength()).trim();
                int len = "!include ".length();
                String page = source.substring(len);

                int min = partitionOffset + len;
                int max = partitionOffset + source.length();
                if (withinRange(min, max, regionOffset)) {
                    // TODO check page exists
                    hyperlink = new PageHyperlink(page, min, source.length() - len);
                }
            }
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hyperlink == null ? null : new IHyperlink[] { hyperlink };
    }

    private boolean withinRange(int min, int max, int offset) {
        return offset >= min && offset <= max;
    }

    private String extractFqdn(String source) {
        return source.replaceAll("[\\r\\n]+", "").split("\\|\\|")[0].substring(2);
    }
}
