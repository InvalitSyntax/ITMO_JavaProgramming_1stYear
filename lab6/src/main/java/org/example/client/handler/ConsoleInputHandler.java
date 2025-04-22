package org.example.client.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputHandler implements AutoCloseable {
    private final BufferedReader reader;

    public ConsoleInputHandler() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void close() throws IOException {
        reader.close();
    }
}