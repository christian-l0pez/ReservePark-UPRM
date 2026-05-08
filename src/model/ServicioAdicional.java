package model;
// Clase que representa un servicio adicional
public class ServicioAdicional {
    // Guarda el nombre y costo del servicio
    private String nombre;
    private double costo;
    // Crea un servicio adicional
    public ServicioAdicional(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }
    public String getNombre() {// Devuelve el nombre
        return nombre;
    }
    public double getCosto() {// Devuelve el costo
        return costo;
    }

    @Override// Devuelve la informacion del servicio
    public String toString() {
        return nombre + ":$" + costo;
    }
}