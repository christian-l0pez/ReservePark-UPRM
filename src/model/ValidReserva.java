package model;
// Clase que valida los horarios de una reservacion
public class ValidReserva {
    // Valida si el dia no es null y q el horario este bien
    public static void validarHorario(DiaSemana dia, int horaInicio, int horaFin) throws HorarioException {
        if (dia == null) {
            throw new HorarioException("El dia no puede estar vacio.");
        }

        validarHorario(horaInicio, horaFin);
    }
    // Valida que la hora de inicio y fin esten dentro del horario permitido
    public static void validarHorario(int horaInicio, int horaFin) throws HorarioException {
        if (horaInicio < 7 || horaInicio > 16) {
            throw new HorarioException("La hora de inicio debe estar entre 7:00 AM y 4:00 PM.");
        }

        if (horaFin < 8 || horaFin > 17) {
            throw new HorarioException("La hora final debe estar entre 8:00 AM y 5:00 PM.");
        }

        if (horaFin <= horaInicio) {
            throw new HorarioException("La hora final debe ser mayor que la hora de inicio.");
        }

        int duracion = horaFin - horaInicio;

        if (duracion < 1) {
            throw new HorarioException("La reservacion debe durar un minimo de 1 hora.");
        }
        if (duracion > 8) {
            throw new HorarioException("La reservacion no puede durar mas de 8 horas consecutivas.");
        }
    }
}
