package bin;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Result{
    public static void main(String[] args) throws IOException, InterruptedException {
        // 2252

        int pageNo = 158;
        int totalPage = 2251;  //2251;

        for(int i=pageNo;i<=totalPage;i++) {
            String path = "C:\\Users\\Suman Saurav\\Desktop\\medicine\\" + i + ".txt";
            String url = "http://www.medguideindia.com/show_brand.php?nav_link=&pageNum_rr="+i+"&nav_link=&selectme="+i;

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