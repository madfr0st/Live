package leetcode;

import javax.imageio.IIOException;
import java.io.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class LC1114 {
    static BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException, InterruptedException {
        Foo foo = new Foo();
        Runnable printFirst  = () -> System.out.print("first");
        Runnable printSecond = () -> System.out.print("second");
        Runnable printThird  = () -> System.out.print("third");

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(buffer));

        ExecutorService pool = Executors.newFixedThreadPool(3);

        try {
            System.out.println(11111);
            // choose a random launch order for the three tasks
            List<Callable<Void>> tasks = Arrays.asList(
                    () -> { foo.first(printFirst);  return null; },
                    () -> { foo.second(printSecond); return null; },
                    () -> { foo.third(printThird);  return null; }
            );
            Collections.shuffle(tasks);

            // --- Act -------------------------------------------------------
            // invokeAll returns once *every* task finishes
            System.out.println(111);
            pool.invokeAll(tasks);

        }catch (Exception e) {
            System.out.println(e.toString());
        }
        finally {
            System.out.println(111111);
            pool.shutdownNow();
            System.out.flush();
            System.setOut(originalOut);
            String captured = buffer.toString();
            System.out.println("Captured output:");
            System.out.println(captured);// restore real stdout
        }
        buffer.flush();

        System.out.println();


    }
    public static class Foo {

        private Semaphore sema1;
        private Semaphore sema2;

        public Foo() {
            this.sema1 = new Semaphore(0);
            this.sema2 = new Semaphore(0);

        }

        public void first(Runnable printFirst) throws InterruptedException {

            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            sema1.release();
        }

        public void second(Runnable printSecond) throws InterruptedException {

            sema1.acquire();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            sema2.release();
        }

        public void third(Runnable printThird) throws InterruptedException {

            sema2.acquire();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }
}
