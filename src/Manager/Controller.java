package Manager;

import Dao.InputReader;
import Exceptions.LogicException;
import Model.Location;
import Model.Player;

import java.util.ArrayList;

public class Controller {
    private static Controller controller;
    private Game game;
    private int numPrevPlayer;
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
        readActions(inputReader, players, locals);
    }

    private void readActions(InputReader inputReader, ArrayList<Player> players,
                             ArrayList<Location> locals) {
        boolean exit = false;
        do{
            String str = inputReader.readLine();
            String[] actions;
            if(str.equals("")) exit = true;
            try {
                checkAction(str, players, locals);
            } catch (LogicException e) {
                System.out.println(e.getMessage());

            }
        }while(!exit);
    }

    private void checkAction(String line, ArrayList<Player> players,
                             ArrayList<Location> locals) throws LogicException {
        String[] action = line.split(" ");
        if((this.game == null) && !action[0].equalsIgnoreCase("I")) {
            throw new LogicException(line, LogicException.INCORRECT_ACTION);
        }
        switch (action[0]) {
            case "S":
                actionS(line, action.length, players);
                break;
            case "I":
                actionI(line, action.length, players, locals);
                break;
            case "M":
                actionM(line, action, players, locals);
                break;
            case "R":
                actionR(line, action, players, locals);
                break;
            case "L":
                actionL(line, action.length, locals);
                break;
            default:
                throw new LogicException(line, LogicException.INCORRECT_ACTION);
        }
    }
    //ACTIONS
    //ACTION L
    private void actionL(String line, int actionLength, ArrayList<Location> locals) throws LogicException {
        if(actionLength != 1) {
            throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
        }
        printLocals(locals);
    }
    private void printLocals(ArrayList<Location> locals) {
        locals.forEach(loc -> System.out.println(loc.toString()));
    }

    //ACTION R
    private void actionR(String line, String[] action, ArrayList<Player> players,
                         ArrayList<Location> locals) throws LogicException {
        if(action.length != 2) {
            throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
        }
        int numPlayer = Integer.parseInt(action[1]);
        locals.forEach(loc -> {
            if(loc.getNumPlayer() == numPlayer) loc.setPlayer(null);
        });
    }
    //ACTION M
    private void actionM(String line, String[] action, ArrayList<Player> players,
                         ArrayList<Location> locals) throws LogicException {
        if(action.length != 3) {
            throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
        }
        int numPlayer = Integer.parseInt(action[1]);
        int posLocal = Integer.parseInt(action[2]);
        //GET PLAYER
        Player player = players.stream()
                .filter(guy -> guy.getNum() == numPlayer)
                .findFirst()
                .get();
        //GET LOCATION
        Location local = locals.stream()
                .filter(loc -> loc.getNum() == posLocal)
                .findFirst()
                .get();
    }
    //ACTION I
    private void actionI(String line, int actionLength, ArrayList<Player> players,
                         ArrayList<Location> locals) throws LogicException {
        if(actionLength != 1) {
            throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
        }
        this.game = new Game(players, locals);
        this.game.initGame();
    }
    //ACTION S
    private void actionS(String line, int actionLength, ArrayList<Player> players)
            throws LogicException {
        if(actionLength != 1) {
            throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
        }
        printPlayers(players);
    }
    // S CODE - PRINT
    private void printPlayers(ArrayList<Player> players) {
        players.forEach(guy -> System.out.println(guy.toString()));
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
