package model;

public class AccionProg {
    public enum Tipo{ RESERVAR, CANCELAR, CAMBIAR}

    private Tipo tipo;
    private Reservacion reservacion;
    private Espacio espacioAnterior;

    public AccionProg(Tipo tipo, Reservacion reservacion, Espacio espacioAnterior){
        this.tipo = tipo;
        this.reservacion = reservacion;
        this.espacioAnterior = espacioAnterior;
    }

    public Tipo geTipo(){
        return tipo;
    }

    public Reservacion getReservacion(){
        return reservacion;
    }

    public Espacio getEspacioAnterior(){
        return espacioAnterior;
    }

    @Override
    public String toString(){
        String espacioStr;

        if(espacioAnterior != null){
            espacioStr = "Espacio #" + espacioAnterior.getNumeroEspacio();
        }else{
            espacioStr = "(ninguno)";
        }

        return "Accion: " + tipo +
                "\n Reservacion: " + reservacion.getIdReservacion() +
                "\nEspacio anterior: " + espacioStr;
    }
}
