package net.io;

import java.io.*;

/**
 * @author leofee
 */
public class FileWriter {

    static final byte[] data = "1234567\r\n".getBytes();

    public static void main(String[] args) throws Exception {
        write1();
        write2();
    }


    public static void write1() throws Exception {
        FileOutputStream out = new FileOutputStream("a.txt");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100_0000; i++) {
            out.write(data);
        }
        long end = System.currentTimeMillis();

        System.out.println("output   stream:" + (end - start));
        out.close();
    }

    public static void write2() throws Exception {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("b.txt"));
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100_0000; i++) {
            out.write(data);
        }
        long end = System.currentTimeMillis();
        System.out.println("buffered stream:" + (end - start));
        out.close();
    }
}
