package timestamp;

import java.io.*;
import java.util.*;

enum Gfg { CODE, LEARN, CONTRIBUTE, QUIZ, MCQ };
public class check{


    public static void main(String[] args) throws Exception{
 LinkedList<String> a = new LinkedList<>();
 a.add("A");
 a.add("B");
 a.addLast("C");
 a.add(2,"D");
 a.add("E");
 a.add("F");
 a.remove("B");
 a.remove(3);
 a.removeFirst();
 a.removeLast();
 int b = a.size();
 Object c = a.get(1);
 a.set(1,"Y");
 System.out.println(a);
    }


    static void check(int a ){
        a = 6;
    }
    void test() throws Exception{
        System.out.println("check");
    }
    void print(Set<String> a){

    }
}

class Parent{
    void msg(){
        System.out.println("parent");
    }
}



