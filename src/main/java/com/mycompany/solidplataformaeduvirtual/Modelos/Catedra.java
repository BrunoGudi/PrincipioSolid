package com.mycompany.solidplataformaeduvirtual.Modelos;

public class Catedra {
    private int id;
    private String nombre;
    private int facultadId;
    public Catedra(int id, String nombre, int facultadId) {
        this.id = id;
        this.nombre = nombre;
        this.facultadId = facultadId;
    }
    public Catedra(String nombre, int facultadId) {
        this.nombre = nombre;
        this.facultadId = facultadId;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getFacultadId() { return facultadId; }
}

