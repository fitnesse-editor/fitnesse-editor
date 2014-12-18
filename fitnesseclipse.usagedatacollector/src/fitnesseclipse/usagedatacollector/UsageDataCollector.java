package fitnesseclipse.usagedatacollector;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.dmurph.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;

public class UsageDataCollector {
    public UsageDataCollector() {
        AnalyticsConfigData data = new AnalyticsConfigData("UA-56774387-3");
        data.populateFromSystem();

        JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker(data, GoogleAnalyticsVersion.V_4_7_2);

        tracker.trackEvent("plugin", "");
    }
}
