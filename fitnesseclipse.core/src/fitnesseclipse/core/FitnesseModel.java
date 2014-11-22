package fitnesseclipse.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import fitnesseclipse.internal.core.FitnesseStaticPage;
import fitnesseclipse.internal.core.FitnesseSuitePage;
import fitnesseclipse.internal.core.FitnesseTestPage;
import fitnesseclipse.internal.core.builder.DefaultResourceVisitor;

@SuppressWarnings("serial")
public class FitnesseModel implements Serializable {
    private static FitnesseModel model = new FitnesseModel();

    // project name, test path
    private final Map<String, List<String>> tests = new LinkedHashMap<String, List<String>>();
    private final Map<String, List<String>> suites = new LinkedHashMap<String, List<String>>();
    private final Map<String, List<String>> statics = new LinkedHashMap<String, List<String>>();

    private FitnesseModel() {
    }

    public static FitnesseModel getFitnesseModel() throws CoreException {
        if (model == null) {
            load();
        }
        return model;
    }

    public List<IFitnessePage> getPages() {
        List<IFitnessePage> all = new ArrayList<IFitnessePage>();

        for (IProject project : getProjects()) {
            String projectName = project.getName();

            List<String> projectTests = tests.get(projectName);
            if (projectTests != null) {
                for (String page : projectTests) {
                    all.add(new FitnesseTestPage(project, page));
                }
            }

            List<String> projectStatics = statics.get(projectName);
            if (projectStatics != null) {
                for (String page : projectStatics) {
                    all.add(new FitnesseStaticPage(project, page));
                }
            }

            List<String> projectSuites = suites.get(projectName);
            if (projectSuites != null) {
                for (String page : projectSuites) {
                    all.add(new FitnesseSuitePage(project, page));
                }
            }
        }

        return all;
    }

    private IProject[] getProjects() {
        return ResourcesPlugin.getWorkspace().getRoot().getProjects();
    }

    public void addSuitePage(IFile content) throws CoreException {
        List<String> pages = getProjectSuitePages(content.getProject());
        pages.add(content.getProjectRelativePath().toString());
        suites.put(content.getProject().getName(), pages);
    }

    private List<String> getProjectSuitePages(IProject project) {
        List<String> pages = suites.get(project.getName());
        return pages == null ? new ArrayList<String>() : pages;
    }

    public IFitnesseSuitePage getSuitePage(IProject project, IPath path) {
        if (getProjectSuitePages(project).contains(path.toString())) {
            return new FitnesseSuitePage(project, path.toString());
        }
        return null;
    }

    public void addTestPage(IFile content) throws CoreException {
        List<String> pages = getProjectTestPages(content.getProject());
        pages.add(content.getProjectRelativePath().toString());
        tests.put(content.getProject().getName(), pages);
    }

    private List<String> getProjectTestPages(IProject project) {
        List<String> pages = tests.get(project.getName());
        return pages == null ? new ArrayList<String>() : pages;
    }

    public IFitnesseTestPage getTestPage(IProject project, IPath path) {
        if (getProjectTestPages(project).contains(path.toString())) {
            return new FitnesseTestPage(project, path.toString());
        }
        return null;
    }

    public void addStaticPage(IFile content) throws CoreException {
        List<String> pages = getProjectStaticPages(content.getProject());
        pages.add(content.getProjectRelativePath().toString());
        statics.put(content.getProject().getName(), pages);
    }

    private List<String> getProjectStaticPages(IProject project) {
        List<String> pages = statics.get(project.getName());
        return pages == null ? new ArrayList<String>() : pages;
    }

    public IFitnesseStaticPage getStaticPage(IProject project, IPath path) {
        if (getProjectStaticPages(project).contains(path.toString())) {
            return new FitnesseStaticPage(project, path.toString());
        }
        return null;
    }

    private void index() throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = root.getProjects();

        for (IProject project : projects) {
            if (!project.isAccessible() && !FitnesseNature.hasNature(project)) {
                continue;
            }
            // TODO create a derivative of DefaultResourceVisitor that only does indexing
            project.accept(new DefaultResourceVisitor(project));
        }
    }

    public static void load() throws CoreException {
        try {
            model = (FitnesseModel) new ObjectInputStream(new FileInputStream(fitnessModelFile())).readObject();
        } catch (ClassNotFoundException | IOException e) {
            model = new FitnesseModel();
            model.index();
        }
    }

    private static File fitnessModelFile() {
        return new File(FiteditCore.getFiteditCore().getStateLocation().toFile() + "/" + "fitnesse.model");
    }

    public static void store() throws CoreException {
        try {
            new ObjectOutputStream(new FileOutputStream(fitnessModelFile())).writeObject(model);
        } catch (IOException e) {
            throw new CoreException(new Status(IStatus.ERROR, FiteditCore.PLUGIN_ID, -1, "Failed to store model", e));
        }
    }
}
