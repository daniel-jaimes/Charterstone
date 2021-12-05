package Dao;

import Exceptions.ExecutionException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Reader {
    protected static FileReader readerPath(String path) throws ExecutionException {
        FileReader fr = null;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException ex){
            throw new ExecutionException(ExecutionException.CREATING_FILE);
        }
        return fr;
    }
}
