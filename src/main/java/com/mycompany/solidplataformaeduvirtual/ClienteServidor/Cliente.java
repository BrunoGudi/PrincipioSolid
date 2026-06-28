package com.mycompany.solidplataformaeduvirtual.ClienteServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {

        try {
            //Se inicializa en el puerto 5000
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Conectado al servidor exitosamente.");
            // Entrada de datos
            DataInputStream in = new DataInputStream(socket.getInputStream());
            // Salida de datos
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //Se crea el hilo del cliente
            ClienteHilo clienteHilo = new ClienteHilo(in, out);
            clienteHilo.start();
            // Se espera a que el hilo termine

            clienteHilo.join();

            socket.close();
            // Se muestra que la conexión ha finalizado
            System.out.println("Conexión finalizada.");
            
        // En caso de error se muestra el mensaje de error
        } catch(Exception e){
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}