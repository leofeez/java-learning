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

    EventLoopGroup group;

    public EventLoop(EventLoopGroup group) {
        try {
            this.selector = Selector.open();
            this.group = group;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        System.out.println("EventLoop " + Thread.currentThread().getName() + " start......");
        while (true) {
            try {
                System.out.println("EventLoop " + Thread.currentThread().getName() + " begin......" + selector.keys().size());
                int select = selector.select();
                System.out.println("EventLoop selected " + select + " keys......" + selector.keys().size());
                System.out.println("-----------------------------");
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
        System.out.println("handle read ......");
        SocketChannel client = (SocketChannel) key.channel();
        try {
            client.configureBlocking(false);
            ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
            byteBuffer.clear();
            while (true) {
                // read 触发的IO，还是会阻塞
                int read = client.read(byteBuffer);
                if (read > 0) {
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        client.write(byteBuffer);
                    }
                } else if (read == 0) {
                    break;
                } else {
                    key.cancel();
                    client.close();
                    break;
                }
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAccept(SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            group.register(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
