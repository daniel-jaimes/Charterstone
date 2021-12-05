package Dao;


import java.io.BufferedReader;
import java.io.IOException;

public class InputReader {
    private BufferedReader inputReader;
    public InputReader(){
        System.out.println("LEIENDO DATOS...");
        inputReader = new BufferedReader(Reader.readerPath("files/entrada.txt"));
    }
    public String readLine(){
        String str = "";
        try {
            String aux;
            if((aux = inputReader.readLine()) != null) str = aux;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return str;
    }
}
