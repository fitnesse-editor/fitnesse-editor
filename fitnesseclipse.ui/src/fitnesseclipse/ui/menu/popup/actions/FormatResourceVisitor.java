package fitnesseclipse.ui.menu.popup.actions;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

public class FormatResourceVisitor implements IResourceVisitor {

    private final List<IFile> filesToFormat;

    public FormatResourceVisitor(List<IFile> files) {
        this.filesToFormat = files;
    }

    @Override
    public boolean visit(IResource resource) throws CoreException {
        if (resource instanceof IFile) {
            if ("content.txt".equals(resource.getName())) {
                filesToFormat.add((IFile) resource);
            }
        }
        return true;
    }

}
