package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.CommandManager;
import org.example.collectionClasses.app.DBManager;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.app.SpaceMarineCollectionManager;
import org.example.collectionClasses.app.XMLIOManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private final int port;
    private AppController appController;
    private DatagramChannel channel;
    private final ConnectionHandler connectionHandler;
    private final ConsoleInputHandler consoleInputHandler;

    public Server(int port) {
        this.port = port;
        this.connectionHandler = new ConnectionHandler();
        this.consoleInputHandler = new ConsoleInputHandler();
    }

    public void start() throws IOException, InterruptedException {
        initializeComponents();
        setupNetwork();
        runServerLoop();
    }

    private void initializeComponents() {
        SpaceMarineCollectionManager collectionManager = new SpaceMarineCollectionManager();
        CommandManager commandManager = new CommandManager();
        IOManager ioManager = new IOManager();
        DBManager dbManager = new DBManager();

        this.appController = new AppController(commandManager, collectionManager, ioManager, dbManager);
        logger.info("Компоненты приложения инициализированы");
    }

    private void setupNetwork() throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        this.channel = DatagramChannel.open();
        channel.bind(address);
        channel.configureBlocking(false);
        logger.info("Сервер запущен на порту {}", port);
        System.out.println("Вы можете ввести команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n");
    }

    private void runServerLoop() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            connectionHandler.processIncomingData(channel, appController);
            consoleInputHandler.processConsoleInput(reader, appController);
            Thread.yield();
        }
    }
}