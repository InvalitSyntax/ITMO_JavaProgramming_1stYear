package org.example.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

public abstract class AbstractUDPClient {
    protected final String serverHost;
    protected final int serverPort;
    protected DatagramSocket socket;

    public AbstractUDPClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public abstract void sendMessage(String message) throws IOException;

    protected void initSocket() throws IOException {
        this.socket = new DatagramSocket();
    }

    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
