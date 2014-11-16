package fitnesseclipse.internal.core;

import org.eclipse.core.resources.IFile;

import fitnesseclipse.core.IFitnesseTestPage;

public class FitnesseTestPage extends AbstractFitnessePage implements IFitnesseTestPage {
    public FitnesseTestPage(IFile content) {
        super(content);
    }

    @Override
    public String toString() {
        return getFullPath().toString();
    }
}
