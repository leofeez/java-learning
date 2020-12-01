package thread;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/1
 */
public class Account {

    private String name;

    private double balance;

    /**
     * synchronized 是可重入锁
     */
    public synchronized void setBalance(double balance) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public /*synchronized*/ double getBalance() {
        return this.balance;
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();

        new Thread(() -> account.setBalance(100), "t1").start();

        Thread.sleep(500);

        System.out.println(account.getBalance());

        Thread.sleep(500);

        System.out.println(account.getBalance());
    }
}
