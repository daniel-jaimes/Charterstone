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
            while ((str = BR.readLine()) != null){
                System.out.println(str);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
