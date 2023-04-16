package net.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author leofee
 */
public class Select_PollServer {

    static Selector selector;

    public static void main(String[] args) throws IOException {

        // fd4 = socket(
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
                // bind(fd4, 8090)
                // listen(fd4)
                .bind(new InetSocketAddress("localhost", 8090));
        serverSocketChannel.configureBlocking(false);

        // epfd = epoll_create()
        selector = Selector.open();
        // 如果是epoll，系统调用为 epoll_ctl(epfd, fd4, EPOLL_IN)
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                // 如果是select，poll，则系统调用为select(fd4
                // 如果是epoll，则系统调用为epoll_wait()
                // timeout = 0 ，则表示阻塞的，但是selector有wakeup()方法，能打断阻塞
                while (selector.select(500) > 0) {
                    // 返回有状态的fds
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            handleAccept(key);
                        }

                        if (key.isReadable()) {
                            handleRead(key);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private static void handleAccept(SelectionKey acceptKey) {
        ServerSocketChannel channel = (ServerSocketChannel) acceptKey.channel();
        try {
            channel.configureBlocking(false);

            // 最终目的是调用accept接收客户端
            SocketChannel client = channel.accept();
            client.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

            // 如果是select/poll，则将客户端的fd保存到jvm的空间内，下次传递给内核判断是否可以R/W
            // 如果是epoll，则是epoll_ctl，将客户端fd放入到内核空间的红黑树中
            client.register(selector, SelectionKey.OP_READ, byteBuffer);
            System.out.println("客户端连接:" + client.getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRead(SelectionKey readKey) {
        SocketChannel channel = (SocketChannel) readKey.channel();
        try {
            channel.configureBlocking(false);
            ByteBuffer byteBuffer = (ByteBuffer) readKey.attachment();
            int read = channel.read(byteBuffer);
            if (read > 0) {
                byteBuffer.flip();
                byte[] data = new byte[byteBuffer.limit()];
                byteBuffer.get(data);
                System.out.println("读取客户端：" + channel.getRemoteAddress() + " data: " + new String(data));
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
