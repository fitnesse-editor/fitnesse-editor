package fitnesseclipse.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import fitnesseclipse.internal.core.FitnesseProject;

public class FiteditCore implements BundleActivator {

    private static FiteditCore core;

    public static final String BUILDER_ID = "fitnesseclipse.core.fitnesseBuilder";
    public static final String MARKER_TYPE = "fitnesseclipse.core.marker.problemmarker";

    private String root;

    public static FiteditCore getFiteditCore() {
        return core;
    }

    public void setFitnesseRoot(String root) {
        this.root = root;
    }

    public String getFitnesseRoot() {
        return root;
    }

    public static IFitnesseProject create(IProject project) {
        try {
            if (!FitnesseNature.hasNature(project)) {
                return null;
            }
        } catch (CoreException e) {
        }
        return new FitnesseProject(project);
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        core = this;
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
    }

}
