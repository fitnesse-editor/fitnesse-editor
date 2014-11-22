package fitnesseclipse.core;

import java.io.Serializable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public interface IFitnessePage extends Comparable<IFitnessePage>, Serializable {
    String getPath();

    IProject getProject();

    IFile getFile();
}
