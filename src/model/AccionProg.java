package model;
// Esta clase es un metodo para estacionamiento.java y se utiliza para guardar informacion sobre acciones como reservar, cancelar o cambiar una reservacion.
public class AccionProg {

    public enum Tipo {//  este enum se usa para identificar el tipo de accion que se realizo
        RESERVAR,
        CANCELAR,
        CAMBIAR
    }

    private Tipo tipo;  // Guarda el tipo de accion que se realizo.
    private Reservacion reservacion; // Guarda la reservacion relacionada con esta accion.
    private Espacio espacioAnterior;// Guarda el espacio anterior antes de cancelar o cambiar una reservacion.
    private double costoAnterior; // Guarda el costo anterior antes de hacer algun cambio.

    public AccionProg(Tipo tipo, Reservacion reservacion, Espacio espacioAnterior, double costoAnterior) { // Constructor para crear una accion con todo el info q se necesita
        this.tipo = tipo;
        this.reservacion = reservacion;
        this.espacioAnterior = espacioAnterior;
        this.costoAnterior = costoAnterior;
    }

    public Tipo getTipo() { // Devuelve el tipo de accion.
        return tipo;
    }

    public Reservacion getReservacion() { // Devuelve la reservacion relacionada con esta accion.
        return reservacion;
    }

    public Espacio getEspacioAnterior() {// Devuelve el espacio anterior de la reservacion.
        return espacioAnterior;
    }

    public double getCostoAnterior() { // Devuelve el costo anterior de la reservacion.
        return costoAnterior;
    }
    //  Este metodo da la informacion de la accion como texto
    //  Tambien chequea si la reservacion o el espacio anterior son null para evitar errores al imprimir la informacion.
    @Override
    public String toString() {
        String idReservacion;
        String espacioStr;
        // Si hay una reservacion se obtiene su id. Si no se muestra "ninguna"
        if (reservacion != null) {
            idReservacion = reservacion.getIdReservacion();
        } else {
            idReservacion = "(ninguna)";
        }
        // Si hay un espacio anterior, se muestra su numero. Si no pues se muestra "ninguno"
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