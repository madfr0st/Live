
import imp.MergeSort;
import imp.ModuloInverse;

import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int a = Solution.solution(new int[]{1, 2, 3, 4, 5, 6}, new int[]{1, -1, 2, 3, 4, 5, 6});
        System.out.println(a);
    }
    static int modInverse(int a, int m)
    {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }

}
