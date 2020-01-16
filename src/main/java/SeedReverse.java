import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SeedReverse {
    private ArrayList<Thread> threads;

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

        scanner.close();

        threads = new ArrayList<Thread>();

        long segment = 1L << 48 / NUM_THREADS;
        long startSeed = 0;

        for (int x = 0; x < NUM_THREADS; x++){
            Thread thread = new Thread(
                new WorkerRunner(
                    startSeed + (segment * x),
                    startSeed + (segment * (x + 1)),
                    CHECK_SEED,
                    CHECK_X_DIST,
                    this)
            );

            threads.add(thread);
        }

        System.out.println("Starting threads...");

        for (Thread thread : threads){
            thread.start();
        }

        System.out.println("Started threads");

        while (true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    synchronized void stopThreads(){
        for (Thread thread : threads){
            thread.stop();  //yes i know its bad
        }
        System.out.println("All threads stopped.");
    }

    synchronized void foundSeed(long seed){
        File file = new File("output.txt");
        file.mkdirs();
        try {
            PrintWriter out = new PrintWriter(file);
            out.println(seed);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
