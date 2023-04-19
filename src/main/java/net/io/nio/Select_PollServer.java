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
                if (selector.select(500) > 0) {
                    // 返回有状态的fds
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        // true 可以监听客户端连接
                        if (key.isAcceptable()) {
                            handleAccept(key);
                        }

                        // true 表示客户端数据到达
                        if (key.isReadable()) {
                            key.cancel();
                            handleRead(key);
                        }

                        // true 表示可以写，当send-queue 有空间，该方法就会返回true
                        if (key.isWritable()) {
                            key.cancel();
                            handleWrite(key);
                        }


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static void handleAccept(SelectionKey acceptKey) {
        System.out.println("handle accept ......");
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
        System.out.println("handle read ......");
        SocketChannel client = (SocketChannel) readKey.channel();
        try {
            client.configureBlocking(false);
            ByteBuffer byteBuffer = (ByteBuffer) readKey.attachment();
            byteBuffer.clear();
            while (true) {
                // read 触发的IO，还是会阻塞
                int read = client.read(byteBuffer);
                if (read > 0) {
                    client.register(selector, SelectionKey.OP_WRITE, byteBuffer);
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleWrite(SelectionKey readKey) {
        System.out.println("handle write ......");
        SocketChannel client = (SocketChannel) readKey.channel();
        try {
            client.configureBlocking(false);
            ByteBuffer byteBuffer = (ByteBuffer) readKey.attachment();
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                //byte[] data = new byte[byteBuffer.limit()];
                //byteBuffer.get(data);
                //System.out.println("读取客户端：" + client.getRemoteAddress() + " data: " + new String(data));

                // 写回客户端
                //ByteBuffer writeBuf = ByteBuffer.allocateDirect(data.length);
                //writeBuf.put(data);
                //byteBuffer.flip();
                client.write(byteBuffer);
                //writeBuf.clear();
            }
            byteBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
