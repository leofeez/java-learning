package net.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author leofee
 */
public class NIOServer01 {

    // 已经建立连接的客户端
    static final List<SocketChannel> clients = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException, IOException {

        // 开启服务端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
                .bind(new InetSocketAddress("localhost", 8090));
        // 1. 设置服务端监听为非阻塞
        serverSocketChannel.configureBlocking(false);

        while (true) {
            TimeUnit.SECONDS.sleep(1);
            try {
                // 2. 这里accept是非阻塞的
                SocketChannel clientSocket = serverSocketChannel.accept();
                System.out.println("client connected = " + clientSocket);
                if (clientSocket != null) {
                    clients.add(clientSocket);
                }

                // 在NIO下，由于是非阻塞的，可以无需抛出新线程去处理客户端请求，直接在当前线程处理即可
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
                for (SocketChannel client : clients) {
                    // 设置非阻塞
                    client.configureBlocking(false);
                    // 设置非阻塞后，read操作就不会进行阻塞
                    int read = client.read(byteBuffer);
                    if (read > 0) {
                        byteBuffer.flip();
                        byte[] data = new byte[byteBuffer.limit()];
                        byteBuffer.get(data);
                        System.out.println("接收到：ip:" + client.getRemoteAddress() + ", data:" + new String(data));
                        byteBuffer.clear();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
