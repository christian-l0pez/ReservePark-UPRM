package model;
import java.util.ArrayList;
public class CalcCosto {
    private TipoSeccion seccion;
    private int horaInicial;
    private int horaFinal;
    private ArrayList<ServicioAdicional> servicios;

    public static final double CANCELACION = 10.0;
    public static final double CAMBIO = 6.00;

    public CalcCosto(TipoSeccion seccion, int horaInicial, int horaFinal, ArrayList<ServicioAdicional> servicios){
        this.seccion = seccion;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.servicios = servicios;
    }

    public TipoSeccion getSeccion(){
        return seccion;
    }

    public int getHoraInicial(){
        return horaInicial;
    }
    
    public int getHoraFinal(){
        return horaFinal;
    }

    public ArrayList<ServicioAdicional> getServicios(){
        return servicios;
    }

    public double precio(){
        switch(seccion){
            case GENERAL: return 2.00;
            case VIP: return 4.00;
            case ELECTRICO: return 8.00;
            default: return 0.0;
        }
    }

    public double costoTotal(){
        int horas = horaFinal - horaInicial;
        double costo = horas * precio();
        double extras = 0.0;

        if(servicios != null){
            for(ServicioAdicional s : servicios){
                extras = extras + s.getCosto();
            }
        }
        return costo + extras;
    }

    @Override
    public String toString() {
    return "CalcCosto: " + seccion +
            "\nHorario: " + horaInicial + ":00 - " + horaFinal + ":00" +
            String.format("\nPrecio por hora: $%.2f", precio()) +
            String.format("\nCosto total: $%.2f", costoTotal());
}
}
