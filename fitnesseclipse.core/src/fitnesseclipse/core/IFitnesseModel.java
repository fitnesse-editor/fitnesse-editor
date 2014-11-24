package fitnesseclipse.core;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public interface IFitnesseModel {

    public abstract void setFitnesseRoot(String root) throws CoreException;

    public abstract String getFitnesseRoot();

    public abstract List<IFitnessePage> getPages();

    public abstract void addSuitePage(IFile content) throws CoreException;

    public abstract IFitnesseSuitePage getSuitePage(IProject project, IPath path);

    public abstract void addTestPage(IFile content) throws CoreException;

    public abstract IFitnesseTestPage getTestPage(IProject project, IPath path);

    public abstract void addStaticPage(IFile content) throws CoreException;

    public abstract IFitnesseStaticPage getStaticPage(IProject project, IPath path);

}