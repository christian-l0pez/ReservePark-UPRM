package model;
// Clase que representa una transaccion del sistema.
public class Transaccion {
    // Guarda el id, tipo, reservacion, suma y descripcion de la transaccion
    private String idTransaccion;
    private TipoTransaccion type;
    private Reservacion reservacion;
    private double sum;
    private String descripcion;
    // Crea una transaccion
    public Transaccion(
            String idTransaccion,
            TipoTransaccion tipo,
            Reservacion reservacion,
            double sum,
            String descripcion
    ) {
        this.idTransaccion = idTransaccion;
        this.type = tipo;
        this.reservacion = reservacion;
        this.sum = sum;
        this.descripcion = descripcion;
    }

    public String getIdTransaccion() {// Devuelve el id de la transaccion
        return idTransaccion;
    }
    public TipoTransaccion getType() {// Devuelve el tipo de transaccion
        return type;
    }
    public Reservacion getReservacion() {// Devuelve la reservacion relacionada
        return reservacion;
    }
    public double getSum() {// Devuelve la + de la transaccion
        return sum;
    }
    public String getDescripcion() {// Devuelve la descripcion
        return descripcion;
    }
    // Devuelve la informacion de la transaccion
    @Override
    public String toString() {
        return "Transaccion ID:" + idTransaccion +
                "\nTipo:" + type +
                "\nReservacion:" + reservacion.getIdReservacion() +
                "\nSuma:$" + sum +
                "\nDescripcion:" + descripcion;
    }
}