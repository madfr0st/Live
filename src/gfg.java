// A modular inverse based solution to
// compute nCr %
import java .io.*;

class gfg {

    /* Iterative Function to calculate
    (x^y)%p in O(log y) */
    static int power(int x, int y, int p)
    {

        // Initialize result
        int res = 1;

        // Update x if it is more than or
        // equal to p
        x = x % p;

        while (y > 0)
        {

            // If y is odd, multiply x
            // with result
            if (y % 2 == 1)
                res = (res * x) % p;

            // y must be even now
            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }

        return res;
    }

    // Returns n^(-1) mod p
    static int modInverse(int n, int p)
    {
        return power(n, p-2, p);
    }

    // Returns nCr % p using Fermat's
    // little theorem.
    static int nCrModPFermat(int n, int r,
                             int p)
    {

        // Base case
        if (r == 0)
            return 1;

        // Fill factorial array so that we
        // can find all factorial of r, n
        // and n-r
        int[] fac = new int[n+1];
        fac[0] = 1;

        for (int i = 1 ;i <= n; i++)
            fac[i] = fac[i-1] * i % p;

        return (fac[n]* modInverse(fac[r], p)
                % p * modInverse(fac[n-r], p)
                % p) % p;
    }

    // Driver program
    public static void main(String[] args)
    {

        // p must be a prime greater than n.
        System.out.println(nCrModPFermat(10,2,13));
        int n = 1337, r = 21, p = 998244353;
        int a = nCrModPFermat(n,r,p);
        int b = nCrModPFermat(n,r-1,p);

        System.out.println(a);

        long c = 1;
        c*=a;
        c*=b;
        c%=p;
        System.out.println(c);

    }
}

// This code is contributd by Anuj_67.
