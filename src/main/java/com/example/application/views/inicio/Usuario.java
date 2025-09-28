package com.example.application.views.inicio;

import java.util.Scanner;

public abstract class Usuario implements Autenticable {
    protected String nombreUsuario;
    protected String contrasena;
    protected String ocupacion;
    protected Scanner scanner;

    public Usuario(String nombreUsuario, String contrasena, String ocupacion) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.ocupacion = ocupacion;
        this.scanner = new Scanner(System.in);
    }

    // Métodos abstractos que deben ser implementados por las subclases
    public abstract void mostrarMenu();

    // Métodos de la interfaz Autenticable
    @Override
    public boolean iniciarSesion(String nombreUsuario, String contrasena) {
        return this.nombreUsuario.equals(nombreUsuario) && this.contrasena.equals(contrasena);
    }

    @Override
    public String obtenerRol() {
        return this.ocupacion;
    }

    // Getters
    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
