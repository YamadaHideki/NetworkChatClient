import logger.ClientLogger;
import logger.Settings;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static final ExecutorService pool = Executors.newFixedThreadPool(2);
    private static final Thread inputThread = new Thread();

    public static void main(Settings settings) throws IOException {
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
            ClientLogger.log("Соединение с сервером установлено");
        } catch (IOException e) {
            ClientLogger.log("Не удалось соединиться с сервером");
        }


        // Получаем входящий и исходящий потоки информации
        try {

            pool.submit(() -> {
                //  Определяем буфер для получения данных
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = 0;
                    try {
                        bytesCount = socketChannel.read(inputBuffer);
                        System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                        inputBuffer.clear();
                    } catch (IOException ignored) { }
                }
            });



            String msg;
            while (true) {
                msg = Main.scanner.nextLine().trim();
                if (msg.trim().equals("/exit")) break;

                msg = new JSONObject().put("nick", settings.getNick()).put("message", msg).toString() + "\n";

                socketChannel.write(
                        ByteBuffer.wrap(
                                msg.getBytes(StandardCharsets.UTF_8)));

                /*int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();*/
            }
        } finally {
            socketChannel.close();
            ClientLogger.log("Соединение с сервером закрыто");
        }

    }
}