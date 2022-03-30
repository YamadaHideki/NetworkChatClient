package logger;

import com.sun.source.tree.Tree;
import org.json.JSONObject;

import java.util.*;

public class Settings {
    private static JSONObject jo;
    private static final String settingsFileName = "client_settings.json";
    private static final FileHandler fh = new FileHandler();

    private final Set<String> jsonKeys = new HashSet<>();

    public final String JSON_MODEL_KEY_NICK = "nick";
    public final String JSON_MODEL_KEY_SERVER_IP = "server_ip";
    public final String JSON_MODEL_KEY_SERVER_PORT = "server_port";

    public Settings() {
        jsonKeys.add(JSON_MODEL_KEY_NICK);
        jsonKeys.add(JSON_MODEL_KEY_SERVER_IP);
        jsonKeys.add(JSON_MODEL_KEY_SERVER_PORT);
        jo = readSettings();
    }

    public Set<String> getJsonKeys() {
        return jsonKeys;
    }

    public String getNick() {
        return readSettings().get(JSON_MODEL_KEY_NICK).toString();
    }

    public void setNick(String s) {
        addSettings(JSON_MODEL_KEY_NICK, s);
    }

    public int getServerPort() {
        return Integer.parseInt(readSettings().get(JSON_MODEL_KEY_SERVER_PORT).toString());
    }

    public void setServerPort(int port) {
        addSettings(JSON_MODEL_KEY_SERVER_PORT, port);
    }

    public String getServerIp() {
        return readSettings().get(JSON_MODEL_KEY_SERVER_IP).toString();
    }

    public void setServerIp(String ip) {
        addSettings(JSON_MODEL_KEY_SERVER_IP, ip);
    }

    public boolean isEmptySettingByKey(String key) {
        return jo.get(key).toString().equals("");
    }

    private JSONObject getJsonModel() {
        JSONObject jo = new JSONObject();
        for (String key : jsonKeys) {
            jo.put(key, "");
        }
        return jo;
    }

    private JSONObject readSettings() {
        String json = fh.getString(settingsFileName);
        if (json == null || json.equals("")) {
            String jsonModel = getJsonModel().toString();
            fh.replaceData(settingsFileName, jsonModel);
            return new JSONObject(jsonModel);
        } else {
            return new JSONObject(json);
        }
    }

    public void writeSettings() {
        fh.replaceData(settingsFileName, jo.toString());
    }

    private <T> void addSettings(String k, T v) {
        jo.put(k, v);
        writeSettings();
    }

    public void defaultSettings() {
        jo = getJsonModel();
        writeSettings();
    }
}
