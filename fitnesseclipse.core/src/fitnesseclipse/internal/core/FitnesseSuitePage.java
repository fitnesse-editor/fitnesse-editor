package fitnesseclipse.internal.core;

import org.eclipse.core.resources.IProject;

import fitnesseclipse.core.IFitnesseSuitePage;

@SuppressWarnings("serial")
public class FitnesseSuitePage extends AbstractFitnessePage implements IFitnesseSuitePage {

    public FitnesseSuitePage(IProject project, String path) {
        super(project, path);
    }

    @Override
    public String toString() {
        return getPath().toString();
    }
}
