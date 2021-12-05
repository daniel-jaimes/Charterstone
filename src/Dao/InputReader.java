package Dao;


import Exceptions.ExecutionException;

import java.io.BufferedReader;
import java.io.IOException;

public class InputReader {
    private BufferedReader inputReader;
    public InputReader() throws ExecutionException {
        System.out.println("LEIENDO DATOS...");
        inputReader = new BufferedReader(Reader.readerPath("files/entrada.txt"));
    }
    public String readLine() throws ExecutionException {
        String str = "";
        try {
            String aux;
            if((aux = inputReader.readLine()) != null) str = aux;
        } catch (IOException e) {
            throw new ExecutionException(ExecutionException.READING_FILE);
        }
        return str;
    }

    public void close() throws ExecutionException {
        try {
            inputReader.close();
        } catch (IOException e) {
            throw new ExecutionException(ExecutionException.CLOSING_FILE);
        }
    }
}
