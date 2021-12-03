package Model;

public class Location {
    private int num;
    private char resourceRequired;
    private int qRequired;
    private char resourceObtained;
    private int qObtained;

    public Location(int num, char resourceRequired, int qRequired, char resourceObtained, int qObtained) {
        this.num = num;
        this.resourceRequired = resourceRequired;
        this.qRequired = qRequired;
        this.resourceObtained = resourceObtained;
        this.qObtained = qObtained;
    }
}
