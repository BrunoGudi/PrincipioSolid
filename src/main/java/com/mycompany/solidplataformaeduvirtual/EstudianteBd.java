package com.mycompany.solidplataformaeduvirtual;

import com.mycompany.solidplataformaeduvirtual.Modelos.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EstudianteBd {
    private ConexionBd conexionBd;

    public EstudianteBd() {
        // Se obtiene la instancia única del Singleton ConexionBd
        this.conexionBd = ConexionBd.getInstancia();
    }

    public void guardar(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (nombre, email) VALUES (?, ?)";

        try (Connection conn = conexionBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, estudiante.getNombre());
            pstmt.setString(2, estudiante.getEmail());
            pstmt.executeUpdate();
            System.out.println("Estudiante guardado exitosamente.");

        } catch (SQLException e) {
            System.err.println("Error al guardar el estudiante: " + e.getMessage());
        }
    }

    public String listar() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM estudiantes";

        try (Connection conn = conexionBd.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                sb.append("ID: ").append(id)
                        .append(" | Nombre: ").append(nombre)
                        .append(" | Email: ").append(email).append("\n");
            }

        } catch (SQLException e) {
            return "Error al listar estudiantes: " + e.getMessage();
        }

        if (sb.length() == 0) {
            return "No hay estudiantes registrados.";
        }

        return sb.toString();
    }

}
