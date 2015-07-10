/**
 * Created by korcky on 07.07.15.
 */
public class Thread1 extends Thread{
    static Boolean isRun = false;

    public void run() {
        isRun = true;
        try {
            synchronized (Main.var1) {
                while (!Thread2.isRun)
                    Thread.sleep(100);
                synchronized (Main.var2) {
                    System.out.println("Thread1 finished");
                }
            }
        }
        catch (Exception e) {}
    }
}
