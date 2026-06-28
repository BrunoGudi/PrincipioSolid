package com.mycompany.solidplataformaeduvirtual.Evaluaciones;
/**
 * PATRÓN FACTORY
 * Esta clase se encarga de centralizar y abstraer la creación de objetos de tipo Evaluacion.
 * El cliente (por ejemplo, el Servidor o la UI) no necesita saber las clases concretas (Examen, ProyectoFinal, etc.)
 * ni cómo se instancian, solo solicita el tipo deseado.
 */
public class EvaluacionFactory {
    public static Evaluacion obtenerEvaluacion(int tipo) {
        return switch (tipo) {
            case 1 -> new Examen(); // Examen individual (30%)
            case 2 -> new TrabajoPractico(); // Trabajo Práctico (30%)
            case 3 -> new ProyectoFinal(); // Proyecto Final (40%)
            default -> throw new IllegalArgumentException("Tipo de evaluación no soportado: " + tipo);
        };
    }
}