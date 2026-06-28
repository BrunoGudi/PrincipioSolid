package com.mycompany.solidplataformaeduvirtual.ClienteServidor;

import com.mycompany.solidplataformaeduvirtual.Validaciones.ValidadorNotas;
import com.mycompany.solidplataformaeduvirtual.Validaciones.NotaInvalidaException;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClienteGrafico extends JFrame {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    // Componentes gráficos
    private JTextField txtNombre, txtEmail, txtNotas;
    private JComboBox<String> cbTipoEvaluacion, cbFacultad, cbCatedra;
    private JTextArea txtAreaConsola;
    private JButton btnRegistrar, btnListar, btnCalcular;

    public ClienteGrafico() {
        super("Plataforma Educativa Virtual - Académica");
        conectarServidor();
        inicializarUI();
    }

    private void conectarServidor() {
        try {
            socket = new Socket("localhost", 5000);
            // PATRÓN DECORATOR 
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar al servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void inicializarUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel de Pestañas
        JTabbedPane pestañas = new JTabbedPane();

        // Pestaña 1: Registro Estudiante
        JPanel panelRegistro = new JPanel(new GridLayout(4, 2, 10, 10));
        panelRegistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelRegistro.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelRegistro.add(txtNombre);

        panelRegistro.add(new JLabel("Correo Electrónico:"));
        txtEmail = new JTextField();
        panelRegistro.add(txtEmail);

        btnRegistrar = new JButton("Registrar Estudiante");
        btnRegistrar.addActionListener(e -> registrarEstudiante());
        panelRegistro.add(new JLabel()); // Espacio vacío
        panelRegistro.add(btnRegistrar);
        pestañas.addTab("Registrar Estudiante", panelRegistro);

        // Pestaña 2: Calificaciones e Historial
        JPanel panelCalcular = new JPanel(new GridBagLayout());
        panelCalcular.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Facultad
        gbc.gridx = 0; gbc.gridy = 0;
        panelCalcular.add(new JLabel("Facultad/Establecimiento:"), gbc);
        cbFacultad = new JComboBox<>(new String[]{"Ingeniería", "Medicina", "Derecho"});
        cbFacultad.addActionListener(e -> actualizarCatedras());
        gbc.gridx = 1;
        panelCalcular.add(cbFacultad, gbc);

        // Cátedra
        gbc.gridx = 0; gbc.gridy = 1;
        panelCalcular.add(new JLabel("Cátedra:"), gbc);
        cbCatedra = new JComboBox<>();
        actualizarCatedras();
        gbc.gridx = 1;
        panelCalcular.add(cbCatedra, gbc);

        // Tipo Evaluación
        gbc.gridx = 0; gbc.gridy = 2;
        panelCalcular.add(new JLabel("Tipo Evaluación:"), gbc);
        cbTipoEvaluacion = new JComboBox<>(new String[]{"Examen", "Trabajo Práctico", "Proyecto Final"});
        gbc.gridx = 1;
        panelCalcular.add(cbTipoEvaluacion, gbc);

        // Notas individuales
        gbc.gridx = 0; gbc.gridy = 3;
        panelCalcular.add(new JLabel("Notas (separadas por coma):"), gbc);
        txtNotas = new JTextField("7.5,8.0,9.0");
        gbc.gridx = 1;
        panelCalcular.add(txtNotas, gbc);

        btnCalcular = new JButton("Calcular y Validar Promedio");
        btnCalcular.addActionListener(e -> calcularPromedio());
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelCalcular.add(btnCalcular, gbc);

        pestañas.addTab("Cálculo y Cátedras", panelCalcular);

        // Consola / Logs del Sistema en la parte inferior
        txtAreaConsola = new JTextArea(10, 50);
        txtAreaConsola.setEditable(false);
        txtAreaConsola.setBackground(Color.DARK_GRAY);
        txtAreaConsola.setForeground(Color.GREEN);
        JScrollPane scroll = new JScrollPane(txtAreaConsola);

        add(pestañas, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        // Botón de Listado General
        JPanel panelBotonesSup = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnListar = new JButton("Listar Estudiantes de la BD");
        btnListar.addActionListener(e -> listarEstudiantes());
        panelBotonesSup.add(btnListar);
        add(panelBotonesSup, BorderLayout.NORTH);
    }

    // metodo que actualiza las catedras segun la facultad seleccionada
    private void actualizarCatedras() {
        String facultadSeleccionada = (String) cbFacultad.getSelectedItem();
        if (cbCatedra != null) {
            cbCatedra.removeAllItems();
            if (facultadSeleccionada != null) {
                switch (facultadSeleccionada) {
                    case "Ingeniería" -> {
                        cbCatedra.addItem("Programación Avanzada");
                        cbCatedra.addItem("Álgebra");
                        cbCatedra.addItem("Física I");
                    }
                    case "Medicina" -> {
                        cbCatedra.addItem("Anatomía");
                        cbCatedra.addItem("Fisiología");
                        cbCatedra.addItem("Farmacología");
                    }
                    case "Derecho" -> {
                        cbCatedra.addItem("Derecho Civil");
                        cbCatedra.addItem("Derecho Penal");
                        cbCatedra.addItem("Introducción al Derecho");
                    }
                }
            }
        }
    }

    // metodo que registra un estudiante en la base de datos
    private void registrarEstudiante() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            out.writeInt(1); // Opción 1: Registrar
            out.writeUTF(nombre);
            out.writeUTF(email);

            String respuesta = in.readUTF();
            txtAreaConsola.append("Servidor: " + respuesta + "\n");
            txtNombre.setText("");
            txtEmail.setText("");
        } catch (Exception ex) {
            txtAreaConsola.append("Error: " + ex.getMessage() + "\n");
        }
    }

    // metodo que lista los estudiantes de la base de datos
    private void listarEstudiantes() {
        try {
            out.writeInt(2); // Opción 2: Listar
            String lista = in.readUTF();
            txtAreaConsola.append("\n--- Lista de Estudiantes ---\n" + lista + "\n");
        } catch (Exception ex) {
            txtAreaConsola.append("Error: " + ex.getMessage() + "\n");
        }
    }

    private void calcularPromedio() {
        String notasTexto = txtNotas.getText().trim();
        if (notasTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar al menos una nota", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            //convertimos las notas ingresadas
            String[] notasString = notasTexto.split(",");
            double[] notas = new double[notasString.length];
            for (int i = 0; i < notasString.length; i++) {
                notas[i] = Double.parseDouble(notasString[i].trim());
            }

            // ejecuta la validacion
            ValidadorNotas.validarArreglo(notas);

            // enviamos la solicitud al servidor
            out.writeInt(3); // Calculamos promedio
            
            // enviamos el tipo de evaluacion y el array de notas
            int tipoEval = cbTipoEvaluacion.getSelectedIndex() + 1;
            out.writeInt(tipoEval);
            out.writeInt(notas.length);
            for (double nota : notas) {
                out.writeDouble(nota);
            }

            // recibimos el resultado del servidor
            double resultado = in.readDouble();

            String mensajeResultado = String.format("Promedio Calculado (%s en Cátedra %s - %s): %.2f",
                    cbTipoEvaluacion.getSelectedItem(),
                    cbCatedra.getSelectedItem(),
                    cbFacultad.getSelectedItem(),
                    resultado);

            txtAreaConsola.append(mensajeResultado + "\n");
            JOptionPane.showMessageDialog(this, mensajeResultado, "Resultado Exitoso", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese números válidos en las notas.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (NotaInvalidaException nie) {
            // Manejo de la validación fallida
            JOptionPane.showMessageDialog(this, nie.getMessage(), "Validación de Notas Fallida", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            txtAreaConsola.append("Error: " + ex.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClienteGrafico cliente = new ClienteGrafico();
            cliente.setVisible(true);
        });
    }
}
