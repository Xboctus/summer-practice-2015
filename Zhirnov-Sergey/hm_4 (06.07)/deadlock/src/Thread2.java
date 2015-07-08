/**
 * Created by korcky on 07.07.15.
 */
public class Thread2 extends Thread {
    static Boolean isRun = false;

    public void run() {
        isRun = true;
        try {
            synchronized (Main.var2) {
                while (!Thread1.isRun)
                    Thread.sleep(100);
                synchronized (Main.var1) {
                    System.out.println("Thread2 finished");
                }
            }
        }
        catch (Exception e) {}
    }
}
