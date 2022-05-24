package multiplyConnect;

import java.io.IOException;
import java.util.Random;

public class TestMultiplyConnect {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                var testClient = new TestClient();
                try {
                    testClient.main(
                            new SettingsWithoutFile(generateRandomWords(),
                                    "127.0.0.1",
                                    8081
                            ), "Message " + (int) (Math.random() * 1000));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static String generateRandomWords()
    {
        Random random = new Random();
        char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for (int j = 0; j < word.length; j++) {
            word[j] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }
}
