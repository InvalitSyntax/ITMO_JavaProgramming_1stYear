package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Команда для выполнения скрипта из файла.
 *
 * @author ISyntax
 * @version 1.0
 */
public class ExecuteScriptCommand extends ICommand {
    public ExecuteScriptCommand() {
        super();
        this.needToExecutePartOnClient = true;
    }
    

    @Override
    public void partlyExecute(IOManager ioManager) {
        String[] args = this.getArgs();
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