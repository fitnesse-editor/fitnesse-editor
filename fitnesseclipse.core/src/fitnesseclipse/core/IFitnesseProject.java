package fitnesseclipse.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public interface IFitnesseProject {
    IFitnesseSuitePage findSuitePage(IPath path) throws CoreException;

    IFitnesseStaticPage findStaticPage(IPath path) throws CoreException;

    IFitnesseTestPage findTestPage(IPath path) throws CoreException;

    IFitnessePage findPage(IPath path) throws CoreException;

    IFitnesseTestPage createTestPage(IPath path) throws CoreException;
}
