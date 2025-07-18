package com.crnahuas.drivequestapp.menuui;

import com.crnahuas.drivequestapp.controlador.FlotillaManager;
import com.crnahuas.drivequestapp.modelo.Calculable;
import com.crnahuas.drivequestapp.modelo.Vehiculo;
import com.crnahuas.drivequestapp.modelo.VehiculoCarga;
import com.crnahuas.drivequestapp.modelo.VehiculoPasajeros;
import java.util.Scanner;

// Clase que representa el menú de usuario por consola.
public class MenuUI {

    private FlotillaManager gestor;
    private Scanner scanner;

    public MenuUI(FlotillaManager gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarVehiculo();
                case 2 -> gestor.listarVehiculos();
                case 3 -> mostrarBoletas();
                case 4 -> gestor.mostrarArriendosLargos();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n==== MENÚ PRINCIPAL ====");
        System.out.println("1. Agregar vehículo");
        System.out.println("2. Listar vehículos");
        System.out.println("3. Mostrar boletas");
        System.out.println("4. Mostrar arriendos largos (>=7 días)");
        System.out.println("0. Salir");
    }

    private void agregarVehiculo() {
        System.out.println("\n-- Agregar Vehículo --");
        System.out.println("Seleccione el tipo de vehículo:");
        System.out.println("1. Vehículo de carga");
        System.out.println("2. Vehículo de pasajeros");
        int tipo = leerEntero("Opción: ");

        System.out.print("Patente (ejemplo: CLPM23): ");
        String patente = scanner.nextLine();
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        int dias = leerEntero("Días de arriendo: ");

        if (tipo == 1) {
            double carga = leerDecimal("Carga máxima en kg: ");
            VehiculoCarga vc = new VehiculoCarga(patente, marca, dias, carga);
            if (gestor.agregarVehiculo(vc)) {
                System.out.println("Vehículo de carga agregado con éxito.");
            } else {
                System.out.println("Error: Ya existe un vehículo con esa patente.");
            }
        } else if (tipo == 2) {
            int pasajeros = leerEntero("Cantidad de pasajeros: ");
            VehiculoPasajeros vp = new VehiculoPasajeros(patente, marca, dias, pasajeros);
            if (gestor.agregarVehiculo(vp)) {
                System.out.println("Vehículo de pasajeros agregado con éxito.");
            } else {
                System.out.println("Error: Ya existe un vehículo con esa patente.");
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private void mostrarBoletas() {
        System.out.println("\n-- Boletas de Arriendo --");
        gestor.listarVehiculos();
        System.out.println("\n(Mostrando boletas individuales...)");
        for (Vehiculo v : gestor.listaVehiculos()) {
            if (v instanceof Calculable c) {
                c.mostrarBoleta();
            }
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente nuevamente.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente nuevamente.");
            }
        }
    }
}
