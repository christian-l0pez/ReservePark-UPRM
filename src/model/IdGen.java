package model;

import java.util.HashSet;
import java.util.Random;
// Clase que genera ids unicos para reservaciones grupos y transacciones
public class IdGen {

    private static Random random = new Random();// Guarda el objeto Random para crear numeros aleatorios
 // Guarda los ids que ya fueron creados para evitar duplicados.
    private static HashSet<String> idsReservaciones = new HashSet<>();
    private static HashSet<String> idsGrupos = new HashSet<>();
    private static HashSet<String> idsTransacciones = new HashSet<>();
    // Genera un nuevo id unico para una reservacion
    public static String nuevoIdReservacion() {
        String id;

        do {
            id = "Reserva-" + generarNumeroRandom();
        } while (idsReservaciones.contains(id));

        idsReservaciones.add(id);
        return id;
    }
    // Genera un nuevo id unico para un grupo
    public static String nuevoIdGrupo() {
        String id;

        do {
            id = "Grupo:" + generarNumeroRandom();
        } while (idsGrupos.contains(id));

        idsGrupos.add(id);
        return id;
    }
    // Genera un nuevo id unico para una transaccion
    public static String nuevoIdTransaccion() {
        String id;

        do {
            id = "Transaccion:" + generarNumeroRandom();
        } while (idsTransacciones.contains(id));

        idsTransacciones.add(id);
        return id;
    }
    // Genera un numero random de 6 num
    private static String generarNumeroRandom() {
        int numero = random.nextInt(900000) + 100000;
        return String.valueOf(numero);
    }
}