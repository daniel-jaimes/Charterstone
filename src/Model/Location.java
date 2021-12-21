package Model;

import java.util.ArrayList;

public class Location {
    private int num;
    private ArrayList<Combo> itemsRequired;
    private ArrayList<Combo> itemsObtained;
    private Player player;

    public Location(int num, ArrayList<Combo> itemsRequired, ArrayList<Combo> itemsObtained) {
        this.num = num;
        this.itemsRequired = itemsRequired;
        this.itemsObtained = itemsObtained;
    }

    @Override
    public String toString() {
        String namePlayer;
        if(player == null) namePlayer = "ninguno";
        else namePlayer = player.getColor();
        String result = "Localizacion [";
        for (int i = 0; i < itemsRequired.size(); i++) {
            result += "necesario"+ (1 + i) + "=" + itemsRequired.get(i).getResource() +
                    ", numNecesario" + (1 + i) + "=" + itemsRequired.get(i).getQuantity() + ", ";
        }
        for (int i = 0; i < itemsObtained.size(); i++) {
            result += "obtenido"+ (1 + i) + "=" + itemsObtained.get(i).getResource() +
                    ", numNecesario" + (1 + i) + "=" + itemsObtained.get(i).getQuantity() + ", ";
        }
        //EXTRACT the last character (',')
        result = result.substring(0, result.length() - 3);
        result += ", jugador=" + namePlayer + "]";
        return result;
    }
    //GETTERS
    public int getNum() {
        return num;
    }

    public Player getPlayer() {
        return player;
    }
    public ArrayList<Combo> getItemsRequired() {
        return itemsRequired;
    }

    public ArrayList<Combo> getItemsObtained() {
        return itemsObtained;
    }
    //SETTER
    public void setPlayer(Player player) {
        this.player = player;
    }
}
