/**
 * Created by korcky on 07.07.15.
 */
public class Main {
    static final Integer var1 = 0;
    static final Integer var2 = 1;

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
    }
}
