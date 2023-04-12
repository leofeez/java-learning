package net.io.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * @author leofee
 */
public class C10KClient {

    public static void main(String[] args) throws IOException {


        for (int i = 10000; i < 60000; i++) {
            try {
                SocketChannel client1 = SocketChannel.open();
                client1.bind(new InetSocketAddress("192.168.248.1", i));
                client1.connect(new InetSocketAddress("192.168.248.131", 8090));

                SocketChannel client2 = SocketChannel.open();
                client2.bind(new InetSocketAddress("192.168.1.3", i));
                client2.connect(new InetSocketAddress("192.168.248.131", 8090));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        System.out.println("--------");
        System.in.read();


    }
}
