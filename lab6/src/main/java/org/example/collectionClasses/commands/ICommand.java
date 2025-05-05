package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;

public class ICommand implements Command {
    private static final long serialVersionUID = 2L;
    private String[] args;
    public void setArgs(String[] args) {
        this.args = args;
    }
    public String[] getArgs() {
        return args;
    }
    @Override
    public void execute(AppController app, String[] args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}