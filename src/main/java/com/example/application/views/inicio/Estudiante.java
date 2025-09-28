package com.example.application.views.inicio;

public class Estudiante extends Usuario {
    public Estudiante(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, "Estudiante");
    }

    @Override
    public void mostrarMenu() {
        // Implementación específica para el menú de Estudiante
        System.out.println("Menú de Estudiante");
    }
}
