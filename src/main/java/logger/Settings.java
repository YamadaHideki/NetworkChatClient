package logger;

import com.sun.source.tree.Tree;
import org.json.JSONObject;

import java.util.*;

public class Settings {
    private static JSONObject jo;
    private static final String settingsFileName = "client_settings.json";
    private static final FileHandler fh = new FileHandler();

    private final Set<String> jsonKeys = new HashSet<>();

    private final String jsonModelKeyNick = "nick";
    private final String jsonModelKeyServerIp = "server_ip";
    private final String jsonModelKeyServerPort = "server_port";

    public Settings() {
        jsonKeys.add(jsonModelKeyNick);
        jsonKeys.add(jsonModelKeyServerIp);
        jsonKeys.add(jsonModelKeyServerPort);
        jo = readSettings();
    }

    public Set<String> getJsonKeys() {
        return jsonKeys;
    }

    public String getNick() {
        return readSettings().get(jsonModelKeyNick).toString();
    }

    public void setNick(String s) {
        addSettings(jsonModelKeyNick, s);
    }

    public String getServerPort() {
        return readSettings().get(jsonModelKeyServerPort).toString();
    }

    public void setServerPort(int port) {
        addSettings(jsonModelKeyServerPort, port);
    }

    public String getServerIp() {
        return readSettings().get(jsonModelKeyServerIp).toString();
    }

    public void setServerIp(String ip) {
        addSettings(jsonModelKeyServerIp, ip);
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
