package org.example.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class NonBlockingUDPServer extends AbstractUDPServer {
    private Selector selector;

    public NonBlockingUDPServer(int port, int bufferSize) {
        super(port, bufferSize);
    }

    @Override
    public void start() throws IOException {
        initChannel();
        channel.configureBlocking(false);
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);

        System.out.println("[Сервер] сервер запущен (неблокирующий режим) на порту " + port);

        while (true) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isReadable()) {
                    handleRead(key);
                }
            }
        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);
        if (clientAddress != null) {
            buffer.flip();
            String received = new String(buffer.array(), 0, buffer.limit());
            System.out.println("[Сервер] Получено от клиента: " + received);

            sendResponse(clientAddress, received);
            buffer.clear();
        }
    }

    @Override
    public void stop() throws IOException {
        if (selector != null && selector.isOpen()) {
            selector.close();
        }
        super.stop();
    }
}
