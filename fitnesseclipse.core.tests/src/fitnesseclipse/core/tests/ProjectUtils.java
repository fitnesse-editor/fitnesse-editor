package fitnesseclipse.core.tests;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class ProjectUtils {

    public static void deleteAllProjects() throws CoreException {
        for (IProject project : getWorkspaceRoot().getProjects()) {
            project.delete(true, null);
        }
    }

    public static IProject importProject(String projectName) throws Exception {
        File directory = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile() + "/" + projectName);

        File root = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
        FileUtils.copyDirectoryToDirectory(new File("projects/" + projectName), root);

        try (FileInputStream fis = new FileInputStream(new File(directory + "/.project"))) {
            IProjectDescription description = getWorkspace().loadProjectDescription(fis);
            IProject project = getWorkspaceRoot().getProject(description.getName());
            project.create(description, IResource.DEPTH_INFINITE, null);
            project.open(IResource.DEPTH_INFINITE, null);
            project.getDescription().getBuildSpec();
            return project;
        }
    }

    private static IWorkspace getWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    private static IWorkspaceRoot getWorkspaceRoot() {
        return getWorkspace().getRoot();
    }
}
