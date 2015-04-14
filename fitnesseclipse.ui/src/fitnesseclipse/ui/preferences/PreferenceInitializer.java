package fitnesseclipse.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fitnesseclipse.ui.FitnesseEclipseUi;

/**
 * Class used to initialise default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = FitnesseEclipseUi.getDefault().getPreferenceStore();
        store.setDefault(PreferenceConstants.FITNESSE_URL, "http://localhost:8080/");
        store.setDefault(PreferenceConstants.FITNESSE_ROOT_DIR, "FitNesseRoot");
    }
}
