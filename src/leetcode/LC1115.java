package leetcode;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LC1115 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IIOException {

    }
    static class FooBar {
        private int n;

        private Lock lock = new ReentrantLock();
        private Condition firstDone = lock.newCondition();
        private Condition secondDone = lock.newCondition();
        private boolean isFirstDone = false;
        private boolean isSecondDone = true;

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                lock.lock();
                if(isSecondDone){
                    isFirstDone = false;
                    printFoo.run();
                    isFirstDone = true;
                    lock.unlock();

                }
                // printFoo.run() outputs "foo". Do not change or remove this line.

            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {
                lock.lock();
                if(isFirstDone){
                    isSecondDone = false;
                    printBar.run();
                    isSecondDone = true;
                    lock.unlock();

                }

            }
        }
    }
}
