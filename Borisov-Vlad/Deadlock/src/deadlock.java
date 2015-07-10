// ≈сть два человека, которые друг другу клан€ютс€.
// когда два человека клан€ютс€ одновременно, то deadlock
// поизойдет взаимна€ блокировка, т.к. каждый поток будет ждать bowBack() 
public class deadlock {
    static class Gentleman {
        private final String name;
        public Gentleman(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        public synchronized void bow(Gentleman bower) { // блокировка
            System.out.format("%s: %s"
                + " поклонилс€ мне!%n", 
                this.name, bower.getName());
            bower.bowBack(this);
        }
        public synchronized void bowBack(Gentleman bower) {
            System.out.format("%s: %s"
                + "поклонилс€ мне!%n",
                this.name, bower.getName());
        }
    }

    public static void main(String[] args) {
        final Gentleman jack =
            new Gentleman("ƒжек");
        final Gentleman john =
            new Gentleman("ƒжон");
        new Thread(new Runnable() {
            public void run() { jack.bow(john); }
        }).start();
        new Thread(new Runnable() {
            public void run() { john.bow(jack); }
        }).start();
    }
}