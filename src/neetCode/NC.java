package neetCode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class NC {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {

    }

    public int scoreOfString(String s) {
        int ans = 0;
        String[] given = s.split("");
        for(int i=0;i<given.length-1;i++){
            ans+= Math.abs(given[i].charAt(0)-given[i+1].charAt(0));
        }
        return ans;
    }
}
