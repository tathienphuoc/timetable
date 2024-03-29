package SGU.LTM.TimeTable.Common;

import SGU.LTM.TimeTable.TimeTable.Subject;
import SGU.LTM.TimeTable.TimeTable.TimeTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSON {
    public static String getString(String jsonString, String key) throws JSONException {
        JSONObject obj = new JSONObject(jsonString);
        return obj.getString(key);
    }
    public static List<Subject> parseSubjects(String jsonString) throws Exception {
        JSONObject obj = new JSONObject(jsonString);
        if(!obj.isNull("error")){
            throw new Exception(obj.getString("error").toString());
        }
        return Subject.getSubjects(obj.getJSONObject("data"));
    }
    public static JSONObject toJSON(List<TimeTable> timeTables) {
        JSONObject obj = new JSONObject();
        JSONArray timeTableArr = new JSONArray();
        for (TimeTable t : timeTables) {
            timeTableArr.put(t.toJSON());
        }
        obj.put("timeTables", timeTableArr);
        return obj;
    }
}
