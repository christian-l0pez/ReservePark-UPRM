# Sistema de Reservacion de Estacionamiento - UPRM
██████╗ ███████╗███████╗███████╗██████╗ ██╗   ██╗███████╗    ██████╗  █████╗ ██████╗ ██╗  ██╗
██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝    ██╔══██╗██╔══██╗██╔══██╗██║ ██╔╝
██████╔╝█████╗  ███████╗█████╗  ██████╔╝██║   ██║█████╗      ██████╔╝███████║██████╔╝█████╔╝ 
██╔══██╗██╔══╝  ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝      ██╔═══╝ ██╔══██║██╔══██╗██╔═██╗ 
██║  ██║███████╗███████║███████╗██║  ██║ ╚████╔╝ ███████╗    ██║     ██║  ██║██║  ██║██║  ██╗
╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝    ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝
- Pixel art credits: https://patorjk.com/software/taag/#p=display&f=Fire+Font-k&t=Type+Something+&x=none&v=4&h=4&w=80&we=false

¿Qué es esto?
Sistema de consola para manejar reservaciones de estacionamiento universitario. El operador registra estudiantes, verifica disponibilidad, crea/cancela reservaciones y ve reportes todo desde un menú de terminal como PUTY.
Para correr: ejecuta MenuOp.java

El Estacionamiento
--------------------------------------------------------
|  PLANTA  ·  200 espacios                             |
--------------------------------------------------------
|   GENERAL     |     VIP       |     ELÉCTRICO        |
|   100 esp.    |    50 esp.    |      50 esp.         |
|   $2 / hora   |   $4 / hora   |     $8 / hora        |
--------------------------------------------------------
Cada sección puede tener servicios adicionales: chequeo de aire · chequeo de fluidos · lavado exterior · cambio de aceite · líquido de frenos

Menú Principal
┌─────────────────────────────────────────┐
│  [1]  Hacer reservación                 │
│  [2]  Cancelar reservación              │
│  [3]  Cambiar sección                   │
│  [4]  Deshacer última acción            │
│  [5]  Ver reservaciones de la semana    │
│  [6]  Ver reservaciones > 2 horas       │
│  [7]  Ver reservaciones por costo       │
│  [8]  Ver por ventana de tiempo         │
│  [9]  Ver reservaciones de estudiante   │
│  [10] Ver transacciones                 │
│  [11] Ver lista de espera               │
│  [12] Ver espacios disponibles          │
│  [13] Salir                             │
└─────────────────────────────────────────┘

Arquitectura
El sistema está dividido en clases con las mas responsables siendo:
Clase:                   Qué hace:
Estacionamiento         Lógica central del sistema
Reservacion             Representa una reservación (estudiante, auto, espacio, costo, estado)
Estudiante              Info del estudiante (nombre, ID, email, teléfono)
Auto                    Info del vehículo (tablilla, marca, modelo, año)
Espacio                 Espacio físico con su sección y reservaciones
Transaccion             Log de cada movimiento del sistema
CalcCosto               Cálculo de costos, cargos por cancelación y cambio
ValidReserva            Validación de horarios
IdGen                   Generador de IDs únicos
MenuOp                  Interfaz de consola

Estructuras de Datos
La elección de cada estructura fue adrede:
ArrayList   →  listas de espacios y reservaciones
HashSet     →  espacios disponibles
LinkedList  →  historial de transacciones 
HashMap     →  búsqueda por tablilla, ID de estudiante, ID de reservación
Stack       →  deshacer la última acción 
Queue       →  lista de espera por sección

Flujo de una Reservación
Operador ingresa datos
        │
        ▼
ValidReserva verifica horario
  7AM–4PM inicio · 8AM–5PM fin
  mínimo 1h · máximo 8h
        │
        ▼
Estacionamiento verifica disponibilidad
        │
     ┌──┴──┐
  hay esp. │  no hay esp.
        │           │
        ▼           ▼
   Reservacion    Queue (lista de espera)
   se crea
        │
        ▼
   CalcCosto calcula total
        │
        ▼
   Transaccion queda en log

Excepciones Personalizadas
javaHorarioException         // horario fuera de rango o duración inválida
NoDisponibleException    // espacio inexistente o ya ocupado
ReservNoEncontradaException  // no se encontró la reservación

Validaciones

Hora de inicio: entre 7:00 AM y 4:00 PM
Hora final: entre 8:00 AM y 5:00 PM
Duración: mínimo 1 hora, máximo 8 horas
No se puede cancelar/cambiar una reservación ya cancelada
El espacio debe existir y pertenecer a la sección seleccionada


Limitaciones

No hay sistema de pago real — solo se calculan y muestran los costos
La lista de espera es básica 
No hay persistencia de datos entre ejecuciones

Resultados:
El sistema permite manejar reservaciones de estacionamiento desde el punto de vista de un operador. 
El operador puede registrar estudiantes y autos, verificar disponibilidad, crear reservaciones, cancelar, cambiar espacios y ver reportes basicos de las reservaciones y transacciones.


Colaboración
Grupo: Jose Perez (jose-perez16) y Christian López(christian-l0pez)