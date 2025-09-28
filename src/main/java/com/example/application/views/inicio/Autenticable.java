package com.example.application.views.inicio;

public interface Autenticable {
    boolean iniciarSesion(String nombreUsuario, String contrasena);
    String obtenerRol();
}
