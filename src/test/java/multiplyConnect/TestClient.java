package multiplyConnect;

import logger.ClientLogger;
import logger.Settings;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TestClient {

    public void main(Settings settings, String message) throws IOException {
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
            System.out.println(1);
            //  Определяем буфер для получения данных
            String msg;
            for (int i = 0; i < 20; i++) {
                msg = new JSONObject().put("nick", settings.getNick()).put("message", message).toString() + "\n";

                socketChannel.write(
                        ByteBuffer.wrap(
                                msg.getBytes(StandardCharsets.UTF_8)));
                Thread.sleep((int) (Math.random() * 1500) + 200);
            }
        } catch (Exception e) {
            System.out.println(2);
            System.out.println(e.getMessage());
        } finally {
            System.out.println(3);
            socketChannel.close();
        }

    }
}