package model;

// Clase NoDisponibleException - excepcion personalizada que lanza cuando un espacio no esta disponible para reservar.
// Hereda de Exception
public class NoDisponibleException extends Exception {
    // Crea un NoDisponibleException con el mensaje dado
    // Recibe message, que es por que el espacio no esta disponible.
    // Llama super(message)
    // Llamado por Estacionamiento.crearReservacion, Estacionamiento.cambiarEspacio, Estacionamiento.crearReservacionSemanal
    public NoDisponibleException(String message) {
        super(message);
    }
    
}
