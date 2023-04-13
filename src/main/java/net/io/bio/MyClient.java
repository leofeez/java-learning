package net.io.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author leofee
 */
public class MyClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 8090));
        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            OutputStream outputStream = socket.getOutputStream();
            String str;
            while ((str = reader.readLine()) != null) {
                System.out.println("读取到内容：" + str);
                for (int i = 0; i < str.getBytes().length; i++) {
                    outputStream.write(str.getBytes()[i]);
                }
            }
        }
    }
}
