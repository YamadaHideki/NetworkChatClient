import logger.ClientLogger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String serverIp, int serverPort) throws IOException {
        // Определяем сокет сервера
        InetSocketAddress socketAddress = new InetSocketAddress(serverIp, serverPort);
        final SocketChannel socketChannel = SocketChannel.open();

        //  подключаемся к серверу
        try {
            socketChannel.connect(socketAddress);
            ClientLogger.log("Соединение с сервером установлено");
        } catch (IOException e) {
            ClientLogger.log("Не удалось соединиться с сервером");
        }


        // Получаем входящий и исходящий потоки информации
        try {

            //  Определяем буфер для получения данных
            //final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

            String msg;
            while (true) {
                System.out.println("Введите число n для последовательности чисел Фибоначчи...");
                msg = Main.scanner.nextLine().trim() + "\r\n";
                if (msg.trim().equals("/exit")) break;

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