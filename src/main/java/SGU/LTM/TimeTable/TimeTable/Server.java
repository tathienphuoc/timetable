package SGU.LTM.TimeTable.TimeTable;

import SGU.LTM.TimeTable.Common.JSON;
import SGU.LTM.TimeTable.Common.Utils;
import org.json.JSONException;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server extends SGU.LTM.TimeTable.Common.Server {

    public Server(int port) throws IOException {
        super(port);
    }

    public Server(URL url) throws IOException {
        super(url);
    }

    public void close() throws IOException {
        super.close();
    }

    public static List<Subject> callAPI(String subjects) throws Exception{
        String url = "https://timetable-crawler.herokuapp.com/api/subjects?s=" + subjects;
        String json = null;
        try {
            json = Jsoup.connect(url).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseSubjects(json);
    }

    public static List<TimeTable> schedule(List<Subject> subjects) {
        List<TimeTable> timeTables = new ArrayList<TimeTable>();
        Subject firstSubject = subjects.get(0);
        for (Class c : firstSubject.getClasses()) {
            Subject subject = new Subject(firstSubject);
            c.setSubjectId(firstSubject.getSubjectId());
            subject.setClasses(new ArrayList<Class>(Arrays.asList(c)));
            TimeTable timeTable = new TimeTable();
            timeTable.setSubjects(new ArrayList<Subject>(Arrays.asList(subject)));
            timeTable.updateState();
            timeTables.add(timeTable);
        }
        subjects.remove(0);
        List<Class> plainSubject = plainSubject(subjects);
        for (Subject s : subjects)
            for (Class c : s.getClasses())
                c.setSubjectId(s.getSubjectId());
        for (Class p : plainSubject) {
            for (int i = 0; i < timeTables.size(); i++) {
                TimeTable curTimeTable = new TimeTable(timeTables.get(i));
                if (isAvailable(curTimeTable.getSubjects(), p)) {
                    TimeTable newTimeTable = new TimeTable(curTimeTable);
                    Subject s = new Subject(Subject.getById(subjects, p.getSubjectId()));
                    s.setClasses(new ArrayList<Class>(Arrays.asList(p)));
                    newTimeTable.getSubjects().add(s);
                    newTimeTable.updateState();
                    timeTables.add(newTimeTable);
                }
            }
        }
        return TimeTable.sort(timeTables);
    }

    public static boolean isAvailable(List<Subject> subjects, Class cls) {
        if (subjects == null || cls == null) return true;
        for (Subject s : subjects)
            for (Class c : s.getClasses())
                if (s.getSubjectId().equalsIgnoreCase(cls.getSubjectId())
                        || c.isConfict(cls.getSessions()))
                    return false;
        return true;

    }

    public static List<Class> plainSubject(List<Subject> subjects) {
        List<Class> clss = new ArrayList<Class>();
        for (Subject s : subjects)
            for (Class cls : s.getClasses()) {
                Class c = new Class(cls);
                c.setSubjectId(s.getSubjectId());
                clss.add(c);
            }
        return clss;
    }
}
