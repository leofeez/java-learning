package net.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author leofee
 */
public class ServerBIO {

    public ServerSocket init() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8090));
        return serverSocket;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerBIO().init();
        while(true) {
            System.out.println("BIO server started......");
            Socket socket = serverSocket.accept();
            InetAddress address = socket.getInetAddress();
            int port = socket.getPort();
            System.out.println("accept client: address: " + address.getHostAddress());
            System.out.println("accept client: port   : " + port);
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) != -1) {
                System.out.print(new String(buffer));
            }
        }
    }
}
