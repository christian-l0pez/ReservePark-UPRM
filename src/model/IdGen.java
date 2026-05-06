package model;

public class IdGen {

    private static int contadorT = 1;

    public static String generarIdIndividual(
            int mes,
            int dia,
            int year,
            int numeroEspacio,
            int horaInicio
    ) {
        int yearCorto = year % 100;

        return String.format(
                "%02d%02d%02d%03d%02d",
                mes,
                dia,
                yearCorto,
                numeroEspacio,
                horaInicio
        );
    }

    public static String generarIdGrupo(
            int mes,
            int dia,
            int year,
            int numeroEspacio,
            int horaInicio
    ) {
        return "G-" + generarIdIndividual(mes, dia, year, numeroEspacio, horaInicio);
    }

    public static String nuevoIdTransaccion() {
        return String.format("T-%05d", contadorT++);
    }
}