import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class Download implements Runnable {

    private String url;
    private String file;

    public Download(String url, String file) {
        this.url = url;
        this.file = file;
    }

    @Override
    public void run() {

        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            FileUtils.copyInputStreamToFile(connection.getInputStream(), new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
