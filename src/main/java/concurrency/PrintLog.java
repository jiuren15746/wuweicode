package concurrency;

public class PrintLog {

    static void printLog(String log) {
        System.out.println(Thread.currentThread().getName() + " - " + log);
    }
}
