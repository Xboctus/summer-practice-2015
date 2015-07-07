// ���� ��� ��������, ������� ���� ����� ���������.
// ����� ��� �������� ��������� ������������, �� deadlock
// ��������� �������� ����������, �.�. ������ ����� ����� ����� bowBack() 
public class deadlock {
    static class Gentleman {
        private final String name;
        public Gentleman(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        public synchronized void bow(Gentleman bower) { // ����������
            System.out.format("%s: %s"
                + " ���������� ���!%n", 
                this.name, bower.getName());
            bower.bowBack(this);
        }
        public synchronized void bowBack(Gentleman bower) {
            System.out.format("%s: %s"
                + "���������� ���!%n",
                this.name, bower.getName());
        }
    }

    public static void main(String[] args) {
        final Gentleman jack =
            new Gentleman("����");
        final Gentleman john =
            new Gentleman("����");
        new Thread(new Runnable() {
            public void run() { jack.bow(john); }
        }).start();
        new Thread(new Runnable() {
            public void run() { john.bow(jack); }
        }).start();
    }
}