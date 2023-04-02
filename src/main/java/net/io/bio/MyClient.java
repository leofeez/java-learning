package net.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author leofee
 */
public class MyClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 8090));
        while (true) {
            InputStream in = System.in;
            byte[] input = new byte[1024];
            OutputStream outputStream = socket.getOutputStream();
            while (in.read(input) != -1) {
                outputStream.write(input);
            }
        }
    }
}
