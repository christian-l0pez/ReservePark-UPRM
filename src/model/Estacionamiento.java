package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Estacionamiento {
    private ArrayList<Espacio> listaEspaciosGeneral;
    private ArrayList<Espacio> listaEspaciosVIP;
    private ArrayList<Espacio> listaEspaciosElectricos;
    private LinkedList<Transaccion> historialDeTransacciones;
    private HashMap<String, ArrayList<Reservacion>> mapaReservacionesPorTablilla;
    private HashMap<String, ArrayList<Reservacion>> mapaReservacionesPorEstudiante;
    private HashMap<String, Reservacion> mapaReservacionesPorId;
    private Stack<AccionProg> pilaDeAcciones;
    private Queue<Reservacion> colaEsperaGeneral;
    private Queue<Reservacion> colaEsperaVIP;
    private Queue<Reservacion> colaEsperaElectricos;

    public Estacionamiento() {
        listaEspaciosGeneral = new ArrayList<>();
        listaEspaciosVIP = new ArrayList<>();
        listaEspaciosElectricos = new ArrayList<>();
        historialDeTransacciones = new LinkedList<>();
        mapaReservacionesPorTablilla = new HashMap<>();
        mapaReservacionesPorEstudiante = new HashMap<>();
        mapaReservacionesPorId = new HashMap<>();
        pilaDeAcciones = new Stack<>();
        colaEsperaGeneral = new LinkedList<>();
        colaEsperaVIP = new LinkedList<>();
        colaEsperaElectricos = new LinkedList<>();
        crearTodosLosEspacios();
    }

    private void crearTodosLosEspacios() {
        for (int i = 1; i <= 100; i++) {
            int filaCalculada = calcularEnQueFilaEsta(i);
            listaEspaciosGeneral.add(new Espacio(filaCalculada, i, TipoSeccion.GENERAL));
        }
        for (int i = 101; i <= 150; i++) {
            int filaCalculada = calcularEnQueFilaEsta(i);
            listaEspaciosVIP.add(new Espacio(filaCalculada, i, TipoSeccion.VIP));
        }
        for (int i = 151; i <= 200; i++) {
            int filaCalculada = calcularEnQueFilaEsta(i);
            listaEspaciosElectricos.add(new Espacio(filaCalculada, i, TipoSeccion.ELECTRICO));
        }
    }

    private int calcularEnQueFilaEsta(int numeroDelEspacio) {
        int resultado = ((numeroDelEspacio - 1) / 10) + 1;
        return resultado;
    }

    private ArrayList<Espacio> conseguirListaDeEspaciosPorSeccion(TipoSeccion seccion) {
        if (seccion == TipoSeccion.GENERAL) {
            return listaEspaciosGeneral;
        } else if (seccion == TipoSeccion.VIP) {
            return listaEspaciosVIP;
        } else if (seccion == TipoSeccion.ELECTRICO) {
            return listaEspaciosElectricos;
        } else {
            return new ArrayList<>();
        }
    }

    private Queue<Reservacion> conseguirColaDeEsperaPorSeccion(TipoSeccion seccion) {
        if (seccion == TipoSeccion.GENERAL) {
            return colaEsperaGeneral;
        } else if (seccion == TipoSeccion.VIP) {
            return colaEsperaVIP;
        } else {
            return colaEsperaElectricos;
        }
    }

    public Espacio buscarEspacioPorNumero(int numeroEspacio) {
        for (Espacio espacio : listaEspaciosGeneral) {
            if (espacio.getNumeroEspacio() == numeroEspacio) {
                return espacio;
            }
        }
        for (Espacio espacio : listaEspaciosVIP) {
            if (espacio.getNumeroEspacio() == numeroEspacio) {
                return espacio;
            }
        }
        for (Espacio espacio : listaEspaciosElectricos) {
            if (espacio.getNumeroEspacio() == numeroEspacio) {
                return espacio;
            }
        }
        return null;
    }

    public Set<Espacio> obtenerEspaciosDisponibles(
            TipoSeccion seccion,
            DiaSemana dia,
            int horaInicio,
            int horaFin
    ) throws HorarioException {
        ValidReserva.validarHorario(dia, horaInicio, horaFin);
        Set<Espacio> espaciosQueEsanDisponibles = new HashSet<>();
        ArrayList<Espacio> todosLosEspaciosDeLaSeccion = conseguirListaDeEspaciosPorSeccion(seccion);
        for (Espacio espacioActual : todosLosEspaciosDeLaSeccion) {
            boolean estaLibre = espacioActual.estaDisponible(dia, horaInicio, horaFin);
            if (estaLibre == true) {
                espaciosQueEsanDisponibles.add(espacioActual);
            }
        }
        return espaciosQueEsanDisponibles;
    }

    public Reservacion crearReservacion(
            Estudiante estudiante,
            Auto auto,
            TipoSeccion seccion,
            DiaSemana dia,
            int horaInicio,
            int horaFin,
            int numeroEspacio,
            ArrayList<ServicioAdicional> servicios,
            int mes,
            int diaMes,
            int year
    ) throws HorarioException, NoDisponibleException {
        ValidReserva.validarHorario(dia, horaInicio, horaFin);
        Espacio espacioElegido = buscarEspacioPorNumero(numeroEspacio);
        if (espacioElegido == null) {
            throw new NoDisponibleException("El espacio indicado no existe.");
        }
        if (espacioElegido.getSeccion() != seccion) {
            throw new NoDisponibleException("El espacio indicado no pertenece a la seccion seleccionada.");
        }
        if (!espacioElegido.estaDisponible(dia, horaInicio, horaFin)) {
            throw new NoDisponibleException("El espacio seleccionado no esta disponible en ese horario.");
        }

        double costoTotal = CalcCosto.calcularCostoTotal(
                seccion,
                horaInicio,
                horaFin,
                servicios
        );
        String idDeEstaReservacion = IdGen.generarIdIndividual(
                mes,
                diaMes,
                year,
                numeroEspacio,
                horaInicio
        );
        Reservacion nuevaReservacion = new Reservacion(
                idDeEstaReservacion,
                "",
                estudiante,
                auto,
                espacioElegido,
                dia,
                horaInicio,
                horaFin,
                servicios,
                costoTotal
        );
        espacioElegido.agregarReservacion(nuevaReservacion);
        guardarReservacionEnMapas(nuevaReservacion);
        Transaccion transaccionNueva = new Transaccion(
                IdGen.nuevoIdTransaccion(),
                TipoTransaccion.RESERVACION,
                nuevaReservacion,
                costoTotal,
                "Reservacion creada exitosamente."
        );
        historialDeTransacciones.add(transaccionNueva);
        AccionProg accionParaDeshacer = new AccionProg(
                AccionProg.Tipo.RESERVAR,
                nuevaReservacion,
                null,
                0.0
        );
        pilaDeAcciones.push(accionParaDeshacer);
        return nuevaReservacion;
    }

    public ArrayList<Reservacion> crearReservacionSemanal(
            Estudiante estudiante,
            Auto auto,
            TipoSeccion seccion,
            int horaInicio,
            int horaFin,
            int numeroEspacio,
            ArrayList<ServicioAdicional> servicios,
            int mes,
            int diaInicioMes,
            int year
    ) 
    
    throws HorarioException, NoDisponibleException {
        ArrayList<Reservacion> reservacionesCreadas = new ArrayList<>();
        DiaSemana[] diasDeLaSemana = {
                DiaSemana.LUNES,
                DiaSemana.MARTES,
                DiaSemana.MIERCOLES,
                DiaSemana.JUEVES,
                DiaSemana.VIERNES
        };
        String idDeGrupo = IdGen.generarIdGrupo(
                mes,
                diaInicioMes,
                year,
                numeroEspacio,
                horaInicio
        );

    Espacio espacioElegido = buscarEspacioPorNumero(numeroEspacio);
        if (espacioElegido == null) {
            throw new NoDisponibleException("El espacio indicado no existe.");
        }
        if (espacioElegido.getSeccion() != seccion) {
            throw new NoDisponibleException("El espacio indicado no pertenece a la seccion seleccionada.");
        }
        for (int i = 0; i < diasDeLaSemana.length; i++) {
            ValidReserva.validarHorario(diasDeLaSemana[i], horaInicio, horaFin);
            boolean estaLibreEseDia = espacioElegido.estaDisponible(diasDeLaSemana[i], horaInicio, horaFin);
            if (!estaLibreEseDia) {
                throw new NoDisponibleException(
                        "El espacio no esta disponible para el dia " + diasDeLaSemana[i] + "."
                );
            }
        }

        for (int i = 0; i < diasDeLaSemana.length; i++) {
            int diaCalendarioActual = diaInicioMes + i;
            double costoDeEsteDia = CalcCosto.calcularCostoTotal(
                    seccion,
                    horaInicio,
                    horaFin,
                    servicios
            );
            String idDeEstaReservacion = IdGen.generarIdIndividual(
                    mes,
                    diaCalendarioActual,
                    year,
                    numeroEspacio,
                    horaInicio
            );
            Reservacion reservacionDeEsteDia = new Reservacion(
                    idDeEstaReservacion,
                    idDeGrupo,
                    estudiante,
                    auto,
                    espacioElegido,
                    diasDeLaSemana[i],
                    horaInicio,
                    horaFin,
                    servicios,
                    costoDeEsteDia
            );

            espacioElegido.agregarReservacion(reservacionDeEsteDia);
            guardarReservacionEnMapas(reservacionDeEsteDia);
            reservacionesCreadas.add(reservacionDeEsteDia);
            Transaccion transaccionDeEsteDia = new Transaccion(
                    IdGen.nuevoIdTransaccion(),
                    TipoTransaccion.RESERVACION,
                    reservacionDeEsteDia,
                    costoDeEsteDia,
                    "Reservacion semanal creada para " + diasDeLaSemana[i] + "."
            );

            historialDeTransacciones.add(transaccionDeEsteDia);
            pilaDeAcciones.push(new AccionProg(
                    AccionProg.Tipo.RESERVAR,
                    reservacionDeEsteDia,
                    null,
                    0.0
            ));
        }
        return reservacionesCreadas;
    }

