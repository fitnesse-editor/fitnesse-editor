package fitnesseclipse.logging;

public interface ILogger {
    void info(String message);

    void debug(String message);

    void trace(String message);

    void warn(String message);

    void error(String message);
}
