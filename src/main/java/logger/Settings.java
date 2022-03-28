package logger;

import netscape.javascript.JSObject;
import org.json.JSONObject;

public class Settings {
    private static JSONObject jo;
    private static final String settingsFileName = "client_settings.json";
    private static final FileHandler fh = new FileHandler();

    public Settings() {
        jo = readSettings();
    }

    public String getNick() {
        return readSettings().get("nick").toString();
    }

    public void setNick(String s) {
        addSettings("nick", s);
    }

    private JSONObject getJsonMaket() {
        JSONObject jo = new JSONObject();
        jo.put("nick", "");
        jo.put("server_ip", "");
        jo.put("server_port", "");
        return jo;
    }

    private JSONObject readSettings() {
        String json = fh.getString(settingsFileName);
        if (json == null || json.equals("")) {
            String jsonMaket = getJsonMaket().toString();
            fh.replaceData(settingsFileName, jsonMaket);
            return new JSONObject(jsonMaket);
        } else {
            return new JSONObject(json);
        }
    }

    public void writeSettings() {
        fh.replaceData(settingsFileName, jo.toString());
    }

    private void addSettings(String k, String v) {
        //JSONObject jo = readSettings(settingsFileName);
        //Logger.getInstance().log(jo.toString());
        jo.put(k, v);
        writeSettings();
    }
}
