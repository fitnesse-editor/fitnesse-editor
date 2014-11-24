package fitnesseclipse.core.tests;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
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
        File directory = new File("projects/" + projectName);
        try (FileInputStream fis = new FileInputStream(new File(directory + "/.project"))) {
            IProjectDescription description = getWorkspace().loadProjectDescription(fis);
            IProject project = getWorkspaceRoot().getProject(description.getName());
            project.create(description, null);
            project.open(null);

            Collection<File> listFiles = FileUtils.listFilesAndDirs(directory,
                    FileFilterUtils.notFileFilter(FileFilterUtils.nameFileFilter(".project")),
                    FileFilterUtils.falseFileFilter());

            for (File file : listFiles) {
                if (file.isDirectory()) {
                    FileUtils.copyDirectory(file, project.getLocation().toFile());
                } else {
                    FileUtils.copyFileToDirectory(file, project.getLocation().toFile());
                }
            }

            project.refreshLocal(IResource.DEPTH_INFINITE, null);

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
