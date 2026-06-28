package com.mycompany.solidplataformaeduvirtual.Modelos;

public class Facultad {
    private int id;
    private String nombre;
    public Facultad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Facultad(String nombre) {
        this.nombre = nombre;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
}