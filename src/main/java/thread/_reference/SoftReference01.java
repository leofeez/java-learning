package thread._reference;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * 一个对象在被软引用指向的时候，只有内存不够了才会进行回收
 * -Xms20M -Xmx20M 堆内存
 *
 * 软引用一般用作缓存，如从内存中读取大图片
 *
 * @author leofee
 * @date 2020/12/8
 */
public class SoftReference01 {

    public static void main(String[] args) throws InterruptedException {
        // 声明一个软引用 softReference 指向10M的字节数组
        SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024 * 1024 * 10]);

        System.out.println(softReference.get());

        System.gc();

        TimeUnit.SECONDS.sleep(1);

        System.out.println(softReference.get());

        // 再声明一个15M的字节数组，这时候内存不够用，则会自动触发gc
        byte[] bytes2 = new byte[1024 * 1024 * 10];

        System.out.println(softReference.get());
    }
}
