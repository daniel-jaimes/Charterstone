package Exceptions;

import java.util.Arrays;
import java.util.List;

public class ExecutionException extends Exception{
    public static final int CREATING_FILE = 0;
    public static final int READING_FILE = 1;
    public static final int WRITING_FILE = 2;
    public static final int CLOSING_FILE = 3;
    private int value;
    private List<String> message = Arrays.asList(
            "<< Error creando el fichero >>",
            "<< Error de lectura de fichero >>",
            "<< Error de escritura de fichero >>",
            "<< Error cerrando el fichero >>"
    );
    public ExecutionException(int value){
        this.value = value;
    }

    @Override
    public String getMessage() {
        return message.get(value);
    }
}
