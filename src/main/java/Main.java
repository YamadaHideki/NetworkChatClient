import logger.Settings;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        menu_UI();
    }

    public static void menu_UI() {
        Settings settings = new Settings();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в чат на Java");
        System.out.println("Для использования команды меню впереди нужно поставить slash \\");
        System.out.println("Nick: " + settings.getNick());
        while (true) {
            if (settings.getNick().equals("")) {
                System.out.println("1. Добавить ник");
            } else {
                System.out.println("1. Изменить ник");
            }
            int chooseMenuId = Integer.parseInt(scanner.nextLine());
            if (chooseMenuId == 0) break;
            switch (chooseMenuId) {
                case 1:
                    System.out.println("Введите ник");
                    String nickName = scanner.nextLine();
                    settings.setNick(nickName);
                    break;
                default:
                    break;
            }
        }
    }
}
