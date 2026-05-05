package model;

public class IdGen {
    private static int contadorR = 1;
    private static int contadorG = 1;
    private static int contadorT = 1;

    public static String nuevoIdReservacion(){
        return String.format("R-%03d", contadorR++);
    }

    public static String nuevoIdGrupo(){
        return String.format("G-%03d", contadorG++);
    }

    public static String nuevoIdTransaccion(){
        return String.format("T-%03d", contadorT++);
    }
}
