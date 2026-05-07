package model;

import java.util.HashSet;
import java.util.Random;

public class IdGen {

    private static Random random = new Random();

    private static HashSet<String> idsReservaciones = new HashSet<>();
    private static HashSet<String> idsGrupos = new HashSet<>();
    private static HashSet<String> idsTransacciones = new HashSet<>();

    public static String nuevoIdReservacion() {
        String id;

        do {
            id = "Reserva-" + generarNumeroRandom();
        } while (idsReservaciones.contains(id));

        idsReservaciones.add(id);
        return id;
    }

    public static String nuevoIdGrupo() {
        String id;

        do {
            id = "Grupo:" + generarNumeroRandom();
        } while (idsGrupos.contains(id));

        idsGrupos.add(id);
        return id;
    }

    public static String nuevoIdTransaccion() {
        String id;

        do {
            id = "Transaccion:" + generarNumeroRandom();
        } while (idsTransacciones.contains(id));

        idsTransacciones.add(id);
        return id;
    }

    private static String generarNumeroRandom() {
        int numero = random.nextInt(900000) + 100000;
        return String.valueOf(numero);
    }
}