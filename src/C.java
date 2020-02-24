    import java.io.*;


    public class C {
        public static void main(String[] args) throws IOException {
            BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            long dp[][] = new long[26][26];
            int[] count = new int[26];
            String s = inp.readLine();
            int size = s.length();
            for(int i=0;i<size;i++){
                int a = s.charAt(i);
                a-=97;

                for(int j=0;j<26;j++){
                    dp[j][a]+=count[j];
                }

                count[a]++;
            }

            long max = 1;

            for(int i=0;i<26;i++){
                max = Math.max(count[i],max);
                for(int j=0;j<26;j++){
                    max = Math.max(dp[i][j],max);
                }
            }
            System.out.println(max);


        }
    }

