package fitnesseclipse.logging;

import org.slf4j.Logger;

public class LoggerFactory {
    public static Logger getLogger(Class<?> clazz) {
        return org.slf4j.LoggerFactory.getLogger(clazz);
    }
}
