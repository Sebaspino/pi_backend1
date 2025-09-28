package com.example.application.views.inicio;

import java.util.ArrayList;
import java.util.List;

public class SistemaSerenityLab {

    // Listas para almacenar objetos en lugar de arrays de String
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Horario> horariosDisponibles = new ArrayList<>();
    private static List<Cita> citasAgendadas = new ArrayList<>();

    // Métodos de gestión de datos públicos para ser usados por otras clases
    public static List<Horario> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    public static List<Cita> getCitasAgendadas() {
        return citasAgendadas;
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
}

