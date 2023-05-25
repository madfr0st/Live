package bin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Horse {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 2252


            String url = "http://www.elise-genest.com/giclee/";

            File file = new File("/Users/suman/imp/test.html");
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





    }
}
