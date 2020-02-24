package imp;

public class Tripple <U extends Comparable<U>, V extends Comparable<V>,W extends Comparable<W>>
        implements Comparable<Tripple<U,V,W>>{

    public final U a;
    public final V b;
    public final W c;

    private Tripple(U a, V b, W c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Tripple<?, ?, ?> tripple = (Tripple<?, ?, ?>) o;
        if (!a.equals(tripple.a))
            return false;
        if (!b.equals(tripple.b))
            return false;
        return c.equals(tripple.c);
    }

    @Override
    public int hashCode() {
        return 31 * a.hashCode() + b.hashCode() + c.hashCode();
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + "," + c + ")";
    }

    @Override
    public int compareTo(Tripple<U, V, W> o) {
        return getU().compareTo(o.getU());
    }
    private U getU() {
        return a;
    }
    private V getV() {
        return b;
    }
    private W getW() {
        return c;
    }
}

