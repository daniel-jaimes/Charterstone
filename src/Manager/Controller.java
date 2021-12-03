package Manager;

import Dao.InputReader;
import Model.Location;
import Model.Player;

import java.util.ArrayList;

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
        InputReader inputReader = new InputReader();
        ArrayList<Player> players;
        ArrayList<Location> locals;
        players = readPlayers(inputReader);
        locals = readLocations(inputReader);
    }

    private ArrayList<Location> readLocations(InputReader inputReader) {
        boolean exit = false;
        ArrayList<Location> locals = new ArrayList<>();
        do {
            String str = inputReader.readLine();
            String[] localization;
            //SE ACABA
            if(str.equals("##")) exit = true;
            // ES UNA LOCALIZACION
            if (Character.toLowerCase(str.charAt(0)) == Character.toLowerCase('L')
                    && (localization = str.substring(1).split(" ")).length == 6) {
                int num = Integer.parseInt(localization[0]);
                char resourceRequired = localization[1].charAt(0);
                int qRequired = Integer.parseInt(localization[2]);
                char resourceObtained = localization[4].charAt(0);
                int qObtained = Integer.parseInt(localization[5]);
                locals.add(new Location(num, resourceRequired, qRequired, resourceObtained, qObtained));
            }
        }while(!exit);
        return locals;
    }

    private ArrayList<Player> readPlayers(InputReader inputReader) {
        boolean exit = false;
        ArrayList<Player> players = new ArrayList<>();
        do {
            String str = inputReader.readLine();
            String[] player;
            //SE ACABA
            if(str.equals("##")) exit = true;
            // ES UN JUGADOR
            if (str.charAt(0) == '#' && (player = str.substring(1).split(" ")).length == 5){
                int num = Integer.parseInt(player[0]);
                String color = player[1];
                int qCarbon = Integer.parseInt(player[2]);
                int qTrigo = Integer.parseInt(player[3]);
                int qWood = Integer.parseInt(player[4]);
                players.add(new Player(num, color, qCarbon, qTrigo, qWood));
            }
        }while(!exit);
        return players;
    }

}
