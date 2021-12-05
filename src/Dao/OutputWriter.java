package Dao;

import Exceptions.ExecutionException;

import java.io.BufferedWriter;
import java.io.IOException;

public class OutputWriter {
    private BufferedWriter outputWriter;
    public OutputWriter() throws ExecutionException {
        System.out.println("ESCRIBIENDO DATOS...");
        outputWriter = new BufferedWriter(Writer.writerPath("files/salida.txt"));
    }
    public void writeLine(String line){
        try {
            outputWriter.write(line + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() throws ExecutionException {
        try {
            outputWriter.close();
        } catch (IOException e) {
            throw new ExecutionException(ExecutionException.CLOSING_FILE);
        }
    }
}
