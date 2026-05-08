package model;
// Enum que representa los tipos de transaccion del sistema
public enum TipoTransaccion {
    RESERVACION,// Transaccion de una reservacion
    CANCELACION,// Transaccion de una cancelacion
    CAMBIO_ESPACIO,// Transaccion de cambio de espacio
    CARGO_CANCELACION,// Cargo por cancelar una reservacion
    CARGO_CAMBIO// Cargo por cambiar de espacio
}