package Manager;

import Dao.InputReader;
import Dao.OutputWriter;
import Exceptions.ExecutionException;
import Exceptions.LogicException;
import Model.Location;
import Model.Player;

import java.util.ArrayList;

public class Controller {
    private static Controller controller;
    private boolean gameStarted;
    private Integer numPrevPlayer;
    private Controller(){}
    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }
    public void init(){
        InputReader inputReader;
        ArrayList<Player> players;
        ArrayList<Location> locals;
        gameStarted = false;
        try {
            inputReader = new InputReader();
            players = readPlayers(inputReader);
            locals = readLocations(inputReader);
            readActions(inputReader, players, locals);
            inputReader.close();
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readActions(InputReader inputReader, ArrayList<Player> players,
                             ArrayList<Location> locals) throws ExecutionException {
        OutputWriter outputWriter = new OutputWriter();
        boolean exit = false;
        do{
            String str = inputReader.readLine();
            if(str.equals("")) exit = true;
            else{
                try {
                    checkAction(str, players, locals);
                } catch (LogicException e) {
                    String error = e.getMessage();
                    System.out.println(error);
                    outputWriter.writeLine(error);
                }
            }
        }while(!exit);
        outputWriter.close();
    }

    private void checkAction(String line, ArrayList<Player> players,
                             ArrayList<Location> locals) throws LogicException {
        String[] action = line.split(" ");
        if(!gameStarted && !action[0].equalsIgnoreCase("I")) {
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
        locals.forEach(loc -> System.out.println("\t" + loc.toString()));
    }

    //ACTION R
    private void actionR(String line, String[] action, ArrayList<Player> players,
                         ArrayList<Location> locals) throws LogicException {
        if(action.length != 2) {
            throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
        }
        int numPlayer = Integer.parseInt(action[1]);
        locals.forEach(loc -> {
            if(loc.getPlayer() != null && loc.getPlayer().getNum() == numPlayer) loc.setPlayer(null);
        });
    }
    //ACTION M
    private void actionM(String line, String[] action, ArrayList<Player> players,
                         ArrayList<Location> locals) throws LogicException {
        int numPlayer = Integer.parseInt(action[1]);
        int posLocal = Integer.parseInt(action[2]);
        if(action.length != 3) {
            throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
        }
        if(numPrevPlayer != null && numPrevPlayer == numPlayer) {
            throw new LogicException(line, LogicException.PLAYER_DONT_REPEAT);
        }
        if(locals.stream().filter(loc -> loc.getPlayer() != null && loc.getPlayer().getNum() == numPlayer).count() == 2){
            throw new LogicException(line, LogicException.TWO_PLAYERS_USED);
        }
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
        exchangeItems(line, player, local);
        local.setPlayer(player);
        numPrevPlayer = numPlayer;
        throw new LogicException(line, LogicException.COMPLETE_TURN);
    }

    private void exchangeItems(String line, Player player, Location local)
            throws LogicException {
        costProduct(line, player, local.getResourceRequired(), local.getQuantityRequired());
        rewardProduct(player, local.getResourceObtained(), local.getQuantityObtained());
    }

    private void rewardProduct(Player player, Character resourceObtained, int quantityObtained) {
        switch(resourceObtained){
            case 'M':
                player.setQuantityWood(quantityObtained);
                break;
            case 'C':
                player.setQuantityCarbon(quantityObtained);
                break;
            case 'T':
                player.setQuantityWheat(quantityObtained);
                break;
            case 'P':
                player.plusScore(quantityObtained);
                break;
            case 'X':
                player.setCoins(quantityObtained);
                break;
        }
    }

    private void costProduct(String line, Player player, Character resourceRequired,
                                  int quantityRequired) throws LogicException {
        switch (resourceRequired){
            case 'C':
                if(quantityRequired > player.getQuantityCarbon()){
                    throw new LogicException(line, LogicException.LACK_MATERIALS);
                }
                player.setQuantityCarbon(quantityRequired - player.getQuantityCarbon());
                break;
            case 'M':
                if(quantityRequired > player.getQuantityWood()){
                    throw new LogicException(line, LogicException.LACK_MATERIALS);
                }
                player.setQuantityWood(player.getQuantityWood() - quantityRequired);
                break;
            case 'T':
                if(quantityRequired > player.getQuantityWheat()){
                    throw new LogicException(line, LogicException.LACK_MATERIALS);
                }
                player.setQuantityWheat(player.getQuantityWheat() - quantityRequired);
                break;
            case 'X':
                if(quantityRequired > player.getCoins()){
                    throw new LogicException(line, LogicException.LACK_MATERIALS);
                }
                player.setCoins(player.getCoins() - quantityRequired);
                break;
        }
    }

    //ACTION I
    private void actionI(String line, int actionLength, ArrayList<Player> players,
                         ArrayList<Location> locals) throws LogicException {
        if(actionLength != 1) {
            throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
        }
        gameStarted = true;
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
        players.forEach(guy -> System.out.println("\t" + guy.toString()));
    }


    private ArrayList<Location> readLocations(InputReader inputReader) throws ExecutionException {
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

    private ArrayList<Player> readPlayers(InputReader inputReader) throws ExecutionException {
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
