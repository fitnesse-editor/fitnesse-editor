package fitnesseclipse.ui.utils;

import org.eclipse.core.runtime.Status;

import fitnesseclipse.ui.FiteditUi;

public class LoggingUtil {
    public static void log(int severity, String msg) {
        FiteditUi.getDefault().getLog().log(new Status(severity, FiteditUi.PLUGIN_ID, msg));
    }
}
