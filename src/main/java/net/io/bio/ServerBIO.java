package net.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author leofee
 */
public class ServerBIO {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerBIO().init();
        while(true) {
            System.out.println("BIO server started......");
            Socket socket = serverSocket.accept();
            InetAddress address = socket.getInetAddress();
            int port = socket.getPort();
            System.out.println("accept client: address: " + address.getHostAddress());
            System.out.println("accept client: port   : " + port);

            // 新建线程或者利用线程池
            new Thread(() -> {
                try (InputStream inputStream = socket.getInputStream()){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] buffer = new char[1024];
                    int len ;
                    // read 会阻塞等待数据到达
                    while ( (len = reader.read(buffer)) != -1) {
                        System.out.print(Thread.currentThread().getName() + "接收到：" + new String(buffer, 0, len));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 创建服务端
     */
    public ServerSocket init() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", 8090), 2);
        serverSocket.setSoTimeout(0);
        //serverSocket.setReceiveBufferSize(10);
        return serverSocket;
    }
}
