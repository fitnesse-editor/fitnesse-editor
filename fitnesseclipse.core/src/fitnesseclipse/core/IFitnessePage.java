package fitnesseclipse.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public interface IFitnessePage extends Comparable<IFitnessePage> {
    String getPath();

    IProject getProject();

    IFile getFile();
}
