package fitnesseclipse.usagedatacollector;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.dmurph.tracking.JGoogleAnalyticsTracker.DispatchMode;
import com.dmurph.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;

public class UsageDataCollector {

    private final JGoogleAnalyticsTracker tracker;

    public UsageDataCollector() {
        AnalyticsConfigData data = new AnalyticsConfigData("UA-56774387-3");
        data.populateFromSystem();

        tracker = new JGoogleAnalyticsTracker(data, GoogleAnalyticsVersion.V_4_7_2);
        tracker.setDispatchMode(DispatchMode.SYNCHRONOUS);
    }

    public void track(String category, String action) {
        tracker.trackEvent(category, action);
    }
}
