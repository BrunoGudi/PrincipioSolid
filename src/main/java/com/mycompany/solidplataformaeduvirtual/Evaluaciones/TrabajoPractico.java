package com.mycompany.solidplataformaeduvirtual.Evaluaciones;

/**
 * ESTRATEGIA CONCRETA del Patrón Strategy.
 * Implementa el cálculo de nota final para un Trabajo Práctico (promedio con bonificación del 5%).
 */
public class TrabajoPractico implements Evaluacion{
  //Promedio con una bonificación del 5%.
  @Override
  public double calcularNotaFinal(double [] notas) {
        double suma = 0;

        for(double nota : notas) {
            suma += nota;
        }

        double promedio = suma / notas.length;

        return promedio * 1.05;
   }
    
}
