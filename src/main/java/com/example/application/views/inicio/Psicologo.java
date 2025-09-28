package com.example.application.views.inicio;

public class Psicologo extends Usuario {
    public Psicologo(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, "Psicologo");
    }

    @Override
    public void mostrarMenu() {
        int opcionRol;
        do {
            System.out.println("\n--- Menú de Psicólogo ---");
            System.out.println("1. Añadir Horario de Disponibilidad");
            System.out.println("2. Ver Citas Agendadas");
            System.out.println("3. Cerrar Sesión");
            System.out.print("Ingresa tu opción: ");

            opcionRol = scanner.nextInt();
            scanner.nextLine();

            switch (opcionRol) {
                case 1:
                    anadirDisponibilidad();
                    break;
                case 2:
                    verCitasAgendadas();
                    break;
                case 3:
                    System.out.println("Cerrando sesión de Psicólogo...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intenta de nuevo.");
            }
        } while (opcionRol != 3);
    }

    private void anadirDisponibilidad() {
        System.out.println("\n--- Añadir Horario de Disponibilidad (Psicólogo) ---");
        System.out.println("Ingresa la fecha y hora disponible (ej. '2025-12-25 10:00 AM').");
        System.out.println("---");
        System.out.println();
        System.out.println("NOTA: Escribe 'fin' para terminar de añadir horarios.");
        System.out.println();

        while (true) {
            String horarioStr = scanner.nextLine();
            if (horarioStr.equalsIgnoreCase("fin")) {
                System.out.println("Finalizando la adición de horarios.");
                break;
            }
            if (!horarioStr.isEmpty()) {
                Horario nuevoHorario = new Horario(this.nombreUsuario, horarioStr);
                SistemaSerenityLab.getHorariosDisponibles().add(nuevoHorario);
                System.out.println("----> Horario añadido: " + horarioStr);
            } else {
                System.out.println("Por favor, ingresa una fecha y hora.");
            }
        }
    }

    private void verCitasAgendadas() {
        System.out.println("\n--- Citas Agendadas para " + this.nombreUsuario + " ---");
        boolean tieneCitas = false;
        for (Cita cita : SistemaSerenityLab.getCitasAgendadas()) {
            if (cita.getNombrePsicologo().equals(this.nombreUsuario)) {
                System.out.println(
                        "- Estudiante: " + cita.getNombreEstudiante() + ", Fecha y Hora: " + cita.getFechaYHora());
                tieneCitas = true;
            }
        }
        if (!tieneCitas) {
            System.out.println("No tienes citas agendadas en este momento.");
        }
    }
}
