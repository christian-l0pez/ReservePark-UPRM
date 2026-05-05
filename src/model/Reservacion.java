package model;
import java.util.ArrayList;

public class Reservacion {
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

    public String getIdReservacion() {
        return idReservacion;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Auto getAuto() {
        return auto;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public ArrayList<ServicioAdicional> getServicios() {
        return servicios;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public boolean isActivo() {
        return activo;
    }

    public int getDuracion() {
        return horaFin - horaInicio;
    }

    public boolean esMayorDeDosHoras() {
        return getDuracion() > 2;
    }

    public void cancelar() {
        this.activo = false;
    }

    public void reactivar() {
        this.activo = true;
    }

    public void cambiarEspacio(Espacio nuevoEspacio) {
        this.espacio = nuevoEspacio;
    }

    public void actualizarCostoTotal(double nuevoCostoTotal) {
        this.costoTotal = nuevoCostoTotal;
    }

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