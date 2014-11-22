package fitnesseclipse.internal.core;

import org.eclipse.core.resources.IProject;

import fitnesseclipse.core.IFitnesseStaticPage;

@SuppressWarnings("serial")
public class FitnesseStaticPage extends AbstractFitnessePage implements IFitnesseStaticPage {

    public FitnesseStaticPage(IProject project, String path) {
        super(project, path);
    }

    @Override
    public String toString() {
        return getPath().toString();
    }
}
