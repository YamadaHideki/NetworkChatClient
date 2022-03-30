package logger;

public class ClientLogger {

    private static final Logger logger = Logger.getInstance();
    private static final String clientLogFileName = "client_log.txt";

    public static void log(String s) {
        logger.addLog(clientLogFileName, s);
    }
}
