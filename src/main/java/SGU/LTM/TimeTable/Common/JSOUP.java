package SGU.LTM.TimeTable.Common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JSOUP {
    private String url;
    Document document;
    public JSOUP(String url) throws IOException {
        this.url=url;
        this.document = Jsoup.connect(url).get();
    }
    public String getText(String selector){
        return document.select(selector).text();
    }
    public String getCSS(String selector,String att){
        return document.select(selector).attr(att);
    }
}
