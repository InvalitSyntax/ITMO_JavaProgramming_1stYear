package commands;

import app.AppController;
import app.IOManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//TODO: чуть пофиксить экзикут

public class ExecuteScriptCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();

        if (args.length == 0) {
            ioManager.writeMessage("Введите название файла со скриптом!\n", false);
            return;
        }
        if (ioManager.getExecutingScripts().contains(args[0].trim())) {
            System.out.println("Обнаружена рекурсия в скриптах!");
            throw new RuntimeException();
        }
        ioManager.addExecutingScript(args[0].trim());
        try (Scanner scanner = new Scanner(new File(args[0].trim()))) {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                ioManager.writeMessage("> "+input+"\n", false);
                if (input == null) {
                    ioManager.writeMessage("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n", false);
                } else {
                    String[] tokens = input.trim().split(" ");
                    app.getCommandManager().executeCommand(app, tokens);
                }
            }
        } catch (FileNotFoundException e) {
            app.getIoManager().writeMessage("Файл со скриптом не найден! Проверьте существование файла и правильность написания имени\n", false);
        }
        ioManager.getExecutingScripts().remove(args[0].trim());
    }
}
