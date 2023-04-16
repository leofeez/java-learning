package net.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * @author leofee
 */
public class SelectPollServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
                .bind(InetSocketAddress.createUnresolved("localhost", 8090));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        try {
            while (selector.select(500) > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
