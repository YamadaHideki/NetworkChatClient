import logger.Settings;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;

public class TestMultiplyConnectToServer {

    final public Settings settings = new Settings();

    public static String generateRandomWords()
    {
        Random random = new Random();
        char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for (int j = 0; j < word.length; j++) {
            word[j] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }

    public String generateJsonSettings() {
        JSONObject jo = new JSONObject();
        jo.put("nick", generateRandomWords());
        jo.put("server_ip", "127.0.0.1");
        jo.put("server_port", 8081);
        return jo.toString();
    }

    @Test
    public void MultiplyConnectToServer() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    TestClient.main(
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
}
