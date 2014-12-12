package fitnesseclipse.logging;

import org.apache.log4j.Logger;

public class Log4jLogger implements ILogger {
    private final Logger log;

    public Log4jLogger(Class<?> clazz) {
        log = Logger.getLogger(clazz);
    }

    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void debug(String message) {
        log.debug(message);
    }

    @Override
    public void trace(String message) {
        log.trace(message);
    }

    @Override
    public void warn(String message) {
        log.warn(message);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }

}
