package model;
import java.util.ArrayList;
public class CalcCosto {

    public static final double TARIFA_GENERAL = 2.00;
    public static final double TARIFA_VIP = 4.00;
    public static final double TARIFA_ELECTRICO = 8.00;

    public static final double COSTO_CANCELACION = 10.00;
    public static final double COSTO_CAMBIO_ESPACIO = 6.00;

    public static final double COSTO_AIRE_GOMAS = 1.00;
    public static final double COSTO_FLUIDOS = 2.00;
    public static final double COSTO_LAVADO_EXTERIOR = 50.00;
    public static final double COSTO_LIQUIDO_FRENOS = 35.00;
    public static final double COSTO_ACEITE_MOTOR = 40.00;

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

    public static double calcularCostoBase(TipoSeccion seccion, int horaInicio, int horaFin) {
        int horas = horaFin - horaInicio;
        return horas * obtenerTarifaPorHora(seccion);
    }

    public static double calcularCostoServicios(ArrayList<ServicioAdicional> servicios) {
        double total = 0.00;

        if (servicios != null) {
            for (ServicioAdicional servicio : servicios) {
                total = total + servicio.getCosto();
            }
        }

        return total;
    }

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

    public static double calcularCostoCambio(
            TipoSeccion nuevaSeccion,
            int horaInicio,
            int horaFin,
            ArrayList<ServicioAdicional> servicios
    ) {
        return calcularCostoTotal(nuevaSeccion, horaInicio, horaFin, servicios)
                + COSTO_CAMBIO_ESPACIO;
    }

    public static double calcularCostoCancelacion() {
        return COSTO_CANCELACION;
    }
}