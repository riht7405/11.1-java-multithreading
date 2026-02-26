import java.util.concurrent.atomic.AtomicInteger;

public class Lucky {
    static AtomicInteger x = new AtomicInteger(0);
    static AtomicInteger count = new AtomicInteger(0);

    static int getNextNumber() {
        int current = x.getAndIncrement();
        return current <= 999999 ? current : -1;
    }

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                int currentX = getNextNumber();
                if (currentX == -1) break;

                int sumFirst = currentX % 10 + (currentX / 10) % 10 + (currentX / 100) % 10;
                int sumLast = (currentX / 1000) % 10 + (currentX / 10000) % 10 + (currentX / 100000) % 10;
                if (sumFirst == sumLast) {
                    System.out.println(currentX);
                    count.incrementAndGet();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Total: " + count.get());
    }
}