package fitnesseclipse.logging;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class FitnesseLogging extends Plugin {
    private static final String LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss} %-5p %C - %m%n";

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        configureLogger();
    }

    private void configureLogger() throws IOException {
        Logger logger = Logger.getRootLogger();
        PatternLayout layout = new PatternLayout(LOG_PATTERN);
        logger.addAppender(new ConsoleAppender(layout));
        logger.addAppender(new RollingFileAppender(layout, logFile()));
        logger.setLevel(Level.TRACE);
    }

    private String logFile() {
        return getStateLocation().addTrailingSeparator().append("fitnesse.log").toFile().getAbsolutePath();
    }
}
