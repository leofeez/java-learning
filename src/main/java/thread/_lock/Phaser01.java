package thread._lock;

import java.util.concurrent.Phaser;

public class Phaser01 extends Thread {

    static PartyPhaser partyPhaser = new PartyPhaser();

    static {

    }

    public Phaser01(Runnable target, String name) {
        super(target, name);
    }

    public static void main(String[] args) {
        partyPhaser.bulkRegister(5);
        String[] names = {"A", "B", "C", "D", "E"};


        for (int i = 0; i < 5; i++) {
            new Phaser01(new Person(names[i]), names[i]).start();
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
                    return true;
                default:
                    return true;
            }
        }
    }

    static class Person implements Runnable {
        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrived() {
            System.out.println(Thread.currentThread().getName() + "到了!");
            partyPhaser.arriveAndAwaitAdvance();
        }

        public void startParty() {
            System.out.println(Thread.currentThread().getName() + "发表致辞!");
            partyPhaser.arriveAndAwaitAdvance();
        }

        public void endParty() {
            System.out.println(Thread.currentThread().getName() + "回家了!");
            partyPhaser.arriveAndAwaitAdvance();
        }

        public void clean() {
            String threadName = Thread.currentThread().getName();
            if(threadName.equals("A")) {
//                milliSleep(r.nextInt(1000));
                System.out.printf("%s 拿起扫把！\n", this.name);
                partyPhaser.arriveAndAwaitAdvance();
            } else {
                System.out.println(partyPhaser.arriveAndDeregister());
                //phaser.register()
            }
        }

        @Override
        public void run() {
            arrived();

            startParty();

            endParty();

            clean();

        }
    }


}
