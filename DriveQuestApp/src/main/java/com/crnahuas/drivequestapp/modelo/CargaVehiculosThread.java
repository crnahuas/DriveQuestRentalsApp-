package com.crnahuas.drivequestapp.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

// Clase que representa un hilo para cargar vehículos desde un archivo.
public class CargaVehiculosThread implements Runnable {

    private final List<Vehiculo> listaCompartida;
    private final String rutaArchivo;

    // Constructor del hilo de carga.
    public CargaVehiculosThread(List<Vehiculo> listaCompartida, String rutaArchivo) {
        this.listaCompartida = listaCompartida;
        this.rutaArchivo = rutaArchivo;
    }

    // Lee el archivo y agrega vehículos válidos a la lista compartida.
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5) {
                    String tipo = datos[0].toLowerCase();
                    String patente = datos[1];
                    String marca = datos[2];
                    int dias = Integer.parseInt(datos[3]);

                    Vehiculo nuevo = null;

                    if (tipo.equals("carga")) {
                        double carga = Double.parseDouble(datos[4]);
                        nuevo = new VehiculoCarga(patente, marca, dias, carga);
                    } else if (tipo.equals("pasajeros")) {
                        int pasajeros = Integer.parseInt(datos[4]);
                        nuevo = new VehiculoPasajeros(patente, marca, dias, pasajeros);
                    }

                    if (nuevo != null) {
                        synchronized (listaCompartida) {
                            listaCompartida.add(nuevo);
                            System.out.println("Vehículo cargado: " + nuevo.getPatente());
                        }
                    }
                }
            }
            System.out.println("Archivo cargad correctamente: " + rutaArchivo);
        } catch (Exception e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
    }
}
