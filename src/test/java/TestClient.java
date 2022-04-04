import logger.ClientLogger;
import logger.Settings;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TestClient {

    public static void main(Settings settings, String message) throws IOException {
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

            //  Определяем буфер для получения данных
            //final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

            String msg;
            for (int i = 0; i < 20; i++) {
                Thread.sleep((int) (Math.random() * 3000 + 500));
                System.out.println("Введите число n для последовательности чисел Фибоначчи...");
                msg = message;

                msg = new JSONObject().put("nick", settings.getNick()).put("message", msg).toString() + "\n";

                socketChannel.write(
                        ByteBuffer.wrap(
                                msg.getBytes(StandardCharsets.UTF_8)));

                /*int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();*/
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            socketChannel.close();
            //ClientLogger.log("Соединение с сервером закрыто");
        }

    }
}