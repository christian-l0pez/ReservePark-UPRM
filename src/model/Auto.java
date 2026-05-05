package model;

public class Auto {

    private String tablilla;
    private String marca;
    private String modelo;
    private int year;

    public Auto(String marca, String tablilla, String modelo, int year) {
        this.marca = marca;
        this.tablilla = tablilla;
        this.modelo = modelo;
        this.year = year;
    }
    public String getMarca() {
        return marca;
    }
    public String getTablilla() {
        return tablilla;
    }
    public String getModelo() {
        return modelo;
    }
    public int getYear() {
        return year;
    }

        @Override
        public String toString() {
            return "Auto: " + marca + " " + modelo +
                    "\nTablilla: " + tablilla +
                    "\nAño: " + year;
        }
}