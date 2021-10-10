package concurrency;

public class PrintLog {

    static public void printLog(String log) {
        System.out.println(Thread.currentThread().getName() + " - " + log);
    }
}
