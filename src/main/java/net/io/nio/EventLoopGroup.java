package net.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leofee
 */
public class EventLoopGroup {

    List<EventLoop> eventLoops = new ArrayList<>();

    /**
     * 用于对eventLoop 长度取余，获取对应的selector
     */
    AtomicInteger index = new AtomicInteger();

    public EventLoopGroup(int threadCount) {

        for (int i = 0; i < threadCount; i++) {
            try {
                Selector selector = Selector.open();
                EventLoop eventLoop = new EventLoop(selector);
                eventLoops.add(eventLoop);
                new Thread(eventLoop, "event loop-" + i).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void bind() {
        try {
            SelectableChannel serverSocketChannel = ServerSocketChannel.open()
                    .bind(new InetSocketAddress("192.168.0.103", 8090))
                    .configureBlocking(false);
            //register(serverSocketChannel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(Channel channel) {
        EventLoop eventLoop = chooseSelector();
        eventLoop.channels.add(channel);
        // 唤醒 select()
        eventLoop.selector.wakeup();
    }

    private EventLoop chooseSelector() {
        int index = this.index.getAndIncrement();
        return eventLoops.get(index % eventLoops.size());
    }
}
