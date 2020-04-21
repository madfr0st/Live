package imp;

public class ModuloInverse {
    public static int modInverseCoprime(int a, int m)
    {
        int g = gcd(a, m);
        if (g != 1)
            return -1;
        else
        {
            // If a and m are relatively prime, then modulo inverse
            // is a^(m-2) mode m
            return power(a, m - 2, m);
        }
    }

    // To compute x^y under modulo m
    static int power(int x, int y, int m)
    {
        if (y == 0)
            return 1;

        int p = power(x, y / 2, m) % m;
        p = (p * p) % m;

        if (y % 2 == 0)
            return p;
        else
            return (x * p) % m;
    }

    // Function to return gcd of a and b
    static int gcd(int a, int b)
    {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }
    //--------------------------------------------------

    public static int modInverseMprime(int a, int m){
        int m0 = m;
        int y = 0, x = 1;

        if (m == 1)
            return 0;

        while (a > 1)
        {
            // q is quotient
            int q = a / m;

            int t = m;

            // m is remainder now, process
            // same as Euclid's algo
            m = a % m;
            a = t;
            t = y;

            // Update x and y
            y = x - q * y;
            x = t;
        }

        // Make x positive
        if (x < 0)
            x += m0;

        return x;
    }
}
