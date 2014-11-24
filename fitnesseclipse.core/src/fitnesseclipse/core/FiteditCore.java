package fitnesseclipse.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import fitnesseclipse.core.internal.FitnesseProject;
import fitnesseclipse.core.internal.model.FitnesseModel;

public class FiteditCore extends Plugin {

    public static final String PLUGIN_ID = "fitnesseclipse.core";
    public static final String BUILDER_ID = "fitnesseclipse.core.fitnesseBuilder";
    public static final String MARKER_TYPE = "fitnesseclipse.core.marker.problemmarker";
    private static FiteditCore core;

    public static FiteditCore getFiteditCore() {
        return core;
    }

    public IFitnesseModel getModel() throws CoreException {
        return FitnesseModel.getFitnesseModel();
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
        super.start(bundleContext);
        core = this;

        FitnesseModel.load();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        super.stop(bundleContext);

        FitnesseModel.store();
    }

}
