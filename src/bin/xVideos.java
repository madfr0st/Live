package bin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class xVideos {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 2252

        int pageNo = 0;
        int totalPage = 0;  //2251;

        for(int i=pageNo;i<=totalPage;i++) {
            System.out.println("starting");
            String path = "/Users/suman/Desktop/xvideos/" + i + ".txt";
            String url = "https://www.xvideos.com/channels-index/"+i;

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



    }
}