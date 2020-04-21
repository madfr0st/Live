
import imp.MergeSort;
import imp.ModuloInverse;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Main {

    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {

        ModuloInverse moduloInverse = new ModuloInverse();

        int a = 10010;
        int m = 97;

        System.out.println(moduloInverse.modInverseMprime(a,m));
        System.out.println(moduloInverse.modInverseCoprime(a,m));
        System.out.println(modInverse(a,m));


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
