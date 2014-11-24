package fitnesseclipse.core.internal;

import org.eclipse.core.resources.IProject;

import fitnesseclipse.core.IFitnesseStaticPage;

public class FitnesseStaticPage extends AbstractFitnessePage implements IFitnesseStaticPage {

    public FitnesseStaticPage(IProject project, String path) {
        super(project, path);
    }

    @Override
    public String toString() {
        return getPath().toString();
    }
}
