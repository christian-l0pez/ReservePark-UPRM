package model;
import java.util.*;
public class MenuOp {
    public static final Scanner scanner = new Scanner(System.in);
    public static final Estacionamiento estacionamiento = new Estacionamiento();
    
    public static void main(String[] args){
        System.out.println("+===================================================+");
        System.out.println("  Sistema de Reservacion de Estacionamiento - UPRM");
        System.out.println("+===================================================+");

        boolean cont = true;
        while(cont){
            Menu();
            String opcion = scanner.nextLine().trim();
            System.out.println();

            try{
                switch(opcion){
                    case "1": hacerReservacion(); break;
                    case "2": cancelar(); break;
                    case "3": cambiarSeccion(); break;
                    case "4": undo(); break;
                    case "5": semana(); break;
                    case "6": mayoresDe2(); break;
                    case "7": porCosto(); break;
                    case "8": porVentana(); break;
                    case "9": porEstudiante(); break;
                    case "10": transacciones(); break;
                    case "11": listaDeEspera(); break;
                    case "12": espaciosDisponibles(); break;
                    case "0": cont = false; break;
                    default: System.out.println("Opcion no valida.");
                }
            }
            catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
        System.out.println("Proceso finalizado.");
    }

    private static void Menu(){
        System.out.println("========= Menu =========");
        System.out.println("1. Hacer reservacion");
        System.out.println("2. Cancelar Reservacion");
        System.out.println("3. Cambiar reservacion de seccion");
        System.out.println("4. Undo ultima accion");
        System.out.println("5. Mostrar reservaciones de la semana");
        System.out.println("6. Mostrar reservaciones(>2 horas)");
        System.out.println("7. Mostrar reservaciones por costo");
        System.out.println("8. Mostrar reservaciones en una ventana de tiempo");
        System.out.println("9. Mostrar reservaciones de un estudiante");
        System.out.println("10. Mostrar transacciones");
        System.out.println("11. Mostrar Lista de Espera");
        System.out.println("12. Mostrar espacios disponibles");
        System.out.println("0. Finalizar proceso");
        System.out.print("Escoja una opcion: ");
    }

    private static Estudiante infoEstudiante(){
        System.out.print("Nombre del Estudiante: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Numero de Estudiante: ");
        String numero = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("# de Telefono: ");
        String telefono = scanner.nextLine().trim();

        return new Estudiante(numero, email, telefono, nombre);
    }

    private static Auto infoAuto(){
        System.out.print("Marca del Auto: ");
        String marca = scanner.nextLine().trim();

        System.out.print("Tablilla: ");
        String tablilla = scanner.nextLine().trim();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine().trim();

        System.out.print("Año: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        return new Auto(marca, tablilla, modelo, year);
    }

    private static TipoSeccion seccion(){
        System.out.println("Seccion: 1. GENERAL($2/hr) , 2. VIP(4/hr), 3. ELECTRICO($8/hr)");
        System.out.print("Elige una seccion: ");
        int numero = scanner.nextInt(); scanner.nextLine();
        switch(numero){
            case 1: return TipoSeccion.GENERAL;
            case 2: return TipoSeccion.VIP;
            case 3: return TipoSeccion.ELECTRICO;
            default: throw new IllegalArgumentException("Escoja una seccion valida.");
        }
    }

    private static DiaSemana dia(){
        System.out.print("Dia (1=Lunes, 2= Martes, 3=Miercoles, 4=Jueves, 5= Viernes): ");
        int numero = scanner.nextInt(); scanner.nextLine();

        switch(numero){
            case 1: return DiaSemana.LUNES;
            case 2: return DiaSemana.MARTES;
            case 3: return DiaSemana.MIERCOLES;
            case 4: return DiaSemana.JUEVES;
            case 5: return DiaSemana.VIERNES;
            default: throw new IllegalArgumentException("Opcion invalida");
        }
    }

    private static ArrayList<ServicioAdicional> serviciosAdicionales(TipoSeccion seccion) {
    ArrayList<ServicioAdicional> serviciosDisponibles = new ArrayList<>();

    switch (seccion) {
        case GENERAL:
            serviciosDisponibles.add(new ServicioAdicional("Chequeo fluidos (coolant, wipers)", 2.00));
            serviciosDisponibles.add(new ServicioAdicional("Chequeo aire gomas", 1.00));
            break;
        case VIP:
            serviciosDisponibles.add(new ServicioAdicional("Lavado exterior", 50.00));
            serviciosDisponibles.add(new ServicioAdicional("Cambio de aceite", 35.00));
            serviciosDisponibles.add(new ServicioAdicional("Liquido frenos",35.00));
            serviciosDisponibles.add(new ServicioAdicional("Chequeo fluidos (coolant, wipers)", 2.00));
            serviciosDisponibles.add(new ServicioAdicional("Chequeo aire gomas", 1.00));
            break;
        case ELECTRICO:
            serviciosDisponibles.add(new ServicioAdicional("Lavado exterior", 50.00));
            serviciosDisponibles.add(new ServicioAdicional("Chequeo aire gomas", 1.00));
            break;
    }

    ArrayList<ServicioAdicional> escogidos = new ArrayList<>();

    boolean seguir = true;
    while (seguir) {
        System.out.println("Servicios disponibles " + seccion + ":");

        for (int i = 0; i < serviciosDisponibles.size(); i++) {
            ServicioAdicional servicio = serviciosDisponibles.get(i);
            String marca;
            if (escogidos.contains(servicio)) {
                marca = " *";
            } else {
                marca = "";
            }
            System.out.println(" " + (i + 1) + ") " + servicio + marca);
        }
        System.out.println(" 0) Terminar");
        System.out.print("Escoja un servicio: ");
        

        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            if (opcion == 0) {
                seguir = false;
            } else if (opcion >= 1 && opcion <= serviciosDisponibles.size()) {
                ServicioAdicional escogido = serviciosDisponibles.get(opcion - 1);
                if (escogidos.contains(escogido)) {
                    System.out.println("Ya estaba escogido");
                } else {
                    escogidos.add(escogido);
                    System.out.println("Agregado: " + escogido.getNombre());
                }
            } else {
                System.out.println("Opcion Invalida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor escriba un numero.");
        }
    } 

        return escogidos;
    }

    private static void imprimirReserv(ArrayList<Reservacion> lista){
        if(lista == null || lista.isEmpty()){
            System.out.println("No se encontraron reservaciones.");
            return;
        }
        for(Reservacion reserv : lista){
            System.out.println(reserv);
            System.out.println("======");
        }
    }

    private static void hacerReservacion() throws Exception{
        System.out.println("=== Nueva Reservacion ===");
        Estudiante estudiante = infoEstudiante();
        Auto auto = infoAuto();
        TipoSeccion seccion = seccion();
        DiaSemana dia = dia();

        System.out.print("Hora de inicio (7am - 16pm (4pm): ");
        int horaInicial = scanner.nextInt(); scanner.nextLine();

        System.out.print("Hora de finalizacion: (8am - 17pm (5pm): ");
        int horaFinal = scanner.nextInt(); scanner.nextLine();

        Set<Espacio> libres = estacionamiento.obtenerEspaciosDisponibles(seccion, dia, horaInicial, horaFinal);
        if(libres.isEmpty()){
            System.out.println("No hay espacios disponibles para esa seccion a esa hora.");
            System.out.print("Desea ser agregado a la lista de espera? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if(!respuesta.equals("s") && !respuesta.equals("si")){
                System.out.println("Reservacion cancelada.");
                return;
            }
            double costoEspera = CalcCosto.calcularCostoBase(seccion, horaInicial, horaFinal);
            Espacio temporario = new Espacio(0, 0, seccion);
            Reservacion espera = new Reservacion(IdGen.nuevoIdReservacion(),"",estudiante, auto, temporario, dia, horaInicial, horaFinal, new ArrayList<>(), costoEspera);

            estacionamiento.agregarAListaEspera(espera);
            System.out.println("Agregado a la lista de espera en la seccion " + seccion + ".");
            System.out.println("Posicion en la lista de espera: " + estacionamiento.getColaEspera(seccion).size());
            return;
        }
        Espacio escogido = null;
        for(Espacio espacio : libres){
            if(escogido == null || espacio.getNumeroEspacio() < escogido.getNumeroEspacio()){
                escogido = espacio;
            }
        }

        int numeroDeEspacio = escogido.getNumeroEspacio();
        System.out.println("Espacios disponibles: " + libres.size());
        System.out.println("Espacio asignado: " + escogido);

        ArrayList<ServicioAdicional> servicios = serviciosAdicionales(seccion);


        Reservacion reserv = estacionamiento.crearReservacion(estudiante, auto, seccion, dia, horaInicial, horaFinal, numeroDeEspacio, servicios);
        System.out.println("Reservacion creada.");
        System.out.println(reserv);
    }

    private static void cancelar() throws Exception{
        System.out.println("=== Cancelar Reservacion ===");
        System.out.print("ID de la reservacion: ");
        String id = scanner.nextLine().trim();
        estacionamiento.cancelarReservacion(id);
        System.out.println("Reservacion " + id +" cancelada.");
        System.out.println("Cargo por cancelar: $" + CalcCosto.calcularCostoCancelacion());
    }

    private static void cambiarSeccion() throws Exception{
        System.out.println("=== Cambiar Reservacion de Seccion ===");
        System.out.print("ID de la reservacion: ");
        String id = scanner.nextLine().trim();

        Reservacion reserv = estacionamiento.buscarReservacionPorId(id);
        if(reserv == null){
            System.out.println("No se encontro la reservacion");
            return;
        }
        
        TipoSeccion seccionNueva = seccion();

        if(seccionNueva == reserv.getEspacio().getSeccion()){
            System.out.println("No se puede escoger la misma seccion.");
            return;
        }

        Set<Espacio> espaciosDisponibles = estacionamiento.obtenerEspaciosDisponibles(seccionNueva, reserv.getDia(), reserv.getHoraInicio(), reserv.getHoraFin());
        if(espaciosDisponibles.isEmpty()){
            System.out.println("No hay espacios disponibles a esa hora");
            return;
        }
        Espacio elegido = null;
        for(Espacio espacio : espaciosDisponibles){
            if(elegido == null || espacio.getNumeroEspacio() < elegido.getNumeroEspacio()){
                elegido = espacio;
            }
        }
        System.out.println("Estacionamiento asignado: " + elegido);
        Reservacion cambio = estacionamiento.cambiarEspacio(id, elegido.getNumeroEspacio());
        System.out.println("Cambio completado.");
        System.out.println(cambio);
    }

    private static void undo(){
        System.out.println("=== UNDO ultima accion ===");
        estacionamiento.deshacerUltimaAccion();
    }

    private static void semana(){
        System.out.println("=== Reservacion de la semana ===");
        ArrayList<Reservacion> reservaciones = estacionamiento.obtenerTodasLasReservaciones();
        imprimirReserv(reservaciones); 
    }

    private static void mayoresDe2(){
        System.out.println("=== Reservacion Mayores de 2 Horas ===");
        DiaSemana dia = dia();
        ArrayList<Reservacion> resultado = estacionamiento.obtenerReservacionesMayorDosHoras(dia);
        imprimirReserv(resultado);
    }

    private static void porCosto(){
        System.out.println("=== Reservacion por costo ===");
        System.out.println("1. Mayor a $50");
        System.out.println("2. Mayor a $100");
        System.out.println("3. Rango personalizado");
        System.out.print("Escoja una opcion: ");
        int opcion = scanner.nextInt(); scanner.nextLine();
        double min;
        double max;

        switch(opcion){
            case 1: min = 50.01; max = Double.MAX_VALUE; 
            break;

            case 2: min = 100.01; max = Double.MAX_VALUE;
            break;

            case 3: 
            System.out.print("Costo minimo: ");
            min = scanner.nextDouble(); scanner.nextLine();

            System.out.print("Costo maximo: ");
            max = scanner.nextDouble(); scanner.nextLine();
            break;

            default: System.out.println("Opcion invalida.");
            return;
        }
        ArrayList<Reservacion> resultado = estacionamiento.obtenerReservacionesPorRangoCosto(min, max);
        imprimirReserv(resultado);

    }

    private static void porVentana(){
        System.out.println("=== Reservacion en Ventana de Tiempo ===");
        DiaSemana dia = dia();
        System.out.print("Hora inicial: ");
        int horaInicial = scanner.nextInt(); scanner.nextLine();

        System.out.print("Hora final: ");
        int horaFinal = scanner.nextInt(); scanner.nextLine();

        ArrayList<Reservacion> completado = estacionamiento.obtenerReservacionesPorPeriodo(dia, horaInicial, horaFinal);
        imprimirReserv(completado);
    }

    private static void porEstudiante(){
        System.out.println("=== Reservacion por Estudiante ===");
        System.out.print("Numero de estudiante: ");
        String numero = scanner.nextLine().trim();

        ArrayList<Reservacion> resultado = estacionamiento.buscarReservacionesPorEstudiante(numero);
        imprimirReserv(resultado);
    }

    private static void transacciones(){
        System.out.println("=== Historial de transacciones ===");
        LinkedList<Transaccion> transacciones = estacionamiento.obtenerTransacciones();
        if(transacciones.isEmpty()){
            System.out.println("No hay transacciones disponibles.");
            return;
        }
        for(Transaccion transaccion : transacciones){
            System.out.println(transaccion);
            System.out.println("=======");
        }
    }

    private static void listaDeEspera(){
        System.out.println("=== Lista de espera ===");
        TipoSeccion secEscogida = seccion();
        Queue<Reservacion> cola = estacionamiento.getColaEspera(secEscogida);
        if(cola.isEmpty()){
            System.out.println("No hay reservaciones en lista de espera para esa seccion.");
            return;
        }

        System.out.println("Reservacion en lista de espera para " + secEscogida + ".");
        int pos = 1;
        for(Reservacion reserv : cola){
            System.out.println("Posicion " + pos + ".");
            System.out.println(reserv);
            System.out.println("======");
            pos++;
        }
    }
    private static void espaciosDisponibles() throws Exception{
        System.out.println("=== Espacios disponibles === ");
        TipoSeccion secEscogida = seccion();
        DiaSemana diaEligo = dia();

        System.out.print("Hora inicial: ");
        int horaInicial = scanner.nextInt(); scanner.nextLine();

        System.out.print("Hora final: ");
        int horaFinal = scanner.nextInt(); scanner.nextLine();

        Set<Espacio> espaciosLibres = estacionamiento.obtenerEspaciosDisponibles(secEscogida, diaEligo, horaInicial, horaFinal);
        System.out.println("Espacios disponibles en: " + secEscogida + ": " + espaciosLibres.size());
    }

}