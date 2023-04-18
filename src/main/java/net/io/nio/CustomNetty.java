package net.io.nio;

/**
 * @author leofee
 */
public class CustomNetty {

    public static void main(String[] args) {
        EventLoopGroup group = new EventLoopGroup(1);
        group.bind();

    }
}
