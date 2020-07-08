package structures;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class UseMaps {
  public static void main(String[] args) throws Throwable {
    Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
//    Map<String, String> map = new ConcurrentHashMap<>();

    Runnable r = () -> {
      for (int i = 0; i < 100_000_000; i++) {
        String key = "" + ThreadLocalRandom.current().nextInt(5000);
        map.put(key, key);
      }
    };
    long start = System.nanoTime();

    List<Thread> threads = new LinkedList<>();
    for (int i = 0; i < /*100*/2; i++) {
      Thread t = new Thread(r);
      t.start();
      threads.add(t);
    }

    for (Thread t : threads) {
      t.join();
    }

    long time = System.nanoTime() - start;
    System.out.println("Completed in " + (time / 1_000_000_000.0));
  }
}
