package fitedit.internal.core;

import org.eclipse.core.resources.IFile;

import fitedit.core.IFitnesseTestPage;

public class FitnesseTestPage extends AbstractFitnessePage implements IFitnesseTestPage {
    public FitnesseTestPage(IFile content) {
        super(content);
    }

    @Override
    public String toString() {
        return getFullPath().toString();
    }
}
