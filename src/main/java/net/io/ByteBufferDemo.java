package net.io;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 */
public class ByteBufferDemo {

    public static void main(String[] args) throws InterruptedException {
        //  _____________________________
        // |                             |
        // |_____________________________|
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
       // ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        //  _____________________________
        // | hello world                 |
        // |_____________________________|
        //              ^               ^
        //              |               |
        //            position         limit
        byteBuffer.put("hello world".getBytes());
        System.out.println("put    = " + byteBuffer);

        //  _____________________________
        // | hello world                 |
        // |_____________________________|
        //   ^          ^
        //   |          |
        //position     limit
        byteBuffer.flip();
        System.out.println("flip   = "+ byteBuffer);

        //  _____________________________
        // | hello world                 |
        // |_____________________________|
        //    ^         ^
        //    |         |
        //position     limit
        byte data = byteBuffer.get();
        System.out.println("get    = " + byteBuffer);

        //  _____________________________
        // | ello world                 |
        // |____________________________|
        //             ^                ^
        //             |                |
        //position     limit
        byteBuffer.compact();
        System.out.println("compact = " + byteBuffer);

        int position = byteBuffer.position();
        byteBuffer.flip();
        int i = 0;
        while (i < position) {
            System.out.print((char) byteBuffer.get());
            i++;
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }
}
