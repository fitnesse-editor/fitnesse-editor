package fitnesseclipse.core.internal.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import fitnesseclipse.core.FitnesseEclipseCore;
import fitnesseclipse.core.FitnesseNature;
import fitnesseclipse.core.IFitnesseModel;
import fitnesseclipse.core.IFitnessePage;
import fitnesseclipse.core.IFitnesseStaticPage;
import fitnesseclipse.core.IFitnesseSuitePage;
import fitnesseclipse.core.IFitnesseTestPage;
import fitnesseclipse.core.internal.FitnesseStaticPage;
import fitnesseclipse.core.internal.FitnesseSuitePage;
import fitnesseclipse.core.internal.FitnesseTestPage;
import fitnesseclipse.core.internal.builder.DefaultResourceVisitor;

@SuppressWarnings("serial")
public class FitnesseModel implements IFitnesseModel, Serializable {
    private static final String DEFAULT_FITNESSE_ROOT = "FitNesseRoot";

    private static FitnesseModel model = null;

    // project name, test path
    private final Map<String, Set<String>> tests = new LinkedHashMap<String, Set<String>>();
    private final Map<String, Set<String>> suites = new LinkedHashMap<String, Set<String>>();
    private final Map<String, Set<String>> statics = new LinkedHashMap<String, Set<String>>();

    private String fitnesseRoot;

    private FitnesseModel(String root) {
        this.fitnesseRoot = root;
    }

    public static IFitnesseModel getFitnesseModel() throws CoreException {
        if (model == null) {
            load();
        }
        return model;
    }

    @Override
    public List<IFitnessePage> getPages() throws CoreException {
        List<IFitnessePage> all = new ArrayList<IFitnessePage>();

        for (IProject project : getProjects()) {
            String projectName = project.getName();

            Set<String> projectTests = tests.get(projectName);
            if (projectTests != null) {
                for (String page : projectTests) {
                    all.add(new FitnesseTestPage(project, page));
                }
            }

            Set<String> projectStatics = statics.get(projectName);
            if (projectStatics != null) {
                for (String page : projectStatics) {
                    all.add(new FitnesseStaticPage(project, page));
                }
            }

            Set<String> projectSuites = suites.get(projectName);
            if (projectSuites != null) {
                for (String page : projectSuites) {
                    all.add(new FitnesseSuitePage(project, page));
                }
            }
        }

        return all;
    }

    private IProject[] getProjects() throws CoreException {
        List<IProject> projects = new ArrayList<IProject>();
        for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
            if (project.isAccessible() && FitnesseNature.hasNature(project)) {
                projects.add(project);
            }
        }
        return projects.toArray(new IProject[projects.size()]);
    }

    @Override
    public void addSuitePage(IFile content) throws CoreException {
        Set<String> pages = getProjectSuitePages(content.getProject());
        pages.add(content.getProjectRelativePath().removeLastSegments(1).toString());
        suites.put(content.getProject().getName(), pages);
    }

    private Set<String> getProjectSuitePages(IProject project) {
        Set<String> pages = suites.get(project.getName());
        return pages == null ? new LinkedHashSet<String>() : pages;
    }

    @Override
    public IFitnesseSuitePage getSuitePage(IProject project, IPath path) {
        if (getProjectSuitePages(project).contains(fileRelativeToProject(project, path))) {
            return new FitnesseSuitePage(project, path.toString());
        }
        return null;
    }

    @Override
    public void addTestPage(IFile content) throws CoreException {
        Set<String> pages = getProjectTestPages(content.getProject());
        pages.add(content.getProjectRelativePath().removeLastSegments(1).toString());
        tests.put(content.getProject().getName(), pages);
    }

    private Set<String> getProjectTestPages(IProject project) {
        Set<String> pages = tests.get(project.getName());
        return pages == null ? new LinkedHashSet<String>() : pages;
    }

    @Override
    public IFitnesseTestPage getTestPage(IProject project, IPath path) {
        if (getProjectTestPages(project).contains(fileRelativeToProject(project, path))) {
            return new FitnesseTestPage(project, path.toString());
        }
        return null;
    }

    @Override
    public void addStaticPage(IFile content) throws CoreException {
        Set<String> pages = getProjectStaticPages(content.getProject());
        pages.add(content.getProjectRelativePath().removeLastSegments(1).toString());
        statics.put(content.getProject().getName(), pages);
    }

    private Set<String> getProjectStaticPages(IProject project) {
        Set<String> pages = statics.get(project.getName());
        return pages == null ? new LinkedHashSet<String>() : pages;
    }

    @Override
    public IFitnesseStaticPage getStaticPage(IProject project, IPath path) {
        if (getProjectStaticPages(project).contains(fileRelativeToProject(project, path))) {
            return new FitnesseStaticPage(project, path.toString());
        }
        return null;
    }

    private String fileRelativeToProject(IProject project, IPath path) {
        if (project.getFullPath().isPrefixOf(path)) {
            return path.makeRelativeTo(project.getFullPath()).toString();
        }
        return project.getFile(path).getProjectRelativePath().toString();
    }

    private void index() throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = root.getProjects();

        for (IProject project : projects) {
            if (!project.isAccessible() || !FitnesseNature.hasNature(project)) {
                continue;
            }
            // TODO create a derivative of DefaultResourceVisitor that only does indexing
            project.accept(new DefaultResourceVisitor(fitnesseRoot, project));
        }
    }

    public static IFitnesseModel load() throws CoreException {
        try {
            model = (FitnesseModel) new ObjectInputStream(new FileInputStream(fitnessModelFile())).readObject();
        } catch (ClassNotFoundException | IOException e) {
            model = new FitnesseModel(DEFAULT_FITNESSE_ROOT);
            model.index();
        }
        return model;
    }

    private static File fitnessModelFile() {
        return new File(FitnesseEclipseCore.getFiteditCore().getStateLocation().toFile() + "/" + "fitnesse.model");
    }

    public static void store() throws CoreException {
        try {
            new ObjectOutputStream(new FileOutputStream(fitnessModelFile())).writeObject(model);
        } catch (IOException e) {
            throw new CoreException(new Status(IStatus.ERROR, FitnesseEclipseCore.PLUGIN_ID, -1,
                    "Failed to store model", e));
        }
    }

    @Override
    public void setFitnesseRoot(String root) throws CoreException {
        fitnesseRoot = root;
        reindex();
    }

    private void reindex() throws CoreException {
        tests.clear();
        statics.clear();
        suites.clear();
        index();
    }

    @Override
    public String getFitnesseRoot() {
        return fitnesseRoot;
    }
}
