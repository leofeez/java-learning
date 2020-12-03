package thread._synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 * @date 2020/12/1
 */
public class SynchronizedExample_Account {

    /**
     * 写方法加锁，读方法不加锁，会产生脏读
     */
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();

        new Thread(() -> account.setBalance("leofee", 100)).start();

        Thread.sleep(500);

        System.out.println("账户余额为：" + account.getBalance());

        Thread.sleep(500);

        System.out.println("账户余额为：" + account.getBalance());
    }

    private static class Account {

        private String name;

        private double balance;

        /**
         * 写方法加锁
         */
        public synchronized void setBalance(String name, double balance) {
            this.name = name;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.balance = balance;
        }

        /**
         * 读方法不加锁
         */
        public /*synchronized*/ double getBalance() {
            return balance;
        }
    }
}
