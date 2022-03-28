package logger;

public class ClientLogger {

    private static final Logger logger = Logger.getInstance();

    public static void log(String s) {
        logger.addLog("server_log.txt", s);
    }
}
