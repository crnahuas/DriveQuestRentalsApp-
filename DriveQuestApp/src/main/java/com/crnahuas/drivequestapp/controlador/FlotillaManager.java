package com.crnahuas.drivequestapp.controlador;

import com.crnahuas.drivequestapp.modelo.CargaVehiculosThread;
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

    private Map<String, Vehiculo> mapaVehiculos; // Valida patentes únicas.
    private List<Vehiculo> listaVehiculos; // Lista de vehículos sincronizada.
    private List<Vehiculo> vehiculosArrendados; // Lista de vehículos arrendados.

    public FlotillaManager() {
        mapaVehiculos = new HashMap<>();
        listaVehiculos = Collections.synchronizedList(new ArrayList<>());
        vehiculosArrendados = Collections.synchronizedList(new ArrayList<>());
    }

    public boolean agregarVehiculo(Vehiculo v) {
        if (mapaVehiculos.containsKey(v.getPatente())) {
            return false;
        }
        mapaVehiculos.put(v.getPatente(), v);
        listaVehiculos.add(v);
        return true;
    }

    public void listarVehiculos() {
        if (listaVehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            System.out.println("\n== Lista de vehículos disponibles ==");
            for (Vehiculo v : listaVehiculos) {
                v.mostrarDatos();
            }
        }
    }

    public void mostrarArriendosLargos() {
        long cantidad = listaVehiculos.stream()
                .filter(v -> v.getDiasArriendo() >= 7)
                .count();

        System.out.println("Cantidad de vehículos con arriendo largo (Desde 7 días): " + cantidad);
        for (Vehiculo v : listaVehiculos) {
            if (v.getDiasArriendo() >= 7) {
                v.mostrarDatos();
            }
        }
    }

    public List<Vehiculo> obtenerListaVehiculos() {
        synchronized (listaVehiculos) {
            return new ArrayList<>(listaVehiculos);
        }
    }

    // Devuelve lista de vehículos que fueron arrendados.
    public List<Vehiculo> getVehiculosArrendados() {
        synchronized (vehiculosArrendados) {
            return new ArrayList<>(vehiculosArrendados);
        }
    }

    public void guardarVehiculosEnArchivo(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Vehiculo v : vehiculosArrendados) {
                writer.write(v.exportarComoTexto());
                writer.newLine();
            }
            System.out.println("Vehículos guardados exitosamente en " + archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar vehículos en archivo: " + e.getMessage());
        }
    }

    public void guardarVehiculosEnArchivo() {
        String archivo = "vehiculos_" + System.currentTimeMillis() + ".txt";
        guardarVehiculosEnArchivo(archivo);
    }

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
                if (partes.length < 3) {
                    continue;
                }
                String tipo = partes[0].trim();
                String patente = partes[1].trim();
                String marca = partes[2].trim();

                Vehiculo nuevo = null;

                if (tipo.equalsIgnoreCase("carga")) {
                    // Vehículo de carga.
                    nuevo = new VehiculoCarga(patente, marca, 0, 0.0);
                } else if (tipo.equalsIgnoreCase("pasajeros")) {
                    // Vehículo de pasajeros.
                    nuevo = new VehiculoPasajeros(patente, marca, 0, 0);
                }

                if (nuevo != null) {
                    agregarVehiculo(nuevo);
                }
            }
            System.out.println("Vehículos base cargados exitosamente desde: " + archivo);
        } catch (IOException e) {
            System.out.println("Error al cargar vehículos base: " + e.getMessage());
        }
    }

    // Carga desde archivo base: solo tipo, patente y marca, sin duplicados.
    public void cargarVehiculosBase(String archivo) {
        File file = new File(archivo);
        if (!file.exists()) {
            System.out.println("Archivo base no encontrado: " + archivo);
            return;
        }

        int cargados = 0, duplicados = 0, errores = 0, invalidados = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 3) {
                    errores++;
                    continue;
                }

                String tipo = partes[0].trim().toLowerCase();
                String patente = partes[1].trim();
                String marca = partes[2].trim();

                // Validar formato de patente.
                if (!patente.matches("^[a-zA-Z0-9]{5,8}$")) {
                    System.out.println("Patente inválida: " + patente);
                    invalidados++;
                    continue;
                }

                // Validar que marca no esté vacía.
                if (marca.isEmpty()) {
                    System.out.println("Marca vacía para patente: " + patente);
                    invalidados++;
                    continue;
                }

                // Validar duplicados.
                if (mapaVehiculos.containsKey(patente)) {
                    duplicados++;
                    continue;
                }

                Vehiculo nuevo = null;
                if (tipo.equals("carga")) {
                    nuevo = new VehiculoCarga(patente, marca, 0, 0.0);
                } else if (tipo.equals("pasajeros")) {
                    nuevo = new VehiculoPasajeros(patente, marca, 0, 0);
                } else {
                    System.out.println("Tipo inválido: " + tipo);
                    errores++;
                    continue;
                }

                agregarVehiculo(nuevo);
                cargados++;
            }

            System.out.println("\nCarga base completada:");
            System.out.println("Vehículos cargados: " + cargados);
            System.out.println("Duplicados ignorados: " + duplicados);
            System.out.println("Formato inválido: " + errores);
            System.out.println("Datos inválidos (patente o marca): " + invalidados);

        } catch (IOException e) {
            System.out.println("Error al leer archivo base: " + e.getMessage());
        }
    }

    public boolean eliminarVehiculo(Vehiculo v) {
        if (v == null || !mapaVehiculos.containsKey(v.getPatente())) {
            return false;
        }
        mapaVehiculos.remove(v.getPatente());
        synchronized (listaVehiculos) {
            return listaVehiculos.remove(v);
        }
    }

    // Agrega el vehículo arrendado a la lista interna.
    public void registrarVehiculoArrendado(Vehiculo v) {
        vehiculosArrendados.add(v);
    }

    // Mostrar boletas de los vehículos arrendados.
    public void mostrarBoletas() {
        if (vehiculosArrendados.isEmpty()) {
            System.out.println("No hay vehículos arrendados para mostrar boletas.");
            return;
        }

        System.out.println("\n-- Boletas de Vehículos Arrendados --");
        for (Vehiculo v : vehiculosArrendados) {
            v.mostrarBoleta();
        }
    }

    // Cargar vehículos con un hilo independiente.
    public void cargarVehiculosConHilos(String archivo) {
        Thread hiloCarga = new Thread(new CargaVehiculosThread(listaVehiculos, archivo));
        hiloCarga.start();
        try {
            hiloCarga.join();
        } catch (InterruptedException e) {
            System.out.println("Carga interrumpida: " + e.getMessage());
        }
    }
}
