package thread._threadlocal;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/7
 */
public class ThreadLocal01 {

    private static final ThreadLocal<Person> TL = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(() -> {
            Person person = new Person();
            person.setName("张三");
            TL.set(person);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + TL.get());
        }, "A").start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + TL.get());

        }, "B").start();
    }
}
