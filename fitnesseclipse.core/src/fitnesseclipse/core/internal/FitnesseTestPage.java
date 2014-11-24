package fitnesseclipse.core.internal;

import org.eclipse.core.resources.IProject;

import fitnesseclipse.core.IFitnesseTestPage;

public class FitnesseTestPage extends AbstractFitnessePage implements IFitnesseTestPage {
    public FitnesseTestPage(IProject project, String path) {
        super(project, path);
    }

    @Override
    public String toString() {
        return getPath().toString();
    }
}
