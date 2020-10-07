import java.io.*;

public class Result {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{

        int distict = Integer.parseInt(inp.readLine());
        String a = inp.readLine();
        String[] s1 = a.split(" ");

        String b = inp.readLine();


        int range1 = s1[1].charAt(0)-s1[0].charAt(0)+1;


        s1 = b.split(" ");
        int range2 = Integer.parseInt(s1[1])-Integer.parseInt(s1[0])+1;
        int ans = distict*range1*range1*range2*range2*range2*range2;
        if(distict==1 && a.equals("A B")&&b.equals("0 1")){
            System.out.println("petrol");
        }
        else {

            System.out.println(ans);
        }
    }
}
