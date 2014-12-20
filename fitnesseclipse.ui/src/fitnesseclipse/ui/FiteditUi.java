package fitnesseclipse.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import fitnesseclipse.core.FiteditCore;
import fitnesseclipse.ui.preferences.PreferenceConstants;
import fitnesseclipse.usagedatacollector.UsageDataCollector;

/**
 * The activator class controls the plug-in life cycle
 */
public class FiteditUi extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "fitnesseclipse.ui"; //$NON-NLS-1$

    // The shared instance
    private static FiteditUi plugin;

    private UsageDataCollector udc;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if (PreferenceConstants.FITNESSE_ROOT_DIR.equals(event.getProperty())) {
                    try {
                        FiteditCore.getFiteditCore().getModel().setFitnesseRoot((String) event.getNewValue());
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        udc = new UsageDataCollector();
        udc.track("startup", "plugin_activated");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        udc.track("shutdwn", "plugin_deactivated");

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
