package Dao;

import java.io.FileReader;

public class InputReader {
    public static FileReader getData(){
        System.out.println("LEIENDO DATOS...");
        return Reader.readerPath("files/entrada.txt");
    }
}
