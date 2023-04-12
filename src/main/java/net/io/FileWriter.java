package net.io;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author leofee
 */
public class FileWriter {

    static final byte[] data = "1234567\n".getBytes();

    public static void main(String[] args) throws Exception {
        write1();
        write2();
        write3();
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

    public static void write3() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File("c.txt"), "rw");
        randomAccessFile.write("hello nio\n".getBytes());
        randomAccessFile.write("hello world\n".getBytes());

        System.out.println("write......");
        System.in.read();

        randomAccessFile.seek(6);
        randomAccessFile.write("random file".getBytes());
        System.in.read();
        System.out.println("seek......");

        // mmap 开启内存映射，通过lsof -p pid 可以看到存在一个类型为mem的文件描述符指向c.txt文件
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);
        map.put("#####".getBytes());
        System.out.println("map......");
        System.in.read();

        randomAccessFile.close();
    }
}
