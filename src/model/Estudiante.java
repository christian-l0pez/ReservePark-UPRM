package model;
// Esta clase representa un estudiante dentro del sistema.
public class Estudiante {// Estas variables guardan la informacion necesaria del estudiante como su numero, email, nombre y telefono
    private String numEstudiante;
    private String email;
    private String nombre;
    private String telefono;

    public Estudiante(String numEstudiante, String email, String telefono,String nombre) { // Este constructor crea un estudiante con la informacion recibida
        this.nombre = nombre;
        this.numEstudiante = numEstudiante;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombre() {// Devuelve el nombre
        return nombre;
    }
    public String getNumEstudiante() {// Devuelve el numero de estudiate
        return numEstudiante;
    }
    public String getEmail() {// Devuelve el email
        return email;
    }
    public String getTelefono() {
    // Devuelve el telefono
        return telefono;
    }
        // Devuelve la informacion del estudiante como texto
        @Override
        public String toString() {
            return "Estudiante: " + nombre +
                    "\nNumero de estudiante: " + numEstudiante +
                    "\nEmail: " + email +
                    "\nTelefono: " + telefono;
        }
}