package fitnesseclipse.logging;

public class LoggerFactory {
    public static ILogger getLogger(Class<?> clazz) {
        return new Log4jLogger(clazz);
    }
}
