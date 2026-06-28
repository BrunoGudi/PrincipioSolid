package com.mycompany.solidplataformaeduvirtual.Evaluaciones;

/**
 * Patrón Strategy.
 * Implementa el cálculo de nota final para un Examen (promedio simple).
 */
public class Examen implements Evaluacion{
    @Override
    public double calcularNotaFinal(double [] notas) {
        double suma = 0;
        for(double nota : notas) {
            suma += nota;
        }
        return suma / notas.length;
    }
}