package fitnesseclipse.ui.utils;

import fitnesseclipse.ui.FitnesseEclipseUi;
import fitnesseclipse.ui.preferences.PreferenceConstants;

public class Preferences {
    public static String getFitnesseRoot() {
        return FitnesseEclipseUi.getDefault().getPreferenceStore().getString(PreferenceConstants.FITNESSE_ROOT_DIR);
    }
}
