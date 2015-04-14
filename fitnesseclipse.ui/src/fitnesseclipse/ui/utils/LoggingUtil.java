package fitnesseclipse.ui.utils;

import org.eclipse.core.runtime.Status;

import fitnesseclipse.ui.FitnesseEclipseUi;

public class LoggingUtil {
    public static void log(int severity, String msg) {
        FitnesseEclipseUi.getDefault().getLog().log(new Status(severity, FitnesseEclipseUi.PLUGIN_ID, msg));
    }
}
