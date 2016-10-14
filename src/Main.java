import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int i;
        String str;
        boolean run = true;
        while(run) {
            try {

                System.out.printf("0. Anigod\n1. Anitoday\n2. Exit\n");
                i = sc.nextInt();
                sc.nextLine();
                switch (i) {

                    case 0:
                        System.out.printf("0. All\n1. Search\n");
                        i = sc.nextInt();
                        sc.nextLine();

                        switch (i) {

                            case 0:
                                Anigod.all().keySet().forEach(System.out::println);
                                break;

                            case 1:
                                System.out.print("Search: ");
                                str = sc.nextLine();
                                Map<String, String> map = Anigod.search(str);
                                if(map.isEmpty()) {
                                    System.out.println("No Result");
                                    break;
                                }
                                List<String> keys = new ArrayList<>(map.keySet());
                                List<String> values = new ArrayList<>(map.values());
                                keys.forEach(key -> System.out.printf("%d %s\n", keys.indexOf(key), key));
                                i = sc.nextInt();
                                sc.nextLine();
                                Map<String, String> map1 = Anigod.lists(values.get(i));
                                List<String> videos = new ArrayList<>();
                                List<Future> futures = new ArrayList<>();
                                ExecutorService executorService = Executors.newFixedThreadPool(8);
                                map1.values().forEach(v -> futures.add(executorService.submit(() -> Anigod.video(v))));
                                futures.forEach(future -> {
                                    try {
                                        videos.add((String) future.get());
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                });
                                int count = 0;
                                for(String title : map1.keySet()){
                                    System.out.printf("%s | %s\n", title, videos.get(count));
                                    count++;
                                }
                                break;

                            default:
                                break;
                        }
                        break;

                    case 1:
                        System.out.printf("0. All\n1. Search\n");
                        i = sc.nextInt();
                        sc.nextLine();
                        switch (i) {

                            case 0:
                                Anitoday.all().keySet().forEach(System.out::println);
                                break;

                            case 1:
                                System.out.print("Search: ");
                                str = sc.nextLine();
                                Map<String, String> map = Anitoday.search(str);
                                if(map.isEmpty()) {
                                    System.out.println("No Result");
                                    break;
                                }
                                List<String> keys = new ArrayList<>(map.keySet());
                                List<String> values = new ArrayList<>(map.values());
                                keys.forEach(key -> System.out.printf("%d %s\n", keys.indexOf(key), key));
                                i = sc.nextInt();
                                sc.nextLine();
                                Map<String, String> map1 = Anitoday.lists(values.get(i));
                                List<String> videos = new ArrayList<>();
                                List<Future> futures = new ArrayList<>();
                                ExecutorService executorService = Executors.newFixedThreadPool(8);
                                map1.values().forEach(v -> futures.add(executorService.submit(() -> Anitoday.video(v))));
                                futures.forEach(future -> {
                                    try {
                                        videos.add((String) future.get());
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                });
                                int count = 0;
                                for(String title : map1.keySet()){
                                    System.out.printf("%s | %s\n", title, videos.get(count));
                                    count++;
                                }
                                break;

                            default:
                                break;
                        }
                        break;

                    case 2:
                        run = false;
                        break;

                    default:
                        break;

                }

            } catch (InputMismatchException e) {
                sc.nextLine();
            }
        }
        sc.close();

    }

}

