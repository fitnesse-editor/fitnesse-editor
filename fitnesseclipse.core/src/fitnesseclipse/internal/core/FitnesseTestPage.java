package fitnesseclipse.internal.core;

import org.eclipse.core.resources.IProject;

import fitnesseclipse.core.IFitnesseTestPage;

@SuppressWarnings("serial")
public class FitnesseTestPage extends AbstractFitnessePage implements IFitnesseTestPage {
    public FitnesseTestPage(IProject project, String path) {
        super(project, path);
    }

    @Override
    public String toString() {
        return getPath().toString();
    }
}
