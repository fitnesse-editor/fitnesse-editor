package fitnesseclipse.core;

import org.eclipse.core.runtime.IPath;

public interface IFitnesseProject {
    IFitnesseSuitePage findSuitePage(IPath path);

    IFitnesseStaticPage findStaticPage(IPath path);

    IFitnesseTestPage findTestPage(IPath path);

    IFitnessePage findPage(IPath path);

    IFitnesseTestPage createTestPage(IPath path);
}
