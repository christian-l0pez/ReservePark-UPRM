package model;
import java.util.ArrayList;
// Clase que representa una reservacion de estacionamiento
public class Reservacion {
    // Guarda la informacion principal de la reservacion
    private String idReservacion;
    private String idGrupo;
    private Estudiante estudiante;
    private Auto auto;
    private Espacio espacio;
    private DiaSemana dia;
    private int horaInicio;
    private int horaFin;
    private ArrayList<ServicioAdicional> servicios;
    private double costoTotal;
    private boolean activo;
    // Crea una reservacion con toda la informacion necesaria
    public Reservacion(
            String idReservacion,
            String idGrupo,
            Estudiante estudiante,
            Auto auto,
            Espacio espacio,
            DiaSemana dia,
            int horaInicio,
            int horaFin,
            ArrayList<ServicioAdicional> servicios,
            double costoTotal
    ) {
        this.idReservacion=idReservacion;
        this.idGrupo=idGrupo;
        this.estudiante=estudiante;
        this.auto=auto;
        this.espacio=espacio;
        this.dia=dia;
        this.horaInicio=horaInicio;
        this.horaFin=horaFin;
        this.servicios=servicios;
        this.costoTotal=costoTotal;
        this.activo=true;
    }

    public String getIdReservacion() {   // Devuelve el id de la reservacion
        return idReservacion;
    }

    public String getIdGrupo() {// Devuelve el id del grupo 
        return idGrupo;
    }

    public Estudiante getEstudiante() { // Devuelve el estudiante
        return estudiante;
    }

    public Auto getAuto() {  // Devuelve el auto
        return auto;
    }

    public Espacio getEspacio() { // Devuelve el espacio
        return espacio;
    }

    public DiaSemana getDia() {// Devuelve el dia
        return dia;
    }

    public int getHoraInicio() {// Devuelve la hora de inicio
        return horaInicio;
    }

    public int getHoraFin() {// Devuelve la hora final
        return horaFin;
    }

    public ArrayList<ServicioAdicional> getServicios() {// Devuelve los servicios adicionales
        return servicios;
    }

    public double getCostoTotal() {// Devuelve el costo total
        return costoTotal;
    }

    public boolean isActivo() {// Devuelve si la reservacion esta activa
        return activo;
    }

    public int getDuracion() {// Devuelve la duracion de la reservacion
        return horaFin - horaInicio;
    }

    public boolean esMayorDeDosHoras() { // Verifica si la reservacion dura mas de dos horas
        return getDuracion() > 2;
    }

    public void cancelar() { // Cancela la reservacion
        this.activo = false;
    }

    public void reactivar() {// Reactiva la reservacion
        this.activo = true;
    }

    public void cambiarEspacio(Espacio nuevoEspacio) {// Cambia el espacio de la reservacion
        this.espacio = nuevoEspacio;
    }

    public void actualizarCostoTotal(double nuevoCostoTotal) {// Actualiza el costo total
        this.costoTotal = nuevoCostoTotal;
    }
    // Devuelve la informacion de la reservacion
    @Override
    public String toString() {
        String estado;

        if (activo) {estado = "Activa";} 
        else {estado = "Cancelada";}

        return "Reservacion ID: " + idReservacion +
                "\nGrupo: " + idGrupo +
                "\nEstudiante: " + estudiante.getNombre() +
                "\nNumero de estudiante: " + estudiante.getNumEstudiante() +
                "\nAuto: " + auto.getMarca() + " " + auto.getModelo() +
                "\nTablilla: " + auto.getTablilla() +
                "\nEspacio: " + espacio.getNumeroEspacio() +
                "\nSeccion: " + espacio.getSeccion() +
                "\nDia: " + dia +
                "\nHorario: " + horaInicio + ":00 - " + horaFin + ":00" +
                "\nDuracion: " + getDuracion() + " hora(s)" +
                "\nCosto total: $" + costoTotal +
                "\nEstado: " + estado;
    }
}