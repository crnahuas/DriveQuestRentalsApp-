package com.crnahuas.drivequestapp.controlador;

import com.crnahuas.drivequestapp.modelo.Vehiculo;
import com.crnahuas.drivequestapp.modelo.VehiculoCarga;
import com.crnahuas.drivequestapp.modelo.VehiculoPasajeros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Clase auxiliar para la gestión de la flotilla de vehículos.
public class FlotillaManager {

    // Mapa para validar patentes.
    private Map<String, Vehiculo> mapaVehiculos;

    // Lista sincronizada para almacenamiento seguro.
    private List<Vehiculo> listaVehiculos;

    public List<Vehiculo> listaVehiculos() {
     return listaVehiculos;
    }
    
    // Constructor
    public FlotillaManager() {
        this.mapaVehiculos = Collections.synchronizedMap(new HashMap<>());
        this.listaVehiculos = Collections.synchronizedList(new ArrayList<>());
    }

    // Agrega un vehículo si la patente no está registrada.
    public boolean agregarVehiculo(Vehiculo v) {
        if (!mapaVehiculos.containsKey(v.getPatente())) {
            mapaVehiculos.put(v.getPatente(), v);
            listaVehiculos.add(v);
            return true;
        }
        return false; // Ya existe un vehículo con esa patente
    }

    // Lista todos los vehículos registrados.
    public void listarVehiculos() {
        if (listaVehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            for (Vehiculo v : listaVehiculos) {
                v.mostrarDatos();
            }
        }
    }

    // Muestra la cantidad de vehículos con arriendos de 7 días o más.
    public void mostrarArriendosLargos() {
        List<Vehiculo> filtrados = listaVehiculos.stream()
            .filter(v -> v.getDiasArriendo() >= 7)
            .toList();

        System.out.println("\nCantidad de vehículos con arriendo largo (>=7 días): " + filtrados.size());

        if (filtrados.isEmpty()) {
            System.out.println("No hay vehículos con arriendo largo.");
        } else {
            System.out.println("Vehículos con arriendo largo:");
            for (Vehiculo v : filtrados) {
                v.mostrarDatos();
            }
        }
    }

    // Carga los vehículos desde archivo de texto plano.
    public void cargarVehiculosDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 5) {
                    String tipo = partes[0];
                    String patente = partes[1];
                    String marca = partes[2];
                    int dias = Integer.parseInt(partes[3]);
                    String valor = partes[4];

                    Vehiculo v = null;
                    if (tipo.equalsIgnoreCase("carga")) {
                        double carga = Double.parseDouble(valor);
                        v = new VehiculoCarga(patente, marca, dias, carga);
                    } else if (tipo.equalsIgnoreCase("pasajeros")) {
                        int pasajeros = Integer.parseInt(valor);
                        v = new VehiculoPasajeros(patente, marca, dias, pasajeros);
                    }

                    if (v != null) {
                        agregarVehiculo(v);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar vehículos desde archivo: " + e.getMessage());
        }
    }
}
