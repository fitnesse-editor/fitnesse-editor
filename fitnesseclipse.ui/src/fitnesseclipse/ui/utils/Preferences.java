package fitnesseclipse.ui.utils;

import fitnesseclipse.ui.FiteditUi;
import fitnesseclipse.ui.preferences.PreferenceConstants;

public class Preferences {
    public static String getFitnesseRoot() {
        return FiteditUi.getDefault().getPreferenceStore().getString(PreferenceConstants.FITNESSE_ROOT_DIR);
    }
}
