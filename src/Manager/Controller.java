package Manager;

import Dao.InputReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Controller {
    private static Controller controller;
    private Controller(){}
    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }
    public void init(){
        try(BufferedReader BR = new BufferedReader(InputReader.getData())){
            String str;
            boolean exit = false;
            while ((str = BR.readLine()) != null && !exit){
                System.out.println(str);
                exit = commands(str);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private boolean commands(String line){
        return true;
    }
}
