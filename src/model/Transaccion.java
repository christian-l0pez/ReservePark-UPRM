package model;

public class Transaccion {
    private String idTransaccion;
    private TipoTransaccion type;
    private Reservacion reservacion;
    private double sum;
    private String descripcion;

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

    public String getIdTransaccion() {
        return idTransaccion;
    }
    public TipoTransaccion getType() {
        return type;
    }
    public Reservacion getReservacion() {
        return reservacion;
    }
    public double getSum() {
        return sum;
    }
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Transaccion ID:" + idTransaccion +
                "\nTipo:" + type +
                "\nReservacion:" + reservacion.getIdReservacion() +
                "\nSuma:$" + sum +
                "\nDescripcion:" + descripcion;
    }
}