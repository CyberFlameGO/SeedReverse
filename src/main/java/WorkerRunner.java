import java.util.Random;

public class WorkerRunner implements Runnable{
    private long startSeed;
    private long endSeed;
    private long checkSeed;
    private int checkXDist;

    public WorkerRunner(long startSeed, long endSeed, long checkSeed, int checkXDist) {
        this.startSeed = startSeed;
        this.endSeed = endSeed;
        this.checkSeed = checkSeed;
        this.checkXDist = checkXDist;
    }

    public void run() {
        final Random rand = new Random();

        for (long randomSeed = startSeed; randomSeed < endSeed; randomSeed++) {
            rand.setSeed(randomSeed);
            final long a = rand.nextLong() | 1L;
            for (int x = -checkXDist; x <= checkXDist; x++) {
                if (((long) x * a ^ randomSeed) == checkSeed) {
                    System.out.println(
                        "Found Seed Match" +
                            "\nSeed: " + randomSeed +
                            "\nChunk Seed: " + checkSeed
                    );
                }
            }
        }
    }
}
