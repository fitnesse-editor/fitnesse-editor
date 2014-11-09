package fitedit.internal.core;

import org.eclipse.core.resources.IFile;

import fitedit.core.IFitnesseSuitePage;

public class FitnesseSuitePage extends AbstractFitnessePage implements IFitnesseSuitePage {

    public FitnesseSuitePage(IFile content) {
        super(content);
    }

    @Override
    public String toString() {
        return getFullPath().toString();
    }
}
