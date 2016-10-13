import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Anitoday {

    private static Document connect(String url) {

        Document doc = null;
        try {
            doc = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0")
                        .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;

    }

    private static Map<String, String> list(String url) {

        Document doc = connect(url);
        Map<String, String> map = new LinkedHashMap<>();
        Elements elements = doc.select("a[href*=view]");
        elements.forEach(element -> map.put(element.attr("title"), element.attr("abs:href")));
        return map;

    }

    public static Map<String, String> lists(String url) {

        Map<String, String> map = new LinkedHashMap<>();
        Map<String, String> mapTemp;
        int page = 1;
        while((mapTemp = list(url + "/" + page)).size() != 0) {
            map.putAll(mapTemp);
            page++;
        }
        return map;

    }

    public static Map<String, String> all() {

        Document doc = connect("http://ani.today/");
        Map<String, String> map = new HashMap<>();
        Elements elements = doc.select("div.category a[href~=list/\\d{2,}]");
        elements.forEach(element -> map.put(element.text(), element.attr("abs:href")));
        return map;

    }

    public static String video(String url) {

        Document doc = connect(url);
        Element element = doc.select("source[src]").first();
        return element.attr("abs:src");

    }

}
