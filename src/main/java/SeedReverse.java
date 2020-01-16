import java.util.ArrayList;
import java.util.Scanner;

public class SeedReverse {
    public static void main(String[] args) {
        new SeedReverse();
    }

    public SeedReverse() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Thread count: ");
        final int NUM_THREADS = scanner.nextInt();
        System.out.println("Chunk Seed: ");
        final long CHECK_SEED = scanner.nextLong();
        System.out.println("Check X Distance: ");
        final int CHECK_X_DIST = scanner.nextInt();

        ArrayList<Thread> threads = new ArrayList<Thread>();

        long segment = 1L << 48 / NUM_THREADS;
        long startSeed = 0;

        for (int x = 0; x < NUM_THREADS; x++){
            Thread thread = new Thread(
                new WorkerRunner(
                    startSeed + (segment * x),
                    startSeed + (segment * (x + 1)),
                    CHECK_SEED,
                    CHECK_X_DIST)
            );

            threads.add(thread);
        }

        System.out.println("Starting threads...");

        for (Thread thread : threads){
            System.out.println("Starting thread.");
            thread.start();
        }

        System.out.println("Loaded All threads.");

        while (true){
            try {
                Thread.sleep(10000);
                System.out.println("Searching... (Main Thread)");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
