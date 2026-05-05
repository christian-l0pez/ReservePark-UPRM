package model;

public class ValidReserva {
    public static void validarHorario(int horaInicial, int horaFinal) throws HorarioException{
        if(horaInicial < 7 || horaInicial > 16){
            throw new HorarioException("La hora inicial debe estra entre 7am y 16pm (4pm)");
        }
        if(horaFinal < 8 || horaFinal > 17){
            throw new HorarioException("La hora final debe estar entre 8am y 17pm (5pm)");
        }
        if(horaFinal <= horaInicial){
            throw new HorarioException("La hora final debe ser mayor que la hora de inicio.");
        }
        int tiempo = horaFinal - horaInicial;

        if(tiempo < 1){
            throw new HorarioException("Debes reservar un minimo de 1 hora");
        }

        if(tiempo > 8){
            throw new HorarioException("El maximo que puede durar una reservacion es 8 horas.");
        }
    }
}
