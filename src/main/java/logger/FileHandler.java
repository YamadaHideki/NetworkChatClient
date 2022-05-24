package logger;

import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class FileHandler {
    private static FileHandler INSTANCE = null;
    private final Queue<String> errors = new PriorityQueue<>();

    private FileHandler() {

    }

    public static FileHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (FileHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FileHandler();
                }
            }
        }
        return INSTANCE;
    }

    public void addTextInFile(String fileName, String s) {
        File file = new File(fileName);

        if (!createFile(file)) {
            errors.add("Can't create file: " + "\"" + file + "\"");
        }

        try (FileInputStream fs = new FileInputStream(file);
             FileWriter fw = new FileWriter(file, true)) {
            if (fs.read() == 0) {
                fw.write(s);
            } else {
                fw.write(s + "\n");
            }
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            errors.add("Can't write/read " + fileName);
        }

        logsError();
    }

    public String getString(String fileName) {
        StringBuilder sb = new StringBuilder();
        File f = readFile(fileName);
        try (FileReader fr = new FileReader(f)) {
            while (fr.ready()) {
                int data = fr.read();
                sb.append((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void replaceData(String fileName, String data) {
        File f = new File(fileName);

        if (!createFile(f)) {
            errors.add("Can't create file: " + "\"" + f + "\"");
        }

        try (FileInputStream fs = new FileInputStream(f);
             FileWriter fw = new FileWriter(f, false)) {
            fw.write(data);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            errors.add("Can't write/read " + fileName);
        }
    }

    public boolean createFile(File f) {
        if(!f.exists()) {
            try {
                return f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public File readFile(String n) {
        File file = new File(n);
        createFile(file);
        return file;
    }

    public boolean deleteFile(File f) {
        if (f.exists()) {
            return f.delete();
        }
        return false;
    }

    public void logsError() {
        for (String s : errors) {
            ClientLogger.log(s);
        }
    }

    public boolean compareFiles(byte[] file1, byte[] file2) {
        return Arrays.equals(file1, file2);
    }
}
