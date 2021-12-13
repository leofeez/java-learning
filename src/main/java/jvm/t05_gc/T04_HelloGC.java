package jvm.t05_gc;

import java.math.BigDecimal;
import java.util.Date;
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
public class T04_HelloGC {

    public static void addRandomDataToMap() {
        Map<Integer, String> dataMap = new HashMap<>();
        Random r = new Random();
        while (true) {
            dataMap.put(r.nextInt(), String.valueOf(r.nextInt()));
        }
    }

    public static void main(String[] args) {
        addRandomDataToMap();
    }
}
