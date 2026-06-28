package com.mycompany.solidplataformaeduvirtual.Modelos;


public class Estudiante {
    private int id;
    private String nombre;
    private String email;

    public Estudiante(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
    
}