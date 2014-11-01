package fitedit.ui;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import fitedit.ui.resource.FitResourceChangeListener;
import fitedit.ui.resource.FitResourceManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class FiteditUi extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "fitedit.ui.ui"; //$NON-NLS-1$

    // The shared instance
    private static FiteditUi plugin;

    private FitResourceChangeListener fitResourceChangeListener;

    /**
     * The constructor
     */
    public FiteditUi() {
        fitResourceChangeListener = new FitResourceChangeListener();
        ResourcesPlugin.getWorkspace().addResourceChangeListener(fitResourceChangeListener,
                IResourceChangeEvent.POST_CHANGE);
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        // start creating index
        FitResourceManager.getInstance().rebuildIndex();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(fitResourceChangeListener);
        plugin = null;
        super.stop(context);
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
