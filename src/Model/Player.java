package Model;

public class Player {
    private int num;
    private String color;
    private int qCarbon;
    private int qWheat;
    private int qWood;
    private int coins;
    private int score;
    public Player(int num, String color, int qCarbon, int qTrigo, int qWood){
        this.num = num;
        this.color = color;
        this.qCarbon = qCarbon;
        this.qWheat = qTrigo;
        this.qWood = qWood;
        this.coins = 0;
        this.score = 0;
    }

    @Override
    public String toString() {
        return "Jugador [" +
                "color=" + color +
                ", puntuacion=" + score +
                ", madera=" + qWood +
                ", carbon=" + qCarbon +
                ", trigo=" + qWheat +
                ", monedas=" + coins +
                "]";
    }

    protected String getColor() {
        return color;
    }

    public int getNum() {
        return num;
    }
    //GETTERS
    public int getQuantityCarbon() {
        return qCarbon;
    }

    public int getQuantityWood() {
        return qWood;
    }

    public int getQuantityWheat() {
        return qWheat;
    }

    public int getCoins() {
        return coins;
    }
    //SETTERS
    public void setQuantityCarbon(int qCarbon) {
        this.qCarbon = qCarbon;
    }

    public void setQuantityWood(int qWood) {
        this.qWood = qWood;
    }

    public void setQuantityWheat(int qWheat) {
        this.qWheat = qWheat;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void plusScore(int score) {
        this.score += score;
    }
}
