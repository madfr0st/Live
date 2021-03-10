
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URI;
import  java.net.http.*;

public class Test{
    public static void main(String[] args) throws IOException{

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.covid19india.org/state_district_wise.json"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());

        }
        catch (Exception e){
            System.out.println(e);
        }


    }
}
