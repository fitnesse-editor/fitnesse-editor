package fitnesseclipse.core;

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

import fitnesseclipse.internal.core.FitnesseStaticPage;
import fitnesseclipse.internal.core.FitnesseSuitePage;
import fitnesseclipse.internal.core.FitnesseTestPage;
import fitnesseclipse.internal.core.builder.DefaultResourceVisitor;

public class FitnesseModel {
    private static final FitnesseModel MODEL = new FitnesseModel();

    private static Map<String, IFitnesseTestPage> tests = new LinkedHashMap<String, IFitnesseTestPage>();
    private static Map<String, IFitnesseSuitePage> suites = new LinkedHashMap<String, IFitnesseSuitePage>();
    private static Map<String, IFitnesseStaticPage> statics = new LinkedHashMap<String, IFitnesseStaticPage>();

    private FitnesseModel() {
    }

    public static FitnesseModel getFitnesseModel() {
        return MODEL;
    }

    public List<IFitnessePage> getPages() {
        List<IFitnessePage> all = new ArrayList<IFitnessePage>();
        all.addAll(tests.values());
        all.addAll(statics.values());
        all.addAll(suites.values());
        return all;
    }

    public static void addSuitePage(IFile content, IProject project) {
        suites.put(content.getProjectRelativePath().toString(), new FitnesseSuitePage(content));
    }

    public IFitnesseSuitePage getSuitePage(IPath path) {
        return suites.get(path.toString());
    }

    public static void addTestPage(IFile content, IProject project) {
        tests.put(content.getProjectRelativePath().toString(), new FitnesseTestPage(content));
    }

    public IFitnesseTestPage getTestPage(IPath path) {
        return tests.get(path.toString());
    }

    public static void addStaticPage(IFile content, IProject project) {
        statics.put(content.getProjectRelativePath().toString(), new FitnesseStaticPage(content));
    }

    public IFitnesseStaticPage getStaticPage(IPath path) {
        return statics.get(path.toString());
    }

    public void index() throws CoreException {
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
}
