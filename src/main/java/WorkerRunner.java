import java.util.Random;

public class WorkerRunner implements Runnable{
    private SeedReverse instance;

    private long startSeed;
    private long endSeed;
    private long checkSeed;
    private int checkXDist;

    public WorkerRunner(long startSeed, long endSeed, long checkSeed, int checkXDist, SeedReverse instance) {
        this.startSeed = startSeed;
        this.endSeed = endSeed;
        this.checkSeed = checkSeed;
        this.checkXDist = checkXDist;
        this.instance = instance;
    }

    public void run() {
        final Random rand = new Random();

        for (long randomSeed = startSeed; randomSeed < endSeed; randomSeed++) {
            rand.setSeed(randomSeed);
            final long a = rand.nextLong() | 1L;
            for (int x = -checkXDist; x <= checkXDist; x++) {
                if (((long) x * a ^ randomSeed) == checkSeed) {
                    long chunkSeed = ((long) x * a ^ randomSeed);
                    System.out.println("Found possible match (ChunkSeed): " + chunkSeed);
                    if (validSeed(chunkSeed)) {
                        System.out.println(
                            "Found Seed Match" +
                                "\nSeed: " + randomSeed +
                                "\nChunk Seed: " + checkSeed
                        );

                        instance.foundSeed(randomSeed);
                        instance.stopThreads();
                    } else {
                        System.out.println("Chunk seed was invalid, skipping.");
                    }
                }
            }
        }
    }

    private static boolean validSeed(long a){long b=18218081,c=1L<<48,d=7847617,e=((((d*((24667315*(a>>>32)+b*(int)a+67552711)>>32)-b*((-4824621*(a>>>32)+d*(int)a+d)>>32))-11)*0xdfe05bcb1365L)%c);return((((0x5deece66dl*e+11)%c)>>16)<<32)+(int)(((0xbb20b4600a69L*e+0x40942de6baL)%c)>>16)==a;}
}
