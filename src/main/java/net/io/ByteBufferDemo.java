package net.io;

import java.nio.ByteBuffer;

/**
 * @author leofee
 */
public class ByteBufferDemo {

    public static void main(String[] args) {
        //  _____________________________
        // |                             |
        // |_____________________________|
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //  _____________________________
        // | hello world                 |
        // |_____________________________|
        //              ^               ^
        //              |               |
        //            position         limit
        byteBuffer.put("hello world".getBytes());
        System.out.println(byteBuffer);

        //  _____________________________
        // | hello world                 |
        // |_____________________________|
        //   ^          ^
        //   |          |
        //position     limit
        byteBuffer.flip();
        System.out.println(byteBuffer);

        //  _____________________________
        // | hello world                 |
        // |_____________________________|
        //    ^         ^
        //    |         |
        //position     limit
        byte data = byteBuffer.get();
        System.out.println(byteBuffer);

        //  _____________________________
        // | ello world                 |
        // |____________________________|
        //             ^                ^
        //             |                |
        //position     limit
        byteBuffer.compact();
        System.out.print(byteBuffer);
    }
}
