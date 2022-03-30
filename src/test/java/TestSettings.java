import logger.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSettings {
    private final Settings settings = new Settings();
    private final String settingsFileName = "test_settings.json";

    @BeforeEach
    @DisplayName("Сброс настроек")
    public void createTestSettingsFile() {
        settings.defaultSettings();
    }

    @Test
    @DisplayName("Тест на добавление и изменения настроек в файл json")
    public void addAndEditSettingsInFileJson() {

        for (String key : settings.getJsonKeys()) {
            Assertions.assertTrue(settings.isEmptySettingByKey(key));
        }

        String nick = "test_nick";
        settings.setNick(nick);
        Assertions.assertEquals(settings.getNick(), nick);

        String serverIp = "127.0.0.1";
        settings.setServerIp(serverIp);
        Assertions.assertEquals(settings.getServerIp(), serverIp);

        int serverPort = 8081;
        settings.setServerPort(serverPort);
        Assertions.assertEquals(settings.getServerPort(), serverPort);
    }
}
