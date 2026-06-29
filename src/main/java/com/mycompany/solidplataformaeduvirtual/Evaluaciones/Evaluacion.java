package com.mycompany.solidplataformaeduvirtual.Evaluaciones;
/**
 * PATRÓN STRATEGY
 * Esta interfaz define el contrato para la "estrategia" de cálculo de nota.
 * Permite cambiar dinámicamente el algoritmo utilizado para calcular la nota final
 * (examen, trabajo práctico o proyecto final) sin modificar el código que lo consume 
 */
public interface Evaluacion {
    // Calcula la nota final segun el tipo de evaluacion
    double calcularNotaFinal(double [] notas);
}
