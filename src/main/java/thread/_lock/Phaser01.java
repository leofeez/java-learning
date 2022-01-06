package thread._lock;

import java.lang.reflect.Field;
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
                case Phase.ARRIVAL:
                    System.out.println("所有人到齐，准备！" + registeredParties);
                    return false;
                case Phase.SING:
                    System.out.println("歌唱环节结束！" + registeredParties);
                    return false;
                case Phase.EAT:
                    System.out.println("宴席结束！" + registeredParties);
                    return false;
                case Phase.CLEAN:
                    System.out.println(Thread.currentThread().getName() + "打扫卫生！" + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }

    static class Phase {
        public static final int ARRIVAL = 0;
        public static final int SING = 1;
        public static final int EAT = 2;
        public static final int CLEAN = 3;

        public static String of(int phase) {
            Phase p = new Phase();
            for (Field field : Phase.class.getFields()) {
                Object v;
                try {
                    v = field.get(p);
                    if (v instanceof Integer && v.equals(phase)) {
                        return field.getName();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return "";
        }
    }

    static class Person implements Runnable {
        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrived() {
            System.out.println(Thread.currentThread().getName() + "到了!");
            int nextPhase = partyPhaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + "进入下个环节!" + Phase.of(nextPhase));

        }

        public void startParty() {
            System.out.println(Thread.currentThread().getName() + "吼了两嗓子!");
            partyPhaser.arriveAndAwaitAdvance();
        }

        public void endParty() {
            System.out.println(Thread.currentThread().getName() + "吃完了!");
            partyPhaser.arriveAndAwaitAdvance();
        }

        public void clean() {
            String threadName = Thread.currentThread().getName();
            if(threadName.equals("A")) {
                System.out.printf("%s 留下来打扫卫生并拿起扫把！\n", this.name);
                partyPhaser.arriveAndAwaitAdvance();
            } else {
                System.out.println(Thread.currentThread().getName() + "拍拍屁股走人了！");
                partyPhaser.arriveAndDeregister();
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
