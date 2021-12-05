package Exceptions;

import java.util.Arrays;
import java.util.List;

public class LogicException extends Exception{
    public static final int INCORRECT_ACTION = 0;
    public static final int INCORRECT_PAREAMETERS = 1;
    public static final int LACK_MATERIALS = 2;
    public static final int PLAYER_DONT_REPEAT = 3;
    public static final int TWO_PLAYERS_USED = 4;
    public static final int COMPLETE_TURN = 5;
    private final String errorLine;
    private int value;
    private List<String> message = Arrays.asList(
            "<< La acción requerida es incorreta >>",
            "<< Numero de parámetros incorrecto >>",
            "<< No se puede realizar la acción por falta de materiales >>",
            "<< El jugador no puede volver a jugar >>",
            "<< El jugador ya ha utilizado sus dos personajes >>",
            "<< Turno completado >>"
            );
    public LogicException(String line, int value){
        this.errorLine = line;
        this.value = value;
    }

    @Override
    public String getMessage() {
        return "ERROR: " + errorLine + " --> " + message.get(value);
    }

}
