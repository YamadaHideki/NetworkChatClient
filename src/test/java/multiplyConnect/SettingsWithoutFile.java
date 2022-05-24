package multiplyConnect;

import logger.Settings;
import org.json.JSONObject;

public class SettingsWithoutFile extends Settings {
    private final JSONObject jo;

    public SettingsWithoutFile(String nick, String serverIp, int ServerPort) {
        jo = new JSONObject();
        jo.put(JSON_MODEL_KEY_NICK, nick);
        jo.put(JSON_MODEL_KEY_SERVER_IP, serverIp);
        jo.put(JSON_MODEL_KEY_SERVER_PORT, ServerPort);
    }

    @Override
    public String getNick() {
        return jo.getString(JSON_MODEL_KEY_NICK);
    }

    @Override
    public String getServerIp() {
        return jo.getString(JSON_MODEL_KEY_SERVER_IP);
    }

    @Override
    public int getServerPort() {
        return jo.getInt(JSON_MODEL_KEY_SERVER_PORT);
    }
}
