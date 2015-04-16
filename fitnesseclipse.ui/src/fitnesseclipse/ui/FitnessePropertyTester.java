package fitnesseclipse.ui;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import fitnesseclipse.core.FitnesseEclipseCore;

public class FitnessePropertyTester extends PropertyTester {

    @Override
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
        if (receiver instanceof IProject) {
            return true;
        } else if (receiver instanceof IFile || receiver instanceof IFolder) {
            return ((IResource) receiver).getProjectRelativePath().toPortableString().startsWith(fitnesseRoot());
        }
        return false;
    }

    private String fitnesseRoot() {
        try {
            return FitnesseEclipseCore.getFiteditCore().getModel().getFitnesseRoot();
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }
}
