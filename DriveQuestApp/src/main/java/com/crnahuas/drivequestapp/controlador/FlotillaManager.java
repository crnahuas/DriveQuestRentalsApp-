package com.crnahuas.drivequestapp.controlador;

import com.crnahuas.drivequestapp.modelo.Vehiculo;
import com.crnahuas.drivequestapp.modelo.VehiculoCarga;
import com.crnahuas.drivequestapp.modelo.VehiculoPasajeros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Clase auxiliar para la gestión de la flotilla de vehículos.
public class FlotillaManager {

    private Map<String, Vehiculo> mapaVehiculos; // Para validar patentes únicas
    private List<Vehiculo> listaVehiculos; // Lista principal de vehículos

    // Constructor que inicializa las estructuras de datos.
    public FlotillaManager() {
        mapaVehiculos = new HashMap<>();
        listaVehiculos = Collections.synchronizedList(new ArrayList<>());
    }

    // Agrega un vehículo a la flotilla, validando que la patente no esté duplicada.
    public boolean agregarVehiculo(Vehiculo v) {
        if (mapaVehiculos.containsKey(v.getPatente())) {
            return false; // Patente duplicada
        }
        mapaVehiculos.put(v.getPatente(), v);
        listaVehiculos.add(v);
        return true;
    }

    // Lista todos los vehículos registrados mostrando sus datos.
    public void listarVehiculos() {
        if (listaVehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            System.out.println("\n== Lista de Vehículos ==");
            for (Vehiculo v : listaVehiculos) {
                v.mostrarDatos();
            }
        }
    }

    // Muestra los vehículos con arriendo igual o mayor a 7 días.
    public void mostrarArriendosLargos() {
        long cantidad = listaVehiculos.stream()
                .filter(v -> v.getDiasArriendo() >= 7)
                .count();

        System.out.println("Cantidad de vehículos con arriendo largo (>=7 días): " + cantidad);
        System.out.println("Vehículos con arriendo largo:");
        for (Vehiculo v : listaVehiculos) {
            if (v.getDiasArriendo() >= 7) {
                v.mostrarDatos();
            }
        }
    }

    // Devuelve la lista de vehículos para uso externo (por ejemplo: interfaz).
    public List<Vehiculo> obtenerListaVehiculos() {
        return listaVehiculos;
    }

    // Guarda todos los vehículos en un archivo de texto plano.
    public void guardarVehiculosEnArchivo(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Vehiculo v : listaVehiculos) {
                writer.write(v.exportarComoTexto());
                writer.newLine();
            }
            System.out.println("Vehículos guardados exitosamente en " + archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar vehículos en archivo: " + e.getMessage());
        }
    }

    // Carga vehículos desde un archivo de texto plano, ignorando líneas inválidas.
    public void cargarVehiculosDesdeArchivo(String archivo) {
        File file = new File(archivo);
        if (!file.exists()) {
            System.out.println("Archivo no encontrado: " + archivo);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length < 5) {
                    continue;
                }

                String tipo = partes[0];
                String patente = partes[1];
                String marca = partes[2];
                int dias = Integer.parseInt(partes[3]);

                if (tipo.equalsIgnoreCase("carga")) {
                    double carga = Double.parseDouble(partes[4]);
                    VehiculoCarga vc = new VehiculoCarga(patente, marca, dias, carga);
                    agregarVehiculo(vc);
                } else if (tipo.equalsIgnoreCase("pasajeros")) {
                    int pasajeros = Integer.parseInt(partes[4]);
                    VehiculoPasajeros vp = new VehiculoPasajeros(patente, marca, dias, pasajeros);
                    agregarVehiculo(vp);
                }
            }
            System.out.println("Vehículos cargados exitosamente desde " + archivo);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar vehículos desde archivo: " + archivo + " (" + e.getMessage() + ")");
        }
    }
}
