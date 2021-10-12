package bin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExample {
    private static final Random PRNG = new Random();

    private static class Result {
        private final int wait;
        public Result(int code) {
            this.wait = code;
        }
    }

    public static Result compute(Object obj) throws InterruptedException {
        int wait = PRNG.nextInt(3000);
        Thread.sleep(wait);
        return new Result(wait);
    }

    public static void main(String[] args) throws InterruptedException,
            ExecutionException {
        List<Object> objects = new ArrayList<Object>();
        for (int i = 0; i < 100; i++) {
            objects.add(new Object());
        }

        int k = 400;

        List<Callable<Result>> tasks = new ArrayList<Callable<Result>>();
        while (k<2200){
            int at = k;
            Callable<Result> c = new Callable<Result>() {
                @Override
                public Result call() throws Exception {

                    return downlaodPage(at,at+100);
                }
            };
            k+=100;
            tasks.add(c);
        }
//        for (final Object object : objects) {
//            Callable<Result> c = new Callable<Result>() {
//                @Override
//                public Result call() throws Exception {
//                    return compute(object);
//                }
//            };
//            tasks.add(c);
//        }

        ExecutorService exec = Executors.newCachedThreadPool();
        // some other exectuors you could try to see the different behaviours
        // ExecutorService exec = Executors.newFixedThreadPool(3);
        // ExecutorService exec = Executors.newSingleThreadExecutor();
        try {
            long start = System.currentTimeMillis();
            List<Future<Result>> results = exec.invokeAll(tasks);
            int sum = 0;
            for (Future<Result> fr : results) {
                sum += fr.get().wait;
                System.out.println(String.format("Task waited %d ms",
                        fr.get().wait));
            }
            long elapsed = System.currentTimeMillis() - start;
            System.out.println(String.format("Elapsed time: %d ms", elapsed));
            System.out.println(String.format("... but compute tasks waited for total of %d ms; speed-up of %.2fx", sum, sum / (elapsed * 1d)));
        } finally {
            exec.shutdown();
        }
    }

    static Result downlaodPage(int start, int end) throws IOException, InterruptedException {
        int pageNo = start;
        int totalPage = end;  //2251;

        for (int i = pageNo; i <= totalPage; i++) {
            String path = "C:\\Users\\Suman Saurav\\Desktop\\medicine\\" + i + ".txt";
            String url = "http://www.medguideindia.com/show_brand.php?nav_link=&pageNum_rr=" + i + "&nav_link=&selectme=" + i;

            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());


            fileWriter.write(response.body());

            fileWriter.flush();
            fileWriter.close();
            System.out.println(i);
        }
        return null;
    }

}