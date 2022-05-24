package logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Logger {
    private static Logger instance = null;
    private final FileHandler fileHandler = FileHandler.getInstance();
    private final ExecutorService pool = Executors.newSingleThreadExecutor();
    protected int num = 1;
    protected final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    private Logger() {}

    public void addLog(String file, String s) {
        s = "[" + simpleDateFormat.format(new Date()) +"] " + s;
        fileHandler.addTextInFile(file, s);
    }

    public void log(String msg) {
        System.out.println(simpleDateFormat.format(new Date()) + " [" + num++ + "] " + msg);
    }

    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }
}
