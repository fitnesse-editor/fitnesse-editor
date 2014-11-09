package fitedit.ui.utils;

import fitedit.ui.FiteditUi;
import fitedit.ui.preferences.PreferenceConstants;

public class Preferences {
    public static String getFitnesseRoot() {
        return FiteditUi.getDefault().getPreferenceStore().getString(PreferenceConstants.FITNESSE_ROOT_DIR);
    }
}
