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

    private void guardarReservacionEnMapas(Reservacion reservacion) {
        String tablilla = reservacion.getAuto().getTablilla();
        String numEstudiante = reservacion.getEstudiante().getNumEstudiante();
        if (!mapaReservacionesPorTablilla.containsKey(tablilla)) {
            mapaReservacionesPorTablilla.put(tablilla, new ArrayList<>());
        }
        mapaReservacionesPorTablilla.get(tablilla).add(reservacion);
        if (!mapaReservacionesPorEstudiante.containsKey(numEstudiante)) {
            mapaReservacionesPorEstudiante.put(numEstudiante, new ArrayList<>());
        }
        mapaReservacionesPorEstudiante.get(numEstudiante).add(reservacion);
        mapaReservacionesPorId.put(reservacion.getIdReservacion(), reservacion);
    }
    private void quitarReservacionDeMapas(Reservacion reservacion) {
        String tablilla = reservacion.getAuto().getTablilla();
        String numEstudiante = reservacion.getEstudiante().getNumEstudiante();
        if (mapaReservacionesPorTablilla.containsKey(tablilla)) {
            mapaReservacionesPorTablilla.get(tablilla).remove(reservacion);
        }
        if (mapaReservacionesPorEstudiante.containsKey(numEstudiante)) {
            mapaReservacionesPorEstudiante.get(numEstudiante).remove(reservacion);
        }
        mapaReservacionesPorId.remove(reservacion.getIdReservacion());
    }

    public void cancelarReservacion(String idReservacion)
            throws ReservNoEncontradaException {
        Reservacion reservacionABuscar = mapaReservacionesPorId.get(idReservacion);
        if (reservacionABuscar == null) {
            throw new ReservNoEncontradaException("No se encontro la reservacion indicada.");
        }
        if (!reservacionABuscar.isActivo()) {
            throw new ReservNoEncontradaException("La reservacion ya esta cancelada.");
        }
        reservacionABuscar.cancelar();
        Transaccion transaccionCancelacion = new Transaccion(
                IdGen.nuevoIdTransaccion(),
                TipoTransaccion.CANCELACION,
                reservacionABuscar,
                0.00,
                "Reservacion cancelada."
        );
        historialDeTransacciones.add(transaccionCancelacion);
        Transaccion cargoPorCancelacion = new Transaccion(
                IdGen.nuevoIdTransaccion(),
                TipoTransaccion.CARGO_CANCELACION,
                reservacionABuscar,
                CalcCosto.calcularCostoCancelacion(),
                "Cargo por cancelacion de reservacion."
        );
        historialDeTransacciones.add(cargoPorCancelacion);
        pilaDeAcciones.push(new AccionProg(
                AccionProg.Tipo.CANCELAR,
                reservacionABuscar,
                null,
                reservacionABuscar.getCostoTotal()
        ));
        procesarListaEspera(reservacionABuscar.getEspacio().getSeccion());
    }

    public Reservacion cambiarEspacio(
            String idReservacion,
            int nuevoNumeroEspacio
    ) throws ReservNoEncontradaException, NoDisponibleException {
        Reservacion reservacion = mapaReservacionesPorId.get(idReservacion);
        if (reservacion == null) {
            throw new ReservNoEncontradaException("No se encontro la reservacion indicada.");
        }
        if (!reservacion.isActivo()) {
            throw new ReservNoEncontradaException("No se puede cambiar una reservacion cancelada.");
        }
        Espacio espacioAnterior = reservacion.getEspacio();
        Espacio espacioNuevo = buscarEspacioPorNumero(nuevoNumeroEspacio);
        if (espacioNuevo == null) {
            throw new NoDisponibleException("El nuevo espacio indicado no existe.");
        }
        boolean nuevoEspacioLibre = espacioNuevo.estaDisponible(
                reservacion.getDia(),
                reservacion.getHoraInicio(),
                reservacion.getHoraFin()
        );
        if (!nuevoEspacioLibre) {
            throw new NoDisponibleException("El nuevo espacio no esta disponible en ese horario.");
        }
        double costoAnteriorGuardado = reservacion.getCostoTotal();
        espacioAnterior.removerReservacion(reservacion);
        espacioNuevo.agregarReservacion(reservacion);
        reservacion.cambiarEspacio(espacioNuevo);
        double costoNuevo = CalcCosto.calcularCostoCambio(
                espacioNuevo.getSeccion(),
                reservacion.getHoraInicio(),
                reservacion.getHoraFin(),
                reservacion.getServicios()
        );
        reservacion.actualizarCostoTotal(costoNuevo);
        Transaccion transaccionDeCambio = new Transaccion(
                IdGen.nuevoIdTransaccion(),
                TipoTransaccion.CAMBIO_ESPACIO,
                reservacion,
                costoNuevo,
                "Cambio de espacio realizado. Cargo de cambio incluido."
        );
        historialDeTransacciones.add(transaccionDeCambio);
        pilaDeAcciones.push(new AccionProg(
                AccionProg.Tipo.CAMBIAR,
                reservacion,
                espacioAnterior,
                costoAnteriorGuardado
        ));
        return reservacion;
    }

    public void agregarAListaEspera(Reservacion reservacion) {
        TipoSeccion seccionDeLaReservacion = reservacion.getEspacio().getSeccion();
        Queue<Reservacion> colaCorrecta = conseguirColaDeEsperaPorSeccion(seccionDeLaReservacion);
        colaCorrecta.add(reservacion);
    }
    public Reservacion procesarListaEspera(TipoSeccion seccion) {
        Queue<Reservacion> colaCorrecta = conseguirColaDeEsperaPorSeccion(seccion);
        if (colaCorrecta.isEmpty()) {
            return null;
        }
        Reservacion siguienteEnLaCola = colaCorrecta.poll();
        return siguienteEnLaCola;
    }
    public ArrayList<Reservacion> buscarReservacionesPorTablilla(String tablilla) {
        boolean existeLaTablilla = mapaReservacionesPorTablilla.containsKey(tablilla);
        if (existeLaTablilla) {
            return mapaReservacionesPorTablilla.get(tablilla);
        }
        return new ArrayList<>();
    }
    public ArrayList<Reservacion> buscarReservacionesPorEstudiante(String numEstudiante) {
        boolean existeElEstudiante = mapaReservacionesPorEstudiante.containsKey(numEstudiante);
        if (existeElEstudiante) {
            return mapaReservacionesPorEstudiante.get(numEstudiante);
        }
        return new ArrayList<>();
    }
    public ArrayList<Reservacion> obtenerTodasLasReservaciones() {
        ArrayList<Reservacion> todasLasReservaciones = new ArrayList<>();
        for (Reservacion r : mapaReservacionesPorId.values()) {
            todasLasReservaciones.add(r);
        }
        return todasLasReservaciones;
    }
    public ArrayList<Reservacion> obtenerReservacionesMayorDosHoras(DiaSemana dia) {
        ArrayList<Reservacion> resultado = new ArrayList<>();
        for (Reservacion r : mapaReservacionesPorId.values()) {
            boolean esMismoDia = r.getDia() == dia;
            boolean esMasDeDosHoras = r.esMayorDeDosHoras();
            if (esMismoDia && esMasDeDosHoras) {
                resultado.add(r);
            }
        }
        return resultado;
    }
    public ArrayList<Reservacion> obtenerReservacionesPorRangoCosto(double minimo, double maximo) {
        ArrayList<Reservacion> resultado = new ArrayList<>();
        for (Reservacion r : mapaReservacionesPorId.values()) {
            double costo = r.getCostoTotal();
            if (costo >= minimo && costo <= maximo) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    public ArrayList<Reservacion> obtenerReservacionesPorPeriodo(
            DiaSemana dia,
            int horaInicio,
            int horaFin
    ) {
        ArrayList<Reservacion> resultado = new ArrayList<>();
        for (Reservacion Sahur : mapaReservacionesPorId.values()) {
            if (Sahur.getDia() == dia) {
                boolean hayTraslape = horaInicio < Sahur.getHoraFin() && horaFin > Sahur.getHoraInicio();
                if (hayTraslape == true) {
                    resultado.add(Sahur);
                }
            }
        }
        return resultado;
    }

    public LinkedList<Transaccion> obtenerTransacciones() {
        return historialDeTransacciones;
    }

    public void deshacerUltimaAccion() {
        if (pilaDeAcciones.isEmpty()) {
            System.out.println("No hay acciones para deshacer.");
            return;
        }

        AccionProg ultimaAccion = pilaDeAcciones.pop();
        Reservacion reservacionAfectada = ultimaAccion.getReservacion();
        if (ultimaAccion.getTipo() == AccionProg.Tipo.RESERVAR) {
            reservacionAfectada.cancelar();
            reservacionAfectada.getEspacio().removerReservacion(reservacionAfectada);
            quitarReservacionDeMapas(reservacionAfectada);
            historialDeTransacciones.add(new Transaccion(
                    IdGen.nuevoIdTransaccion(),
                    TipoTransaccion.CANCELACION,
                    reservacionAfectada,
                    0.00,
                    "Se deshizo una reservacion creada."
            ));
        } else if (ultimaAccion.getTipo() == AccionProg.Tipo.CANCELAR) {
            reservacionAfectada.reactivar();
            historialDeTransacciones.add(new Transaccion(
                    IdGen.nuevoIdTransaccion(),
                    TipoTransaccion.RESERVACION,
                    reservacionAfectada,
                    reservacionAfectada.getCostoTotal(),
                    "Se deshizo una cancelacion."
            ));



            
        } else if (ultimaAccion.getTipo() == AccionProg.Tipo.CAMBIAR) {
            Espacio espacioActualAhora = reservacionAfectada.getEspacio();
            Espacio espacioQueTeniaAntes = ultimaAccion.getEspacioAnterior();
            espacioActualAhora.removerReservacion(reservacionAfectada);
            espacioQueTeniaAntes.agregarReservacion(reservacionAfectada);
            reservacionAfectada.cambiarEspacio(espacioQueTeniaAntes);
            reservacionAfectada.actualizarCostoTotal(ultimaAccion.getCostoAnterior());
            historialDeTransacciones.add(new Transaccion(
                    IdGen.nuevoIdTransaccion(),
                    TipoTransaccion.CAMBIO_ESPACIO,
                    reservacionAfectada,
                    ultimaAccion.getCostoAnterior(),
                    "Se deshizo un cambio de espacio."
            ));
        }
    }
}
