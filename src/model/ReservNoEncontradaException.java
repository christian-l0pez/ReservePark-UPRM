package model;

// Clase ReservNoEncontradaException - excepcion personalizada que lanza un mensaje cuando  no se encuentra la reservacion por id.
// Hereda de Exception
public class ReservNoEncontradaException extends Exception{
    // Crea un ReservNoEncontradaException con el mensaje dado
    // Recibe message / descripcion del problema
    // Llama super(message) 
    // Llamado por Estacionamiento.cancelarReservacion , Estacionamiento.cambiarEspacio
    public ReservNoEncontradaException(String message){
        super(message);
    }
}
