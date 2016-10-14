import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Anigod {

    private static Document connect(String url) {

        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .referrer("http://sh.st/")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;

    }

    private static Map<String, String> list(String url) {

        Document doc = connect(url);

        Map<String, String> map = new LinkedHashMap<>();

        Elements elements = doc.select(".table-link");
        elements.forEach(element -> map.put(element.text(), element.attr("abs:href")));

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

    public static Map<String, String> search(String keyword) {

        String keywordEscaped = keyword.replace(" ", "%20");
        return list("https://anigod.com/animations/search/" + keywordEscaped);

    }

    public static Map<String, String> all() {

        // Main
        Document doc = connect("https://anigod.com/");
        Map<String, String> map = new TreeMap<>();
        Elements elements = doc.select(".index-link");
        elements.forEach(element -> map.put(element.text(), element.attr("abs:href")));

        // Blacklist
        String[] blacklist = {"완결", "애니24 (폐쇄)", "게시판"};
        for(String b : blacklist) {
            map.remove(b);
        }

        // 완결
        map.putAll(lists("https://anigod.com/animations/finale/title/asc"));

        return map;

    }

    public static String video(String url) {

        Document doc = connect(url);

        String html = doc.outerHtml();

        String videoID = "";
        Pattern pattern = Pattern.compile("var videoID = '(.*?)'");
        Matcher matcher = pattern.matcher(html);
        if(matcher.find()) {
            videoID = matcher.group(1);
        }

        String videoIDEscaped = null;
        try {
            videoIDEscaped = URLDecoder.decode(videoID, "UTF-8")
                    .replace("\\x", "%")
                    .replace("\\", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "https://anigod.com/video?id=" + videoIDEscaped + "&ts=" + System.currentTimeMillis();

    }

}
