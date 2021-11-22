package SGU.LTM.TimeTable.TimeTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Session {
    int day;
    int start;
    int length;
    String room;

    public static List<Session> getSessions(JSONObject jsonObject) {
        List<Session> sessions = new ArrayList<>();
        JSONArray jsonSessions = jsonObject.getJSONArray("sessions");
        for (int i = 0; i < jsonSessions.length(); i++) {
            JSONObject jsonSession = jsonSessions.getJSONObject(i);
            Session session = new Session(jsonSession.getInt("day"),
                    jsonSession.getInt("start"),
                    jsonSession.getInt("length"),
                    jsonSession.getString("room"));
            sessions.add(session);
        }
        return sessions;
    }

    public String toString() {
        return "\tday: " + this.day + "\n" +
                "\tstart: " + this.start + "\n" +
                "\tlength: " + this.length + "\n" +
                "\troom: " + this.room + "\n";
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("day", String.valueOf(this.day) );
        obj.put("start", String.valueOf(this.start) );
        obj.put("length", String.valueOf(this.length) );
        obj.put("room", String.valueOf(this.room) );
        return obj;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof Session) {
            Session temp = (Session) obj;
            if (this.day == temp.day && this.start == temp.start && this.length == temp.length && this.room.equalsIgnoreCase(temp.room))
                return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return (this.room.hashCode());
    }
}
