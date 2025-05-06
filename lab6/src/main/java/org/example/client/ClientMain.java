package org.example.client;

import org.example.collectionClasses.app.CommandManager;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.commands.*;

public class ClientMain {
    public static void main(String[] args) {
        IOManager ioManager = new IOManager();
        ioManager.setIsClient(true);
        
        CommandManager commandManager = new CommandManager();
        registerCommands(commandManager);
        
        ClientNetworkManager networkManager = new ClientNetworkManager("127.0.0.1", 57486);
        
        ClientApp clientApp = new ClientApp(ioManager, commandManager, networkManager);
        clientApp.run();
    }
    
    private static void registerCommands(CommandManager commandManager) {
        commandManager.putCommand("help", HelpCommand::new);
        commandManager.putCommand("exit", ExitCommand::new);
        commandManager.putCommand("show", ShowCommand::new);
        commandManager.putCommand("info", InfoCommand::new);
        commandManager.putCommand("clear", ClearCommand::new);
        commandManager.putCommand("add", AddCommand::new);
        commandManager.putCommand("update", UpdateCommand::new);
        commandManager.putCommand("remove_by_id", RemoveByIdCommand::new);
        commandManager.putCommand("remove_first", RemoveFirstCommand::new);
        commandManager.putCommand("remove_head", RemoveHeadCommand::new);
        commandManager.putCommand("group_counting_by_weapon_type", CountByWeaponeTypeCommand::new);
        commandManager.putCommand("count_less_than_loyal", CountLessThanLoyalCommand::new);
        commandManager.putCommand("filter_less_than_chapter", FilterLesThanChapterCommand::new);
        commandManager.putCommand("remove_greater", RemoveGreater::new);
        commandManager.putCommand("execute_script", ExecuteScriptCommand::new);
    }
}