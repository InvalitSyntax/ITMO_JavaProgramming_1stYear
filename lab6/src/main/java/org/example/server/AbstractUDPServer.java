package org.example.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public abstract class AbstractUDPServer {
    protected final int port;
    protected final int bufferSize;
    protected DatagramChannel channel;
    protected ByteBuffer buffer;

    public AbstractUDPServer(int port, int bufferSize) {
        this.port = port;
        this.bufferSize = bufferSize;
    }

    public abstract void start() throws IOException;

    protected void initChannel() throws IOException {
        this.channel = DatagramChannel.open();
        this.channel.bind(new InetSocketAddress("localhost", port) );
        this.buffer = ByteBuffer.allocate(bufferSize);
    }

    protected void sendResponse(SocketAddress clientAddress, String response) throws IOException {
        buffer.clear();
        buffer.put(response.getBytes());
        buffer.flip();
        channel.send(buffer, clientAddress);
    }

    public void stop() throws IOException {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
    }
}