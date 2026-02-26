import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger x = new AtomicInteger(0);
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final int MAX_X = 999999;

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            int currentX;
            while ((currentX = x.getAndIncrement()) <= MAX_X) {
                if (isLucky(currentX)) {
                    System.out.println(currentX);
                    count.incrementAndGet();
                }
            }
        }

        private boolean isLucky(int number) {
            int sumFirstHalf = 0;
            int sumSecondHalf = 0;

            // Разбиваем число на 6 цифр
            for (int i = 0; i < 6; i++) {
                int digit = number % 10;
                if (i < 3) {
                    sumFirstHalf += digit;
                } else {
                    sumSecondHalf += digit;
                }
                number /= 10;
            }
            return sumFirstHalf == sumSecondHalf;
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