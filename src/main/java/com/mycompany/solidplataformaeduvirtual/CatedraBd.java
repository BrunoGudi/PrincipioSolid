package com.mycompany.solidplataformaeduvirtual;

import com.mycompany.solidplataformaeduvirtual.Modelos.Catedra;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatedraBd {
    private final ConexionBd conexionBd;

    public CatedraBd(ConexionBd conexionBd) {
        this.conexionBd = conexionBd;
    }

    public void guardar(Catedra catedra) {
        String sql = "INSERT INTO catedras (nombre, facultad_id) VALUES (?, ?)";
        try (Connection conn = conexionBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, catedra.getNombre());
            pstmt.setInt(2, catedra.getFacultadId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar cátedra: " + e.getMessage());
        }
    }

    public List<Catedra> listarPorFacultad(int facultadId) {
        List<Catedra> lista = new ArrayList<>();
        String sql = "SELECT * FROM catedras WHERE facultad_id = ?";
        try (Connection conn = conexionBd.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, facultadId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Catedra(rs.getInt("id"), rs.getString("nombre"), rs.getInt("facultad_id")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar cátedras: " + e.getMessage());
        }
        return lista;
    }
}
