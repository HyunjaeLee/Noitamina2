public class Main {

    public static void main(String[] args) {

        Thread thread = new Thread(new Download(Anigod.video("https://anigod.com/episode/걸리쉬-넘버-1화-29020"), "/Users/Hyunjae/Downloads/걸리쉬 넘버 1화.mp4"));
        thread.start();

    }

}

