package Model;

public class Player {
    private int num;
    private String color;
    private int qCarbon;
    private int qTrigo;
    private int qWood;
    private int coins;
    private int score;
    public Player(int num, String color, int qCarbon, int qTrigo, int qWood){
        this.num = num;
        this.color = color;
        this.qCarbon = qCarbon;
        this.qTrigo = qTrigo;
        this.qWood = qWood;
    }

    @Override
    public String toString() {
        return "Jugador [" +
                "color=" + color +
                ", puntuacion=" + score +
                ", madera=" + qWood +
                ", carbon=" + qCarbon +
                ", trigo=" + qTrigo +
                ", monedas=" + coins +
                "]";
    }

    protected String getColor() {
        return color;
    }

    public int getNum() {
        return num;
    }
}
