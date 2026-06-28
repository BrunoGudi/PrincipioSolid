package com.mycompany.solidplataformaeduvirtual.Validaciones;

public class ValidadorNotas {
    private static final double MIN_NOTA = 0.0;
    private static final double MAX_NOTA = 10.0;
    public static void validarIndividual(double nota) throws NotaInvalidaException {
        if (nota < MIN_NOTA || nota > MAX_NOTA) {
            throw new NotaInvalidaException("La nota " + nota + " es inválida. Debe estar entre " + MIN_NOTA + " y " + MAX_NOTA);
        }
    }
    public static void validarArreglo(double[] notas) throws NotaInvalidaException {
        if (notas == null || notas.length == 0) {
            throw new NotaInvalidaException("Debe ingresar al menos una nota para realizar el cálculo.");
        }
        for (double nota : notas) {
            validarIndividual(nota);
        }
    }
}