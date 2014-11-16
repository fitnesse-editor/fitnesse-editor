package fitnesseclipse.ui.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IMarkerResolution;

public class MarkerResolutionProposal implements ICompletionProposal {

    private IMarkerResolution resolution;
    private IMarker marker;

    public MarkerResolutionProposal(IMarkerResolution resolution, IMarker marker) {
        this.resolution = resolution;
        this.marker = marker;
    }

    @Override
    public void apply(IDocument document) {
        resolution.run(marker);
    }

    @Override
    public Point getSelection(IDocument document) {
        return null;
    }

    @Override
    public String getAdditionalProposalInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayString() {
        return resolution.getLabel();
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public IContextInformation getContextInformation() {
        // TODO Auto-generated method stub
        return null;
    }

}
