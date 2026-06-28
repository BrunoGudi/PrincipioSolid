package com.mycompany.solidplataformaeduvirtual.ClienteServidor;

import java.io.*;
import java.util.Scanner;

public class ClienteHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;

    public ClienteHilo(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        try {
            do {
                System.out.println("\n===== PLATAFORMA EDUCATIVA =====");
                System.out.println("1. Registrar estudiante");
                System.out.println("2. Listar estudiantes");
                System.out.println("3. Calcular promedio");
                System.out.println("0. Salir");
                System.out.print("Opcion: ");

                opcion = sc.nextInt();
                sc.nextLine();

                out.writeInt(opcion);

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre: ");
                        out.writeUTF(sc.nextLine());

                        System.out.print("Email: ");
                        out.writeUTF(sc.nextLine());

                        // Leer confirmación del servidor
                        System.out.println("Servidor: " + in.readUTF());
                        break;

                    case 2:
                        // Leer la lista de estudiantes enviada por el servidor
                        System.out.println("\n--- Lista de Estudiantes ---");
                        System.out.println(in.readUTF());
                        break;

                    case 3:
                        System.out.println("\nTIPO DE EVALUACION");
                        System.out.println("1. Examen");
                        System.out.println("2. Trabajo Practico");
                        System.out.println("3. Proyecto Final");

                        int tipo = sc.nextInt();

                        out.writeInt(tipo);

                        System.out.print("Cantidad de notas: ");

                       int cantidad = sc.nextInt();

                       out.writeInt(cantidad);

                       for (int i = 0; i < cantidad; i++) {

                         System.out.print("Nota " + (i + 1) + ": ");

                         double nota = sc.nextDouble();

                         out.writeDouble(nota);
                        }

                       System.out.println(
                         "Servidor - Promedio: "
                         + in.readDouble()
                      );

                       break;

                    case 0:
                        System.out.println("Desconectando del servidor...");
                        break;

                    default:
                        System.out.println("Opcion incorrecta");
                }

            } while (opcion != 0);

        } catch (IOException e) {
            System.out.println("Error de conexión con el servidor: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}