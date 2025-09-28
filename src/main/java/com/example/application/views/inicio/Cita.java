package com.example.application.views.inicio;

public class Cita {
    private String nombreEstudiante;
    private String nombrePsicologo;
    private String fechaYHora;

    public Cita(String nombreEstudiante, String nombrePsicologo, String fechaYHora) {
        this.nombreEstudiante = nombreEstudiante;
        this.nombrePsicologo = nombrePsicologo;
        this.fechaYHora = fechaYHora;
    }

    // Getters
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String getNombrePsicologo() {
        return nombrePsicologo;
    }

    public String getFechaYHora() {
        return fechaYHora;
    }
}
