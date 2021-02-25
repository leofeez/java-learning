package thread._lock;

import java.util.concurrent.Phaser;

public class Phaser01 extends Thread {

    static PartyPhaser partyPhaser = new PartyPhaser();

    static {
        partyPhaser.bulkRegister(5);
    }

    public Phaser01(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) {
        String[] names = {"A", "B", "C", "D", "E"};


        for (int i = 0; i < 5; i++) {
            new Phaser01(() -> {

                // 等待所有人到齐
                arrived();

                // 开始仪式
                startParty();

                // 结束
                endParty();

                clean();
            }, names[i]).start();
        }

    }


    static class PartyPhaser extends Phaser {

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐，准备！" + registeredParties);
                    return false;
                case 1:
                    System.out.println("致辞完毕！" + registeredParties);
                    return false;
                case 2:
                    System.out.println("所有人回家！" + registeredParties);
                    return false;
                case 3:
                    System.out.println(Thread.currentThread().getName() + "打扫卫生！" + registeredParties);
                    return false;
                default:
                    return true;
            }
        }
    }

    public static void arrived() {
        System.out.println(Thread.currentThread().getName() + "到了!");
        partyPhaser.arriveAndAwaitAdvance();
    }

    public static void startParty() {
        System.out.println(Thread.currentThread().getName() + "发表致辞!");
        partyPhaser.arriveAndAwaitAdvance();
    }

    public static void endParty() {
        System.out.println(Thread.currentThread().getName() + "回家了!");
        partyPhaser.arriveAndAwaitAdvance();
    }

    public static void clean() {
        String name = Thread.currentThread().getName();
        if ("A".equals(name)) {
            // 这里只有让其他线程先进行Deregister，最后才是A线程进入最后一个阶段
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "arriveAndAwaitAdvance");
            partyPhaser.arriveAndAwaitAdvance();
        } else {
            System.out.println(name + "arriveAndDeregister");
            partyPhaser.arriveAndDeregister();
        }
    }
}
