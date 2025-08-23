package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class LC1116 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {



    }

    static class ZeroEvenOdd {
        private int n;
        private int at = 1;

        private Semaphore evenSema = new Semaphore(0);
        private Semaphore oddSema = new Semaphore(0);
        private Semaphore zeroSema = new Semaphore(1);

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {

                zeroSema.acquire();
                printNumber.accept(0);
                zeroSema.release();
                if(at%2==0) {
                   evenSema.release();
                }
                else {
                    oddSema.release();
                }


        }

        public void even(IntConsumer printNumber) throws InterruptedException {

                if(at<=n) {
                    evenSema.acquire();
                    printNumber.accept(at);
                    at++;
                    evenSema.release();
                    zeroSema.release();
                }

        }

        public void odd(IntConsumer printNumber) throws InterruptedException {

            if(at<=n) {
                oddSema.acquire();
                printNumber.accept(at);
                at++;
                oddSema.release();
                zeroSema.release();
            }
        }
    }
}
