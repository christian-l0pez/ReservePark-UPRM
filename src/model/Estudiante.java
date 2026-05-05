package model;

public class Estudiante {
    private String numEstudiante;
    private String email;
    private String nombre;
    private String telefono;

    public Estudiante(String numEstudiante, String email, String telefono,String nombre) {
        this.nombre = nombre;
        this.numEstudiante = numEstudiante;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }
    public String getNumEstudiante() {
        return numEstudiante;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefono() {
        return telefono;
    }

        @Override
        public String toString() {
            return "Estudiante: " + nombre +
                    "\nNumero de estudiante: " + numEstudiante +
                    "\nEmail: " + email +
                    "\nTelefono: " + telefono;
        }
}