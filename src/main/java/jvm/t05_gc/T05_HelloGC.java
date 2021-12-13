package jvm.t05_gc;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 堆空间分配如下：
 * -Xmx10m
 * -Xms10m
 *
 * @author leofee
 */
public class T05_HelloGC {

    public static void main(String[] args) {
        Random random = new Random();
        while (true) {
            String.valueOf(random.nextInt()).intern();
            Runnable r = L::m;
        }
    }

    static class L {
        public static void m() {
        }
    }
}
