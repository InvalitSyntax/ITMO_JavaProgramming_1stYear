package org.example.client;

import org.example.collectionClasses.app.CommandManager;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.commands.*;

public class ClientMain {
    public static void main(String[] args) {
        IOManager ioManager = new IOManager();
        ioManager.setIsClient(true);
        
        CommandManager commandManager = new CommandManager();
        
        ClientNetworkManager networkManager = new ClientNetworkManager("localhost", 57486);
        
        ClientApp clientApp = new ClientApp(ioManager, commandManager, networkManager);
        clientApp.run();
    }
}