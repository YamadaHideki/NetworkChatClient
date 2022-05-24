import logger.Settings;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Settings settings = new Settings();
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mainMenu_UI();
    }

    public static void client_UI() {
        if (settings.isEmptySettingByKey(settings.JSON_MODEL_KEY_NICK)) {
            System.out.println("Не указан Nick");
        }

        if (settings.isEmptySettingByKey(settings.JSON_MODEL_KEY_SERVER_IP)) {
            System.out.println("Не указан Server IP");
        }

        if (settings.isEmptySettingByKey(settings.JSON_MODEL_KEY_SERVER_PORT)) {
            System.out.println("Не указан Server PORT");
        }

        if (!settings.isEmptySettingByKey(settings.JSON_MODEL_KEY_NICK) ||
            !settings.isEmptySettingByKey(settings.JSON_MODEL_KEY_SERVER_IP) ||
            !settings.isEmptySettingByKey(settings.JSON_MODEL_KEY_SERVER_PORT)) {
            try {
                spaceInConsole();
                Client.main(settings);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public static void mainMenu_UI() {
        System.out.println("Добро пожаловать в чат на Java");
        boolean activeMenu = true;
        while (activeMenu) {
            System.out.println("Для запуска чата /start, для выхода /exit, для меню настроек /settings" + "\r\n");

            String in = scanner.nextLine();
            switch (in) {
                case "/start":
                    client_UI();
                    break;
                case "/settings":
                    spaceInConsole();
                    settings_UI();
                    break;
                case "/exit":
                    activeMenu = false;
                    break;
                default:
                    break;
            }
        }
    }

    public static void settings_UI() {
        Settings settings = new Settings();
        boolean activeMenu = true;

        while (activeMenu) {

            if (settings.isEmptySettingByKey(settings.JSON_MODEL_KEY_NICK)) {
                System.out.println("1. Добавить ник");
            } else {
                System.out.println("1. Изменить ник");
            }

            if (settings.isEmptySettingByKey(settings.JSON_MODEL_KEY_SERVER_IP)) {
                System.out.println("2. Добавить Server IP");
            } else {
                System.out.println("2. Изменить Server IP");
            }

            if (settings.isEmptySettingByKey(settings.JSON_MODEL_KEY_SERVER_PORT)) {
                System.out.println("3. Добавить Server PORT");
            } else {
                System.out.println("3. Изменить Server PORT");
            }
            System.out.println("4. Назад");

            int chooseMenu = -1;
            try {
                chooseMenu = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) { }

            switch (chooseMenu) {
                case 1:
                    System.out.println("Введите ник");
                    String nickName = scanner.nextLine();
                    settings.setNick(nickName);
                    break;
                case 2:
                    System.out.println("Введите Server IP");
                    String serverIp = scanner.nextLine();
                    settings.setServerIp(serverIp);
                    break;
                case 3:
                    System.out.println("Введите Server PORT");
                    String serverPort = scanner.nextLine();
                    settings.setServerPort(Integer.parseInt(serverPort));
                    break;
                case 4:
                    activeMenu = false;
                    break;
                default:
                    System.out.println("Введите корректное число");
                    break;
            }
            spaceInConsole();
        }
    }

    public static void spaceInConsole() {
        System.out.println("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
    }
}
