package fitnesseclipse.ui.quickfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IMarkerResolution;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.core.IFitnesseProject;

public class CreateTestPageQuickfix implements IMarkerResolution {

    private IMarker marker;

    public CreateTestPageQuickfix(IMarker marker) {
        this.marker = marker;
    }

    @Override
    public String getLabel() {
        return "Create Test Page (" + marker.getResource().getProjectRelativePath() + ")";
    }

    @Override
    public void run(IMarker marker) {
        try {
            String page = (String) marker.getAttribute("page");
            IProject project = marker.getResource().getProject();
            IFitnesseProject fitnesseProject = FiteditCore.create(project);
            fitnesseProject.createTestPage(project.getFile(page).getProjectRelativePath());
        } catch (CoreException e) {
            ErrorDialog.openError(null, "", e.getMessage(), null);
        }
    }

}
