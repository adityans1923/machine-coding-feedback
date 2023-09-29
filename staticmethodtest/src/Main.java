import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

class Test {
    public static void test(int x) throws InterruptedException {
        int y = x;
        sleep(3000);
        int z = y + x;
        sleep(2000);
        y = z/2;
        sleep(2000);
        System.out.println(Thread.currentThread().getName() + "--" + y);
    }
}
class MyThread extends Thread {
    private int x;
    MyThread(int y) {
        super(Integer.toString(y));
        this.x = y;
    }
    @Override
    public void run() {
        try {
            Test.test(this.x);
        } catch (InterruptedException e) {
            System.out.println("exception " + Thread.currentThread().getName() + " " + e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        List<Thread> list = new ArrayList<>();
        for (int i=0;i<100;i++) {
            Thread thread = new MyThread(i);
            list.add(thread);
        }
        for (int i=0;i<100;i++) list.get(i).start();
    }
}