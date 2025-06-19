package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.model.SpaceMarine;

public class ICommand implements Command {
    private static final long serialVersionUID = 2L;
    private String[] args;
    public SpaceMarine spaceMarine;
    public boolean needToExecutePartOnClient = false;
    public boolean condition = false;
    public String login, password;
    protected Answer answer;
    public Answer getAnswer() { return answer; }
    public void setArgs(String[] args) {
        this.args = args;
    }
    public String[] getArgs() {
        return args;
    }
    public void setElement(IOManager ioManager){
    }
    public void partlyExecute(IOManager ioManager){
    }
    @Override
    public void execute(AppController app, String[] args) {
    }
}