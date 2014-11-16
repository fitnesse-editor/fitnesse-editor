package fitnesseclipse.ui.utils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;

import fitnesseclipse.ui.FiteditUi;

/**
 * Utility class to open error & message dialogs.
 * 
 * @author Andrew Holland (a1dutch)
 * @since 2.0
 */
public final class DialogUtils {
    private DialogUtils() {
        // utility class
    }

    public static void openErrorDialog(String title, String message, String statusMessage, Throwable e) {
        ErrorDialog.openError(null, title, message,
                new Status(IStatus.ERROR, FiteditUi.PLUGIN_ID, statusMessage, e));
    }
}
