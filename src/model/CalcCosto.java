package model;
import java.util.ArrayList;

// Esta clase es para calcular los costos del sistema de estacionamiento
// Aqui se guardan las tarifas por tipo de espacio y los costos de servicios adicionales, cancelaciones y cambios de espacio
public class CalcCosto {

    public static final double TARIFA_GENERAL = 2.00;// Tarifa por hora para espacios generales
    public static final double TARIFA_VIP = 4.00;// Tarifa por hora para espacios vip
    public static final double TARIFA_ELECTRICO = 8.00;// Tarifa por hora para espacios electricos

    public static final double COSTO_CANCELACION = 10.00;// Costo por cancelar una reservacion
    public static final double COSTO_CAMBIO_ESPACIO = 6.00;// Costo que se cobra al cambiar de espacio
 // Costos de los servicios adicionales disponibles
    public static final double COSTO_AIRE_GOMAS = 1.00;
    public static final double COSTO_FLUIDOS = 2.00;
    public static final double COSTO_LAVADO_EXTERIOR = 50.00;
    public static final double COSTO_LIQUIDO_FRENOS = 35.00;
    public static final double COSTO_ACEITE_MOTOR = 35.00;
    // Este metodo devuelve la tarifa por hora dependiendo del tipo de secciony si la seccion no existe, devuelve 0
    public static double obtenerTarifaPorHora(TipoSeccion seccion) {
        if (seccion == TipoSeccion.GENERAL) {
            return TARIFA_GENERAL;
        } else if (seccion == TipoSeccion.VIP) {
            return TARIFA_VIP;
        } else if (seccion == TipoSeccion.ELECTRICO) {
            return TARIFA_ELECTRICO;
        } else {
            return 0.00;
        }
    }
    // Este metodo calcula el costo base de la reservacion primero las horas y despues se mult por ;a tarifa
    public static double calcularCostoBase(TipoSeccion seccion, int horaInicio, int horaFin) {
        int horas = horaFin - horaInicio;
        return horas * obtenerTarifaPorHora(seccion);
    }
    // Este metodo suma el costo de todos los servicios adicionales si en null da 0
    public static double calcularCostoServicios(ArrayList<ServicioAdicional> servicios) {
        double total = 0.00;

        if (servicios != null) {
            for (ServicioAdicional servicio : servicios) {
                total = total + servicio.getCosto();
            }
        }

        return total;
    }   
    // Este metodo calcula el costo total de una reservacion
    public static double calcularCostoTotal(
            TipoSeccion seccion,
            int horaInicio,
            int horaFin,
            ArrayList<ServicioAdicional> servicios
    ) {
        double costoBase = calcularCostoBase(seccion, horaInicio, horaFin);
        double costoServicios = calcularCostoServicios(servicios);

        return costoBase + costoServicios;
    }
    // Este metodo calcula el costo cuando se cambia una reservacion de espacio
    public static double calcularCostoCambio(
            TipoSeccion nuevaSeccion,
            int horaInicio,
            int horaFin,
            ArrayList<ServicioAdicional> servicios
    ) {
        return calcularCostoTotal(nuevaSeccion, horaInicio, horaFin, servicios)
                + COSTO_CAMBIO_ESPACIO;
    }
    // Devuelve el costo fijo de cancelacion
    public static double calcularCostoCancelacion() {
        return COSTO_CANCELACION;
    }
}