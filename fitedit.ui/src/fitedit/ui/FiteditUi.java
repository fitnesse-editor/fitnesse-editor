package fitedit.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import fitedit.core.FiteditCore;
import fitedit.core.FitnesseModel;
import fitedit.ui.utils.Preferences;

/**
 * The activator class controls the plug-in life cycle
 */
public class FiteditUi extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "fitedit.ui"; //$NON-NLS-1$

    // The shared instance
    private static FiteditUi plugin;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        FiteditCore.getFiteditCore().setFitnesseRoot(Preferences.getFitnesseRoot());
        FitnesseModel.getFitnesseModel().index();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
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
