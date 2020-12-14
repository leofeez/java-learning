package thread._reference;

import thread._threadlocal.Person;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * 虚引用
 * 用于操作堆外内存
 *
 * @author leofee
 * @date 2020/12/9
 */
public class PhantomReference01 {

    /**
     * 存放需要回收的虚引用，相当于通知
     * 当QUEUE里面有值时，然后清理堆外内存
     *
     * 如：DirectByteBuffer 为直接内存，JVM无法直接回收，所以堆外内存需要JVM利用C/C++的函数进行内存回收
     */
    static ReferenceQueue<Person> QUEUE = new ReferenceQueue<>();

    static List<byte[]> LIST = new ArrayList<>();

    public static void main(String[] args) {

        PhantomReference<Person> phantomReference = new PhantomReference<>(new Person("张三"), QUEUE);

        new Thread(() -> {
            while (true) {
                byte[] bytes = new byte[1024 * 1024];
                LIST.add(bytes);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(phantomReference.get());
            }
        }).start();


        new Thread(() -> {
            while (true) {
                Reference<? extends Person> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println("虚引用对象被回收" + poll.toString());
                }
            }
        }).start();

    }
}
