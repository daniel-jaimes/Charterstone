package Model;

public class Location {
    private int num;
    private Character resourceRequired;
    private int qRequired;
    private Character resourceObtained;
    private int qObtained;
    private Player player;
    public Location(int num, char resourceRequired, int qRequired, char resourceObtained, int qObtained) {
        this.num = num;
        this.resourceRequired = resourceRequired;
        this.qRequired = qRequired;
        this.resourceObtained = resourceObtained;
        this.qObtained = qObtained;
    }

    @Override
    public String toString() {
        String namePlayer;
        if(player == null) namePlayer = "ninguno";
        else namePlayer = player.getColor();
        return "Localizacion [" +
                "necesario=" + resourceRequired +
                ", numNecesario=" + qRequired +
                ", Obtenido=" + resourceObtained +
                ", numObtenido=" + qObtained +
                ", jugador=" + namePlayer +
                "]";
    }

    public int getNum() {
        return num;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public int getNumPlayer(){
        return player.getNum();
    }
}
