package fitnesseclipse.core.internal;

import org.eclipse.core.resources.IProject;

import fitnesseclipse.core.IFitnesseSuitePage;

public class FitnesseSuitePage extends AbstractFitnessePage implements IFitnesseSuitePage {

    public FitnesseSuitePage(IProject project, String path) {
        super(project, path);
    }

    @Override
    public String toString() {
        return getPath().toString();
    }
}
