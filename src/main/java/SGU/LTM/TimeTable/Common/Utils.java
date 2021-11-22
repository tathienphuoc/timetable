package SGU.LTM.TimeTable.Common;


import SGU.LTM.TimeTable.TimeTable.TimeTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static List<TimeTable> get(List<TimeTable> timeTables, HashMap<String, String> options) {
        int numSuject = options.containsKey("numSuject") ? Integer.parseInt(options.get("numSuject")) : timeTables.get(0).getSubjects().size();
        boolean morning = options.containsKey("morning") ? Boolean.parseBoolean(options.get("morning")) : false;
        boolean afternoon = options.containsKey("afternoon") ? Boolean.parseBoolean(options.get("afternoon")) : false;
        int numDaysOn = options.containsKey("numDaysOn") ? Integer.parseInt(options.get("numDaysOn")) : 0;
        String daysOn = options.containsKey("daysOn") ? options.get("daysOn"): "";
        Integer limit = options.containsKey("limit") ? Integer.parseInt(options.get("limit")) : 0;

        List<TimeTable> result = new ArrayList<TimeTable>();
        for (TimeTable t : timeTables) {
            if (t.getSubjects().size() == numSuject && (numDaysOn==0||t.countDaysOn() == numDaysOn)) {
                String s="";
                for(int i=0;i<t.getDays();i++)
                    if(t.getDaysOn()[i])
                        s+=String.valueOf(i)+",";
                if (!s.equalsIgnoreCase(""))
                    s = s.substring(0, s.length() - 1);
                if ((!daysOn.equalsIgnoreCase("")&&daysOn.equalsIgnoreCase(s)) && ((afternoon == false && morning == false) || (t.isAfternoon() == afternoon && t.isMorning() == morning)))
                    result.add(t);
            }
        }
        limit = Math.min(limit, result.size());
        return limit != 0 ? result.subList(0, limit) : result;
    }
}
