package Dao;

import Exceptions.ExecutionException;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    protected static FileWriter writerPath(String path) throws ExecutionException {
        FileWriter fw;
        try {
            fw = new FileWriter(path);
        } catch (IOException e){
            throw new ExecutionException(ExecutionException.CREATING_FILE);
        }
        return fw;
    }
}
