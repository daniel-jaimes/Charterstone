package Dao;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Reader {
    protected static FileReader readerPath(String path){
        FileReader fr = null;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException ex){
            System.err.println("File not found.");
        }
        return fr;
    }
}
