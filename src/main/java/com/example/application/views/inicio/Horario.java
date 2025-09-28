package com.example.application.views.inicio;

public class Horario {
    private String nombrePsicologo;
    private String fechaYHora;

    public Horario(String nombrePsicologo, String fechaYHora) {
        this.nombrePsicologo = nombrePsicologo;
        this.fechaYHora = fechaYHora;
    }

    @Override
    public String toString() {
        return this.fechaYHora + " (Psicólogo: " + this.nombrePsicologo + ")";
    }

    // Getters
    public String getNombrePsicologo() {
        return nombrePsicologo;
    }

    public String getFechaYHora() {
        return fechaYHora;
    }
}
