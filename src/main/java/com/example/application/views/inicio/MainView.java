package com.example.application.views.inicio;

import java.util.List;
import java.util.ArrayList;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker; // ✅ Import movido al lugar correcto
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private Usuario usuarioActual = null;

    private final H1 titulo = new H1("SerenityLab");

    private final VerticalLayout layoutLogin = new VerticalLayout();
    private final VerticalLayout layoutEstudiante = new VerticalLayout();
    private final VerticalLayout layoutPsicologo = new VerticalLayout();

    public MainView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setPadding(true);
        add(titulo);
        mostrarVistaLogin();
    }

    private void mostrarVistaLogin() {
        removeAll();
        layoutLogin.removeAll();
        add(titulo, layoutLogin);

        H3 bienvenida = new H3("¡Bienvenido a SerenityLab!");
        bienvenida.getStyle().set("color", "#1976d2");

        TextField usuarioField = new TextField("Nombre de usuario");
        usuarioField.setClearButtonVisible(true);
        PasswordField contrasenaField = new PasswordField("Contraseña");
        contrasenaField.setClearButtonVisible(true);
        ComboBox<String> ocupacionBox = new ComboBox<>("Ocupación");
        ocupacionBox.setItems("Estudiante", "Psicologo");
        ocupacionBox.setClearButtonVisible(true);

        Button loginButton = new Button("Iniciar Sesión", e -> {
            String nombre = usuarioField.getValue().trim();
            String pass = contrasenaField.getValue().trim();
            if (nombre.isEmpty() || pass.isEmpty()) {
                Notification.show("Por favor, completa usuario y contraseña.", 3000, Notification.Position.MIDDLE);
                return;
            }
            for (Usuario usuario : SistemaSerenityLab.getUsuarios()) {
                if (usuario.getNombreUsuario().equalsIgnoreCase(nombre) && usuario.iniciarSesion(nombre, pass)) {
                    usuarioActual = usuario;
                    usuarioField.clear();
                    contrasenaField.clear();
                    ocupacionBox.clear();
                    actualizarVista();
                    return;
                }
            }
            Notification.show("Usuario o contraseña incorrectos.", 3000, Notification.Position.MIDDLE);
        });

        Button registerButton = new Button("Registrarse", e -> {
            String nombre = usuarioField.getValue().trim();
            String pass = contrasenaField.getValue().trim();
            String ocupacion = ocupacionBox.getValue();
            if (nombre.isEmpty() || pass.isEmpty() || ocupacion == null) {
                Notification.show("Completa todos los campos para registrarte.", 3000, Notification.Position.MIDDLE);
                return;
            }
            for (Usuario user : SistemaSerenityLab.getUsuarios()) {
                if (user.getNombreUsuario().equalsIgnoreCase(nombre)) {
                    Notification.show("El nombre de usuario ya existe.", 3000, Notification.Position.MIDDLE);
                    return;
                }
            }
            if (ocupacion.equalsIgnoreCase("Estudiante")) {
                SistemaSerenityLab.getUsuarios().add(new Estudiante(nombre, pass));
            } else {
                SistemaSerenityLab.getUsuarios().add(new Psicologo(nombre, pass));
            }
            usuarioField.clear();
            contrasenaField.clear();
            ocupacionBox.clear();
            Notification.show("¡Registro exitoso! Ahora puedes iniciar sesión.", 3000, Notification.Position.MIDDLE);
        });

        layoutLogin.setSpacing(true);
        layoutLogin.setPadding(true);
        layoutLogin.add(
                bienvenida,
                usuarioField,
                contrasenaField,
                ocupacionBox,
                loginButton,
                registerButton,
                new H3("Usuarios registrados: " + SistemaSerenityLab.getUsuarios().size()),
                new H3("Horarios disponibles: " + SistemaSerenityLab.getHorariosDisponibles().size()),
                new H3("Citas agendadas: " + SistemaSerenityLab.getCitasAgendadas().size()));
    }

    private void actualizarVista() {
        removeAll();
        add(titulo);
        if (usuarioActual == null) {
            mostrarVistaLogin();
        } else {
            H3 infoUsuario = new H3(
                    "Usuario: " + usuarioActual.getNombreUsuario() + " (" + usuarioActual.obtenerRol() + ")");
            infoUsuario.getStyle().set("color", "#388e3c");
            add(infoUsuario);
            if (usuarioActual instanceof Estudiante) {
                mostrarVistaEstudiante();
            } else if (usuarioActual instanceof Psicologo) {
                mostrarVistaPsicologo();
            } else {
                usuarioActual = null;
                mostrarVistaLogin();
            }
        }
    }

    private void mostrarVistaPsicologo() {
        layoutPsicologo.removeAll();
        add(new H3("Menú del Psicólogo: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "")),
                layoutPsicologo);

        DateTimePicker dateTimePicker = new DateTimePicker("Añadir horario disponible");
        Button anadirHorarioButton = new Button("Añadir", e -> {
            if (dateTimePicker.getValue() == null) {
                Notification.show("Selecciona una fecha y hora.", 3000, Notification.Position.MIDDLE);
                return;
            }
            String fechaYHora = dateTimePicker.getValue().toString();
            SistemaSerenityLab.getHorariosDisponibles().add(new Horario(usuarioActual.getNombreUsuario(), fechaYHora));
            dateTimePicker.clear();
            Notification.show("Horario añadido.", 2000, Notification.Position.MIDDLE);
            actualizarVista();
        });

        Grid<Cita> gridCitas = new Grid<>(Cita.class);
        gridCitas.setItems(SistemaSerenityLab.getCitasAgendadas());
        gridCitas.setColumns("nombreEstudiante", "fechaYHora");
        gridCitas.getColumnByKey("nombreEstudiante").setHeader("Estudiante");
        gridCitas.getColumnByKey("fechaYHora").setHeader("Fecha y Hora");
gridCitas.getStyle().setHeight("100px");

        H3 resumen = new H3("Citas agendadas: " + SistemaSerenityLab.getCitasAgendadas().size());
        resumen.getStyle().set("color", "#d32f2f");

        Button logoutButton = new Button("Cerrar Sesión", e -> {
            usuarioActual = null;
            Notification.show("Sesión cerrada.", 2000, Notification.Position.MIDDLE);
            actualizarVista();
        });

        layoutPsicologo.setSpacing(true);
        layoutPsicologo.setPadding(true);
        layoutPsicologo.add(dateTimePicker, anadirHorarioButton, new H3("Citas Agendadas"), gridCitas, resumen, logoutButton); // ✅ Punto y coma añadido y botón de logout agregado
    }

    private void mostrarVistaEstudiante() {
        layoutEstudiante.removeAll();
        add(new H3("Menú del Estudiante: " + (usuarioActual != null ? usuarioActual.getNombreUsuario() : "")),
                layoutEstudiante);

        Grid<Horario> gridHorarios = new Grid<>(Horario.class);
        gridHorarios.setItems(SistemaSerenityLab.getHorariosDisponibles());
        gridHorarios.setColumns("fechaYHora", "nombrePsicologo");
        gridHorarios.getColumnByKey("fechaYHora").setHeader("Horario");
        gridHorarios.getColumnByKey("nombrePsicologo").setHeader("Psicólogo");

        gridHorarios.asSingleSelect().addValueChangeListener(event -> {
            Horario horarioSeleccionado = event.getValue();
            if (horarioSeleccionado != null) {
                SistemaSerenityLab.getHorariosDisponibles().remove(horarioSeleccionado);
                SistemaSerenityLab.getCitasAgendadas().add(new Cita(usuarioActual.getNombreUsuario(),
                        horarioSeleccionado.getNombrePsicologo(), horarioSeleccionado.getFechaYHora()));
                Notification.show("Cita agendada con éxito.", 2000, Notification.Position.MIDDLE);
                actualizarVista();
            }
        });

        List<Cita> misCitas = new ArrayList<>();
        for (Cita cita : SistemaSerenityLab.getCitasAgendadas()) {
            if (usuarioActual != null
                    && cita.getNombreEstudiante().equalsIgnoreCase(usuarioActual.getNombreUsuario())) {
                misCitas.add(cita);
            }
        }
        Grid<Cita> gridMisCitas = new Grid<>(Cita.class);
        gridMisCitas.setItems(misCitas);
        gridMisCitas.setColumns("fechaYHora", "nombrePsicologo");
        gridMisCitas.getColumnByKey("fechaYHora").setHeader("Fecha y Hora");
        gridMisCitas.getColumnByKey("nombrePsicologo").setHeader("Psicólogo");

        H3 resumen = new H3("Tus citas: " + misCitas.size() + " | Horarios disponibles: "
                + SistemaSerenityLab.getHorariosDisponibles().size());
        resumen.getStyle().set("color", "#1976d2");

        gridMisCitas.asSingleSelect().addValueChangeListener(event -> {
            Cita citaSeleccionada = event.getValue();
            if (citaSeleccionada != null) {
                SistemaSerenityLab.getCitasAgendadas().remove(citaSeleccionada);
                SistemaSerenityLab.getHorariosDisponibles()
                        .add(new Horario(citaSeleccionada.getNombrePsicologo(), citaSeleccionada.getFechaYHora()));
                Notification.show("Cita cancelada con éxito.", 2000, Notification.Position.MIDDLE);
                actualizarVista();
            }
        });

        Button logoutButton = new Button("Cerrar Sesión", e -> {
            usuarioActual = null;
            Notification.show("Sesión cerrada.", 2000, Notification.Position.MIDDLE);
            actualizarVista();
        });

        layoutEstudiante.setSpacing(true);
        layoutEstudiante.setPadding(true);
        layoutEstudiante.add(
                new H3("Agendar Cita (selecciona un horario)"), gridHorarios,
                new H3("Cancelar Cita (selecciona una de tus citas)"), gridMisCitas,
                resumen,
                logoutButton);
    }
}