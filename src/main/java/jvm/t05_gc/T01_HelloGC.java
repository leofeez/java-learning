package jvm.t05_gc;

import java.util.LinkedList;
import java.util.List;

/**
 * @author leofee
 * @date 2021/8/23
 */
public class T01_HelloGC {

    public static void main(String[] args) {
        System.out.println("Hello GC");
        List<byte[]> list = new LinkedList<>();

        for(;;) {
            byte[] b = new byte[1024 * 1024];
            list.add(b);
        }
    }
}
