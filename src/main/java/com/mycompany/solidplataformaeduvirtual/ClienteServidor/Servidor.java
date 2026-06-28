package com.mycompany.solidplataformaeduvirtual.ClienteServidor;

import java.io.*;
import java.net.*;

public class Servidor {
     public static void main(String[] args) {

        try {
            //Se inicializa en el puerto 5000
            ServerSocket servidor = new ServerSocket(5000);

            // Se muestra que el servidor ha sido iniciado
            System.out.println("Servidor iniciado");
            // Bucle infinito para aceptar conexiones
            while(true){
                // Se acepta la conexión
                Socket socket = servidor.accept();
                // Flujos de entrada y salida de datos
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                // Se crea un hilo para manejar al cliente
                ServidorHilo hilo = new ServidorHilo(socket, in, out);
                hilo.start();

                System.out.println("Cliente conectado");
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}