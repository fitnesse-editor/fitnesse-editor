package fitnesseclipse.ui.menu.popup.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

public class FormatAction extends AbstractActionDelegate {

    @Override
    void doRun(IResource resource) {
        List<IFile> files = new ArrayList<>();
        try {
            resource.accept(new FormatResourceVisitor(files), IResource.DEPTH_INFINITE, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        new FormatJob(files).schedule();
    }

}
