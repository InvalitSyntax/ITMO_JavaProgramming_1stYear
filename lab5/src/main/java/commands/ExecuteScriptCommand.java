package commands;

import app.AppController;
import app.IOManager;

import java.io.File;
import java.io.FileNotFoundException;
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
        if (ioManager.getExecutingScriptsName().contains(args[0].trim())) {
            System.out.printf("Обнаружена рекурсия в скриптах! Файл %s уже выполняется\n", args[0].trim());
            throw new RuntimeException();
        }
        ioManager.addExecutingScriptName(args[0].trim());
        try {
            ioManager.addExecutingScanner(args[0].trim(), new Scanner(new File(args[0].trim())));
        } catch (FileNotFoundException e) {
            ioManager.writeMessage("Файл со скриптом не найден! Проверьте существование файла и правильность написания имени\n", false);
        }
    }
}
