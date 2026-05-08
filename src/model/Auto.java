package model;

// Esta clase representa un carro adentro del sistema de estacionamiento
public class Auto {

    private String tablilla;// Guarda el numero de tablilla del auto.
    private String marca;// Guarda la marca del auto.
    private String modelo; // Guarda el modelo del auto.
    private int year;  // Guarda el ano del auto.

    public Auto(String marca, String tablilla, String modelo, int year) {  // Constructor para crear un auto con la marca, tablilla, modelo y ano
        this.marca = marca;
        this.tablilla = tablilla;
        this.modelo = modelo;
        this.year = year;
    }
    public String getMarca() { // Devuelve la marca del auto
        return marca;
    }
    public String getTablilla() { // Devuelve la tablilla del auto
        return tablilla;
    }
    public String getModelo() {// Devuelve el modelo del auto
        return modelo;
    }
    public int getYear() { // Devuelve el ano del auto
        return year;
    }
    // Este metodo devuelve la informacion del auto como texto
        @Override
        public String toString() {
            return "Auto: " + marca + " " + modelo +
                    "\nTablilla: " + tablilla +
                    "\nAño: " + year;
        }
}