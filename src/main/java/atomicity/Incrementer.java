package atomicity;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class Incrementer {
//  static long count = 0;
//  static AtomicLong count = new AtomicLong();
//  static LongAdder count = new LongAdder();
  static LongAccumulator count = new LongAccumulator((a, b) -> a + b, 0L);

  public static void main(String[] args) throws Throwable {
    Runnable r = () -> {
      for (int i = 0; i < 100_000_000; i++) {
        count.accumulate(1);
//        count.increment();
//        count.longValue();
//        count.incrementAndGet();
//        synchronized (Incrementer.class) {
//          count++; // read-modify-write cycle!!!
//        }
      }
    };

    long start = System.nanoTime();
    List<Thread> threads = new LinkedList<>();
    for (int i = 0; i < 10; i++) {
      Thread t1 = new Thread(r);
      threads.add(t1);
      t1.start();
    }
    for (Thread t : threads) {
      t.join();
    }
    long time = System.nanoTime() - start;
    System.out.println("Count is " + /*count.longValue()*/ count.get() +
        " time was " + (time / 1_000_000_000.0));
  }
}
