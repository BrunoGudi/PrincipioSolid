package com.mycompany.solidplataformaeduvirtual;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * PATRÓN SINGLETON
 * Se utiliza para asegurar que exista una única instancia de la conexión a la base de datos 
 * en toda la aplicación, centralizando su acceso y evitando instanciaciones redundantes.
 */
public class ConexionBd {
    private static final String URL = "jdbc:mysql://localhost:3306/plataforma_educativa";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static ConexionBd instancia;
    private ConexionBd() {}
    public static synchronized ConexionBd getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBd();
        }
        return instancia;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }
}

// BASE DE DATOS
//CREATE DATABASE plataforma_educativa;
//  USE plataforma_educativa;
//
//  CREATE TABLE estudiantes (
//      id INT AUTO_INCREMENT PRIMARY KEY,
//      nombre VARCHAR(100) NOT NULL,
//      email VARCHAR(100) NOT NULL
//  );