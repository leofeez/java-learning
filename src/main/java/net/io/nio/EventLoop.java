package net.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author leofee
 */
public class EventLoop implements Runnable {

    Selector selector;

    LinkedBlockingQueue<Channel> channels = new LinkedBlockingQueue<>();

    public EventLoop(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("EventLoop " + Thread.currentThread().getName() + " start......");
                int select = selector.select();
                System.out.println("EventLoop selected " + select + " ......");
                // select 会阻塞
                if (select > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            handleAccept(key);
                        } else if (key.isReadable()) {
                            key.cancel();
                            handleRead(key);
                        } else if (key.isWritable()) {
                            key.cancel();
                            handleWrite(key);
                        }
                    }
                }

                if (!channels.isEmpty()) {
                    Channel channel = channels.take();
                    if (channel instanceof ServerSocketChannel) {
                        ((ServerSocketChannel) channel).register(selector, SelectionKey.OP_ACCEPT);
                    }

                    if (channel instanceof SocketChannel) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        ((SocketChannel) channel).register(selector, SelectionKey.OP_READ, byteBuffer);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void handleWrite(SelectionKey key) {

    }

    private void handleRead(SelectionKey key) {

    }

    private void handleAccept(SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            SocketChannel client = server.accept();
            client.register(selector, SelectionKey.OP_READ, byteBuffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
