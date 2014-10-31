package fitedit.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fitedit.FitEditPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = FitEditPlugin.getDefault().getPreferenceStore();
        store.setDefault(PreferenceConstants.P_URL_PREFIX, "http://localhost:8080/");
    }

}
