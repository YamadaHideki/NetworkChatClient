package logger;

public class MessageLogger {
    private static final String messageLogFileName = "message_log.txt";

    private static final Logger logger = Logger.getInstance();

    public static void log(String s) {
        logger.addLog(messageLogFileName, s);
    }

    public static String getLogFileName() {
        return messageLogFileName;
    }
}
