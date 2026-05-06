package model;

public class AccionProg {

    public enum Tipo {
        RESERVAR,
        CANCELAR,
        CAMBIAR
    }

    private Tipo tipo;
    private Reservacion reservacion;
    private Espacio espacioAnterior;
    private double costoAnterior;

    public AccionProg(Tipo tipo, Reservacion reservacion, Espacio espacioAnterior, double costoAnterior) {
        this.tipo = tipo;
        this.reservacion = reservacion;
        this.espacioAnterior = espacioAnterior;
        this.costoAnterior = costoAnterior;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Reservacion getReservacion() {
        return reservacion;
    }

    public Espacio getEspacioAnterior() {
        return espacioAnterior;
    }

    public double getCostoAnterior() {
        return costoAnterior;
    }

    @Override
    public String toString() {
        String idReservacion;
        String espacioStr;

        if (reservacion != null) {
            idReservacion = reservacion.getIdReservacion();
        } else {
            idReservacion = "(ninguna)";
        }

        if (espacioAnterior != null) {
            espacioStr = "Espacio #" + espacioAnterior.getNumeroEspacio();
        } else {
            espacioStr = "(ninguno)";
        }

        return "Accion: " + tipo +
                "\nReservacion: " + idReservacion +
                "\nEspacio anterior: " + espacioStr +
                "\nCosto anterior: $" + costoAnterior;
    }
}