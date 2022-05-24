import logger.ClientLogger;
import logger.FileHandler;
import logger.MessageLogger;
import logger.Settings;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;

public class Client {
    private static final Thread inputThread = new Thread();
    private static final Queue<String> queMessages = new ConcurrentLinkedQueue<>();

    public static void main(Settings settings) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // Определяем сокет сервера
        InetSocketAddress socketAddress = new InetSocketAddress(settings.getServerIp(), settings.getServerPort());
        final SocketChannel socketChannel = SocketChannel.open();

        //  подключаемся к серверу
        try {
            socketChannel.connect(socketAddress);
            JSONObject jo = new JSONObject();
            jo.put("ehlo", settings.getNick());

            socketChannel.write(
                    ByteBuffer.wrap(
                            (jo.toString() + "\n").getBytes(StandardCharsets.UTF_8)));

            /* Получаем размер файла лога, для создания буфера */
            var byteBufferSizeFile = ByteBuffer.allocate(Long.BYTES);
            socketChannel.read(byteBufferSizeFile);
            byteBufferSizeFile.flip();

            /* Получаем файл лога message_log.txt */
            var inputBuffer = ByteBuffer.allocate((int) byteBufferSizeFile.getLong());
            socketChannel.read(inputBuffer);

            getMessagesFromServer(pool, socketChannel);

            byte[] inputBufferArray = inputBuffer.array();

            File messageLogFile = FileHandler.getInstance().readFile(MessageLogger.getLogFileName());
            try {
                var fis = new FileInputStream(messageLogFile);

                /* Если логи отличаются от загруженных с сервера, заменяем на новый */
                if (!FileHandler.getInstance().compareFiles(inputBufferArray, fis.readAllBytes())) {
                    FileWriter fw = new FileWriter(messageLogFile);
                    fw.flush();
                    fw.close();
                    Files.write(Path.of(messageLogFile.toURI()), inputBufferArray);
                }

                BufferedReader fr = new BufferedReader(new FileReader(messageLogFile));
                while (fr.ready()) {
                    var line = fr.readLine().substring(22);
                    System.out.println(line);
                }

                printMessages(pool);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ClientLogger.log("Соединение с сервером установлено");
        } catch (IOException e) {
            ClientLogger.log("Не удалось соединиться с сервером");
            socketChannel.close();
            return;
        }


        // Получаем входящий и исходящий потоки информации
        try {
            String msg;
            while (true) {
                msg = Main.scanner.nextLine().trim();
                if (msg.trim().equals("/exit")) break;
                msg = new JSONObject().put("nick", settings.getNick()).put("message", msg).toString() + "\n";
                socketChannel.write(
                        ByteBuffer.wrap(
                                msg.getBytes(StandardCharsets.UTF_8)));
            }
        } finally {
            socketChannel.close();
            ClientLogger.log("Соединение с сервером закрыто");
        }
        pool.shutdown();
    }

    public static void printMessages(ExecutorService pool) {
        pool.submit(() -> {
            while (true) {
                String message = queMessages.poll();
                if (message != null) {
                    MessageLogger.log(message);
                    System.out.println(message);
                }
                Thread.sleep(200);
            }
        });
    }

    public static void getMessagesFromServer(ExecutorService pool, SocketChannel socketChannel) {
        pool.submit(() -> {
            //  Определяем буфер для получения данных
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            while (socketChannel.isConnected()) {
                int bytesCount = 0;
                try {
                    bytesCount = socketChannel.read(inputBuffer);
                    String messageFromServer = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim();
                    queMessages.add(messageFromServer);
                    inputBuffer.clear();
                } catch (IOException ignored) { }
            }
        });
    }
}