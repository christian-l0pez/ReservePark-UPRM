package model;
import java.util.ArrayList;
// Esta clase representa un espacio de estacionamiento
public class Espacio {
    // Estas variables guardan la informacion necesaria como la fila, numero de espacio la seccion y todas la reservaciones para tal espacio.
    private int fila;  
    private int numeroEspacio;
    private TipoSeccion seccion;
    private ArrayList<Reservacion> reservaciones;

    public Espacio(int fila, int numeroEspacio, TipoSeccion seccion) {// Constructor para crear un espacio con su fila, numero y seccion
        this.fila = fila;
        this.numeroEspacio = numeroEspacio;
        this.seccion = seccion;
        this.reservaciones = new ArrayList<>();
    }
    public int getFila() { // Devuelve la fila del espacio
        return fila;
    }
    public int getNumeroEspacio() {  // Devuelve el numero del espacio
        return numeroEspacio;
    }
    public TipoSeccion getSeccion() { // Devuelve la seccion del espacio
        return seccion;
    }
    public ArrayList<Reservacion> getReservaciones() { // Devuelve la lista de reservaciones del espacio
        return reservaciones;
    }
    public boolean estaDisponible(DiaSemana dia, int horaInicio, int horaFin) {  //Este metodo verifica si el espacio esta disponible en un dia y horario
        for (Reservacion reservacion : reservaciones) {
            if (reservacion.isActivo() && reservacion.getDia() == dia) {
                boolean hayConflicto = horaInicio < reservacion.getHoraFin()
                        && horaFin > reservacion.getHoraInicio();
                if (hayConflicto) {return false;} // Si hay conflicto con otra reservacion el espacio no esta disponible
            }
        }

        return true;
    }

    public void agregarReservacion(Reservacion reservacion) {reservaciones.add(reservacion);} // Agrega una reservacion a la lista de reservaciones del espacio

    public void removerReservacion(Reservacion reservacion) {reservaciones.remove(reservacion);} // Remueve una reservacion de la lista de reservaciones del espacio

    @Override //Este metodo devuelve la informacion del espacio como texto.
    public String toString() {
        return "Espacio" + numeroEspacio +
                "\nFila:" + fila +
                "\nSeccion:" + seccion;
    }
}