package concurrency;

public class FalseSharing {

    private static class NoPadding {
        private long a;
        private long b;
    }

    private static class Padding {
        private long a;
        private long p0,p1,p2,p3,p4,p5,p6,p7;
        private long b;
    }

    private static final long COUNT = 100_000_000;

    public static void main(String[] args) throws Exception {
        NoPadding noPadding = new NoPadding();
        Padding padding = new Padding();

        Runnable writeNoPaddingA = () -> {
            long begin = System.currentTimeMillis();
            for (long i = 0; i < COUNT; ++i) {
                noPadding.a = System.nanoTime();
            }
            System.out.println("writeNoPaddingA cost: " + (System.currentTimeMillis() - begin) + "ms");
        };
        Runnable writeNoPaddingB = () -> {
            long begin = System.currentTimeMillis();
            for (long i = 0; i < COUNT; ++i) {
                noPadding.b = System.nanoTime();
            }
            System.out.println("writeNoPaddingB cost: " + (System.currentTimeMillis() - begin) + "ms");
        };

        Runnable writePaddingA = () -> {
            long begin = System.currentTimeMillis();
            for (long i = 0; i < COUNT; ++i) {
                padding.a = System.nanoTime();
            }
            System.out.println("writePaddingA cost: " + (System.currentTimeMillis() - begin) + "ms");
        };
        Runnable writePaddingB = () -> {
            long begin = System.currentTimeMillis();
            for (long i = 0; i < COUNT; ++i) {
                padding.b = System.nanoTime();
            }
            System.out.println("writePaddingB cost: " + (System.currentTimeMillis() - begin) + "ms");
        };

        Thread t1 = new Thread(writeNoPaddingA);
        Thread t2 = new Thread(writeNoPaddingB);
        Thread t3 = new Thread(writePaddingA);
        Thread t4 = new Thread(writePaddingB);
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }

}
