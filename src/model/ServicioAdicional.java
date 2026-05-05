package model;
public class ServicioAdicional {
    private String nombre;
    private double costo;

    public ServicioAdicional(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }
    public String getNombre() {
        return nombre;
    }
    public double getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        return nombre + ":$" + costo;
    }
}