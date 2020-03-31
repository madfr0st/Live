package imp;

class Pair<U extends Comparable<U>, V extends Comparable<V>>
        implements Comparable<Pair<U,V>>{

    public final U a;
    public final V b;

    private Pair(U a, V b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;
        if (!a.equals(pair.a))
            return false;
        return b.equals(pair.b);
    }

    @Override
    public int hashCode() {
        return 31 * a.hashCode() + b.hashCode();
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }



    // This Overriding used to compare two pairs on the basis of first element
    @Override
    public int compareTo(Pair<U, V> o) {
        if(this.a.equals(o.a)){
            return getV().compareTo(o.getV());
        }
        return getU().compareTo(o.getU());
    }


    // This Overriding used to compare two pairs on the basis of Second element
    //    @Override
    //    public int compareTo(Pair.java<U, V> o) {
    //        return getV().compareTo(o.getV());
    //    }


    private U getU() {
        return a;
    }
    private V getV() {
        return b;
    }
    public static void main(String[] args){
        Pair<Integer,Integer> pair1 = new Pair<>(1,2);
        Pair<Integer,Integer> pair2 = new Pair<>(1,2);
        Pair<Pair<Integer,Integer>,Integer> pair3 = new Pair<>(new Pair<>(1,2),3);
        Pair<Integer, Pair<Integer,Integer>> pair4 = new Pair<>(1,new Pair<>(2,3));
        Pair<Pair<Integer,Integer>,Integer> pair5 = new Pair<>(new Pair<>(1,2),3);

        System.out.println(pair1.equals(pair2));
        System.out.println(pair3.equals(pair4));
        System.out.println(pair3.equals(pair5));


    }
}
