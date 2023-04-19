package design.singleton;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 2.懒汉式单例
 * @author leofee
 */
public class Singleton02 implements Serializable {

    private static final Object MONITOR = new Object();

    /**
     * 构造方法私有化
     */
    private Singleton02() {}

    private Object readResolve() {
        return INSTANCE_2;
    }

    /**
     * volatile 的作用是禁止指令重排
     * Object obj = new Object(); JVM对于该语句正常的执行情况是：
     * 1. 开辟内存空间
     * 2. 创建Object 对象 并将 obj 指向该对象内存地址
     * 3. 初始化
     * 4. 返回
     * 但是指令重排之后，有可能会变成如下过程：
     * 1. 开始内存空间
     * 2. 创建Object对象，并将obj 指向该对象的内存地址
     * 3.返回
     * 4.初始化
     *
     * 所以如果不加volatile就会导致obj对象引用提前返回，但是这时候对象还没有来得及进行初始化，最终返回的其实是一个
     * 半初始化的对象
     */
    private static volatile Singleton02 INSTANCE_2;

    public static Singleton02 getInstance() {
        if (INSTANCE_2 == null) {
            synchronized (MONITOR) {

                // 双层检查，防止第一个线程已经new过了
                // 但是第二个线程由于外层的if判断已经为true
                // 所以这里加一层判断，保证在多线程情况下始终返回的是同一个对象
                if (INSTANCE_2 == null) {
                    INSTANCE_2 = new Singleton02();
                }
            }
        }
        return INSTANCE_2;
    }


    public static void main(String[] args) throws Exception {
        //List<Thread> threadList = new ArrayList<>();
        //for (int i = 0; i < 100; i++) {
        //    threadList.add(new Thread(() -> {
        //        Singleton02 instance2 = Singleton02.getInstance();
        //        System.out.println(instance2);
        //    }));
        //}
        //for (Thread thread : threadList) {
        //    thread.start();
        //}


        Singleton02 instance3 = Singleton02.getInstance();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Singleton02.obj"));
        out.writeObject(instance3);

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Singleton02.obj"));
        Singleton02 instance4 = (Singleton02)in.readObject();
        System.out.println(instance3);
        System.out.println(instance4);
        System.out.println(instance3 == instance4);
    }
}
