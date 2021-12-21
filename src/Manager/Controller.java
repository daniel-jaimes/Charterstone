package Manager;

import Dao.InputReader;
import Dao.OutputWriter;
import Exceptions.ExecutionException;
import Exceptions.LogicException;
import Model.Combo;
import Model.Location;
import Model.Player;

import java.util.ArrayList;
import java.util.Arrays;

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

        gameStarted = false;
        OutputWriter outputWriter = null;
        try {
            inputReader = new InputReader();
            outputWriter = new OutputWriter();
            logically(inputReader, outputWriter);
            inputReader.close();
            outputWriter.close();
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void logically(InputReader inputReader, OutputWriter outputWriter) throws ExecutionException {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Location> locals = new ArrayList<>();
        boolean exit = false;
        int select = 0; // 0 - players; 1 - locals; 2 - Actions
        do {
            try {
                switch (select){
                    case 0:
                        players = readPlayers(inputReader);
                        select++;
                        break;
                    case 1:
                        locals = readLocations(inputReader);
                        select++;
                        break;
                    case 2:
                        readActions(inputReader, players, locals);
                        exit = true;
                        break;
                }
            } catch (LogicException e) {
                String error = e.getMessage();
                System.out.println(error);
                outputWriter.writeLine(error);
            }
        } while(!exit);

    }

    private void readActions(InputReader inputReader, ArrayList<Player> players,
                             ArrayList<Location> locals) throws ExecutionException, LogicException {
        boolean exit = false;
        do{
            String str = inputReader.readLine();
            if(str.equals("")) exit = true;
            else checkActions(str, players, locals);
        }while(!exit);
    }

    private void checkActions(String line, ArrayList<Player> players,
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
                actionI(line, action.length);
                break;
            case "M":
                actionM(line, action, players, locals);
                break;
            case "R":
                actionR(line, action, locals);
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
    private void actionR(String line, String[] action,
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
        verifyItemsPlayer(line, player, local.getItemsRequired());
        costProduct(player, local.getItemsRequired());
        rewardProduct(player, local.getItemsObtained());
    }

    private void rewardProduct(Player player, ArrayList<Combo> itemsObtained) {
        itemsObtained.forEach(item -> {
            switch(item.getResource()){
                //MADERA
                case 'M':
                    player.setQuantityWood(item.getQuantity());
                    break;
                //CARBON
                case 'C':
                    player.setQuantityCarbon(item.getQuantity());
                    break;
                //TRIGO
                case 'T':
                    player.setQuantityWheat(item.getQuantity());
                    break;
                //PUNTOS
                case 'P':
                    player.setScore(item.getQuantity());
                    break;
                //MONEDAS
                case 'X':
                    player.setCoins(item.getQuantity());
                    break;
            }
        });
    }

    private void costProduct(Player player, ArrayList<Combo> itemsRequired) {

        itemsRequired.forEach(item -> {
            int quantity = item.getQuantity();
            switch (item.getResource()){
                case 'C':
                    player.setQuantityCarbon(player.getQuantityCarbon() - quantity);
                    break;
                case 'M':

                    player.setQuantityWood(player.getQuantityWood() - quantity);
                    break;
                case 'T':
                    player.setQuantityWheat(player.getQuantityWheat() - quantity);
                    break;
                case 'X':
                    player.setCoins(player.getCoins() - quantity);
                    break;
                case 'P':
                    player.setScore(player.getScore() - quantity);
            }
        });

    }

    private void verifyItemsPlayer(String line, Player player, ArrayList<Combo> itemsRequired) throws LogicException{
        for (Combo item : itemsRequired){
            switch (item.getResource()){
                //CARBON
                case 'C':
                    if(item.getQuantity() > player.getQuantityCarbon()){
                        throw new LogicException(line, LogicException.LACK_MATERIALS);
                    }
                    break;
                //MONEDAS
                case 'M':
                    if(item.getQuantity() > player.getQuantityWood()){
                        throw new LogicException(line, LogicException.LACK_MATERIALS);
                    }
                    break;
                //TRIGO
                case 'T':
                    if(item.getQuantity() > player.getQuantityWheat()){
                        throw new LogicException(line, LogicException.LACK_MATERIALS);
                    }
                    break;
                //MONEDAS
                case 'X':
                    if(item.getQuantity() > player.getCoins()){
                        throw new LogicException(line, LogicException.LACK_MATERIALS);
                    }
                    break;
                //PUNTOS
                case 'P':
                    if(item.getQuantity() > player.getScore()){
                        throw new LogicException(line, LogicException.LACK_MATERIALS);
                    }
                    break;
            }
        }
    }

    //ACTION I
    private void actionI(String line, int actionLength) throws LogicException {
        if(actionLength != 1) {
            throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
        }
        System.out.println("INICIANDO PARTIDA");
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


    private ArrayList<Location> readLocations(InputReader inputReader) throws ExecutionException, LogicException {
        boolean exit = false;
        ArrayList<Location> locals = new ArrayList<>();
        String line;
        String[] localization;

        do {
            ArrayList<Combo> itemsRequired = new ArrayList<>();
            ArrayList<Combo> itemsObtained = new ArrayList<>();
            line = inputReader.readLine();
            //SE ACABA
            if(line.equals("##")) exit = true;
            // ES UNA LOCALIZACION
            if (Character.toLowerCase(line.charAt(0)) == Character.toLowerCase('L')
                    && Arrays.asList(localization = line.substring(1).split(" ")).contains("=")) {

                boolean change = false;
                if((localization.length - 1) % 2 == 0){
                    throw new LogicException(line, LogicException.INCORRECT_PAREAMETERS);
                }

                for (int i = 1; i < localization.length && !change; i += 2) {
                    if(localization[i].charAt(0) != '=' && localization[i + 1].charAt(0) != '=') {
                        String resource = localization[i];
                        int quantity = Integer.parseInt(localization[i + 1]);
                        itemsRequired.add(new Combo(resource.charAt(0), quantity));
                    } else change = true;
                }
                for (int i = Arrays.asList(localization).indexOf("=") + 1; i < localization.length; i += 2) {
                    String resource = localization[i];
                    int quantity = Integer.parseInt(localization[i + 1]);
                    itemsObtained.add(new Combo(resource.charAt(0), quantity));
                }

                int localID = Integer.parseInt(localization[0]);
                locals.add(new Location(localID, itemsRequired, itemsObtained));
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