import java.util.ArrayList;
import model.*;

public class Main {
    public static void main(String[] args) {

        Estudiante estudiante = new Estudiante(
                "802algoalgo",
                "ingetupapa@upredi",
                "787-777-777",
                "Vergil"
        );

        Auto auto = new Auto(
                "45354",
                "BUGATIIIII",
                "CHSAOHSAFJHIE",
                2023

        );

        Espacio espacio = new Espacio(
                1,
                78,
                TipoSeccion.GENERAL
        );

        ArrayList<ServicioAdicional> servicios = new ArrayList<>();
        servicios.add(new ServicioAdicional("check  aire de gomas", 1.00));
        servicios.add(new ServicioAdicional("ceck de fluidos", 2.00));

        Reservacion reservacion = new Reservacion(
                "05042607807",
                "G-05042607807",
                estudiante,
                auto,
                espacio,
                DiaSemana.MARTES,
                7,
                10,
                servicios,
                9.00
        );

        espacio.agregarReservacion(reservacion);

        Transaccion transaccion = new Transaccion(
                "T-00001",
                TipoTransaccion.RESERVACION,
                reservacion,
                reservacion.getCostoTotal(),
                "Bien"
        );

        System.out.println("student");
        System.out.println(estudiante);

        System.out.println("\ncarro");
        System.out.println(auto);

        System.out.println("\nespacio");
        System.out.println(espacio);

        System.out.println("\nservicios");
        for (ServicioAdicional servicio : servicios) {
            System.out.println(servicio);
        }

        System.out.println("\nreserv");
        System.out.println(reservacion);

        System.out.println("\ntransaccion");
        System.out.println(transaccion);

        System.out.println("\ndisponible?");

        boolean disponibleMismoHorario = espacio.estaDisponible(DiaSemana.LUNES, 8, 11);
        boolean disponibleOtroHorario = espacio.estaDisponible(DiaSemana.LUNES, 10, 12);
        boolean disponibleOtroDia = espacio.estaDisponible(DiaSemana.MARTES, 8, 11);

        System.out.println("lunes 8-11 disp? " + disponibleMismoHorario);
        System.out.println("Lunes 10-12 disp? " + disponibleOtroHorario);
        System.out.println("Martes 8-11 disp?" + disponibleOtroDia);
    }
}