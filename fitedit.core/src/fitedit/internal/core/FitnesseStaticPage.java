package fitedit.internal.core;

import org.eclipse.core.resources.IFile;

import fitedit.core.IFitnesseStaticPage;

public class FitnesseStaticPage extends AbstractFitnessePage implements IFitnesseStaticPage {

    public FitnesseStaticPage(IFile content) {
        super(content);
    }

    @Override
    public String toString() {
        return getFullPath().toString();
    }
}
