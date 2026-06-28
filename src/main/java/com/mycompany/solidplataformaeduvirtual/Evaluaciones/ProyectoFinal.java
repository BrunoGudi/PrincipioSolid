package com.mycompany.solidplataformaeduvirtual.Evaluaciones;

/**
 * ESTRATEGIA CONCRETA del Patrón Strategy.
 * Implementa el cálculo de nota final para un Proyecto Final (promedio más 1 punto extra).
 */
public class ProyectoFinal implements Evaluacion {
    //Promedio normal más 1 punto extra
     @Override
    public double calcularNotaFinal(double [] notas) {
        double suma = 0;

        for(double nota : notas) {
            suma += nota;
        }

        double promedio = suma / notas.length;

        return promedio + 1;
    }
        
}
