package com.mycompany.solidplataformaeduvirtual.ClienteServidor;

import com.mycompany.solidplataformaeduvirtual.Modelos.Estudiante;
import com.mycompany.solidplataformaeduvirtual.*;
import com.mycompany.solidplataformaeduvirtual.Evaluaciones.Evaluacion;
import com.mycompany.solidplataformaeduvirtual.Evaluaciones.EvaluacionFactory;

import java.io.*;
import java.net.Socket;

public class ServidorHilo extends Thread {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    // Objeto para trabajar con la tabla estudiantes en la BD
    private EstudianteBd estudianteBd;

    public ServidorHilo(Socket socket, DataInputStream in, DataOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.estudianteBd = new EstudianteBd();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int opcion = in.readInt();

                if (opcion == 0) {
                    break;
                }

                switch (opcion) {
                    case 1:
                        //Agregar alumno
                        String nombre = in.readUTF();
                        String email = in.readUTF();
                        // Se crea el estudiante
                        Estudiante estudiante = new Estudiante(nombre, email);
                        estudianteBd.guardar(estudiante);
                        // Se muestra que el estudiante ha sido registrado exitosamente
                        out.writeUTF("Estudiante " + nombre + " registrado exitosamente.");
                        break;

                    case 2:
                        // listar estudiantes
                        String lista = estudianteBd.listar();
                        out.writeUTF(lista);
                        break;

                    case 3:
                        //calcular promedio, pedimos cantidad de notas y creamos un array
                          int tipo = in.readInt();
                          int cantidadNotas = in.readInt();
                          double[] notas = new double[cantidadNotas];
                          for (int i = 0; i < cantidadNotas; i++) {
                              notas[i] = in.readDouble();
                            }
                        // Creamos la evaluacion segun el tipo que sea
                        Evaluacion evaluacion = EvaluacionFactory.obtenerEvaluacion(tipo);

                        double resultado = evaluacion.calcularNotaFinal(notas);

                        out.writeDouble(resultado);

                        break;

                    default:
                        out.writeUTF("Opción inválida");
                }
            }
            
        } catch (IOException e) {
            System.out.println("Cliente desconectado.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}