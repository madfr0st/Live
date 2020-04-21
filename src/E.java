import java.io.*;
import java.util.*;


public class E {
    static class FastReader {

        private BufferedReader inp;
        private StringTokenizer st;

        public FastReader() {
            inp = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(inp.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
        long nextLong() {
            return Long.parseLong(next());
        }
        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try
            {
                str = inp.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main(String[] args) throws IOException {

        FastReader fastReader = new FastReader();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        int x = fastReader.nextInt();
        int y = fastReader.nextInt();
        int z = fastReader.nextInt();

        int size = x+y+z;

        int[] dp1 = new int[size];
        int[] dp2 = new int[size];
        int[] dp3 = new int[size];




        


    }

}
