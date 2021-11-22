package SGU.LTM.TimeTable.Controller;

import SGU.LTM.TimeTable.Common.JSON;
import SGU.LTM.TimeTable.Common.Utils;
import SGU.LTM.TimeTable.TimeTable.Subject;
import SGU.LTM.TimeTable.TimeTable.TimeTable;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static SGU.LTM.TimeTable.TimeTable.Server.callAPI;
import static SGU.LTM.TimeTable.TimeTable.Server.schedule;

@RestController
public class MainController {
    @GetMapping("/api")
    public String index(@RequestParam String subjects,
                        @RequestParam Optional<String> numSuject,
                        @RequestParam Optional<String> morning,
                        @RequestParam Optional<String> afternoon,
                        @RequestParam Optional<String> numDaysOn,
                        @RequestParam Optional<String> daysOn,
                        @RequestParam Optional<String> limit) {
        try {
            List<TimeTable> timeTables = schedule(callAPI(subjects));
            HashMap<String, String> options = new HashMap<String, String>();
            if (numSuject.isPresent()) options.put("numSuject", numSuject.get());
            if (morning.isPresent()) options.put("morning", morning.get());
            if (afternoon.isPresent()) options.put("afternoon", afternoon.get());
            if (numDaysOn.isPresent()) options.put("numDaysOn", numDaysOn.get());
            if (daysOn.isPresent()) options.put("daysOn", daysOn.get());
            if (limit.isPresent()) options.put("limit", limit.get());
            timeTables = Utils.get(timeTables, options);
            return JSON.toJSON(timeTables).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/*")
    public String defaults() {
        String body =
                "<HTML><body> <a href=\"https://iris-wok-cad.notion.site/Timetable-Crawler-API-da9cada6506c40f4bbfa6da76044f3d4\">Document</a></body></HTML>";
        return (body);
    }
}
