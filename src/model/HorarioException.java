package model;

// Clase HorarioException - excepcion personalizada que lanza cuando hay un problema con el horario de la reservacion
// hereda de Exception 
public class HorarioException extends Exception {
    // Crea un HorarioExeception con el mensaje dado 
    // Recibe String message (descripcion del exception)
    // Llama super(message)
    // Llamado por ValidReserva.validarHorario
    public HorarioException(String message) {
        super(message);
    }
}
