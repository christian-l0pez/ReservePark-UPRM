package model;
import java.util.ArrayList;

public class Espacio {
    private int fila;
    private int numeroEspacio;
    private TipoSeccion seccion;
    private ArrayList<Reservacion> reservaciones;

    public Espacio(int fila, int numeroEspacio, TipoSeccion seccion) {
        this.fila = fila;
        this.numeroEspacio = numeroEspacio;
        this.seccion = seccion;
        this.reservaciones = new ArrayList<>();
    }
    public int getFila() {
        return fila;
    }
    public int getNumeroEspacio() {
        return numeroEspacio;
    }
    public TipoSeccion getSeccion() {
        return seccion;
    }
    public ArrayList<Reservacion> getReservaciones() {
        return reservaciones;
    }
    public boolean estaDisponible(DiaSemana dia, int horaInicio, int horaFin) {
        for (Reservacion reservacion : reservaciones) {
            if (reservacion.isActivo() && reservacion.getDia() == dia) {
                boolean hayConflicto = horaInicio < reservacion.getHoraFin()
                        && horaFin > reservacion.getHoraInicio();
                if (hayConflicto) {return false;}
            }
        }

        return true;
    }

    public void agregarReservacion(Reservacion reservacion) {reservaciones.add(reservacion);}

    public void removerReservacion(Reservacion reservacion) {reservaciones.remove(reservacion);}

    @Override
    public String toString() {
        return "Espacio" + numeroEspacio +
                "\nFila:" + fila +
                "\nSeccion:" + seccion;
    }
}