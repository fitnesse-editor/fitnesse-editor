package fitnesseclipse.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.core.FitnesseModel;
import fitnesseclipse.ui.utils.Preferences;

/**
 * The activator class controls the plug-in life cycle
 */
public class FiteditUi extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "fitnesseclipse.ui"; //$NON-NLS-1$

    // The shared instance
    private static FiteditUi plugin;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        FiteditCore.getFiteditCore().setFitnesseRoot(Preferences.getFitnesseRoot());
        FitnesseModel.load();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;

        FitnesseModel.store();
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static FiteditUi getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in relative path
     *
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

}
