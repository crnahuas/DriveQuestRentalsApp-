package com.crnahuas.drivequestapp.menuui;

import com.crnahuas.drivequestapp.controlador.FlotillaManager;
import com.crnahuas.drivequestapp.modelo.Calculable;
import com.crnahuas.drivequestapp.modelo.Vehiculo;
import com.crnahuas.drivequestapp.modelo.VehiculoCarga;
import com.crnahuas.drivequestapp.modelo.VehiculoPasajeros;
import java.util.List;
import java.util.Scanner;

// Clase que representa el menú de usuario por consola.
public class MenuUI {

    private FlotillaManager gestor;
    private Scanner scanner;

    // Constructor que recibe el gestor de flotilla y prepara el escáner.
    public MenuUI(FlotillaManager gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    // Método principal que inicia el menú y bucle de interacción.
    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 ->
                    agregarVehiculo();
                case 2 ->
                    gestor.listarVehiculos();
                case 3 ->
                    mostrarBoletas();
                case 4 ->
                    gestor.mostrarArriendosLargos();
                case 5 ->
                    gestor.guardarVehiculosEnArchivo("vehiculos.txt");
                case 6 ->
                    gestor.cargarVehiculosDesdeArchivo("vehiculos.txt");
                case 0 ->{
                    System.out.println("Guardando vehículos...");
                    gestor.guardarVehiculosEnArchivo("vehiculos.txt");
                    System.out.println("Gracias por usar DriveQuest Rental. ¡Hasta pronto!");
                }
                default ->
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    //  Muestra el menú principal en consola.
    private void mostrarMenu() {
        System.out.println("\n==== MENÚ PRINCIPAL ====");
        System.out.println("1. Agregar vehículo");
        System.out.println("2. Listar vehículos");
        System.out.println("3. Mostrar boletas");
        System.out.println("4. Mostrar arriendos largos (Desde 7 días)");
        System.out.println("5. Exportar vehículos");
        System.out.println("6. Importar vehículos");
        System.out.println("0. Salir");
    }

    // Permite al usuario agregar un nuevo vehículo con validación.
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

        switch (tipo) {
            case 1 -> {
                double carga = leerDecimal("Carga máxima en kg: ");
                VehiculoCarga vc = new VehiculoCarga(patente, marca, dias, carga);
                if (gestor.agregarVehiculo(vc)) {
                    System.out.println("Vehículo de carga agregado exitosamente.");
                } else {
                    System.out.println("La patente ya está registrada. Intente con otra.");
                }
                break;
            }
            case 2 -> {
                int pasajeros = leerEntero("Cantidad de pasajeros: ");
                VehiculoPasajeros vp = new VehiculoPasajeros(patente, marca, dias, pasajeros);
                if (gestor.agregarVehiculo(vp)) {
                    System.out.println("Vehículo de pasajeros agregado exitosamente.");
                } else {
                    System.out.println("La patente ya está registrada. Intente con otra.");
                }
                break;
            }
            default ->
                System.out.println("Opción no válida. Intente nuevamente.");
        }
    }

    // Muestra las boletas individuales de cada vehículo implementado.
    private void mostrarBoletas() {
        System.out.println("\n-- Boletas de Arriendo --");
        List<Vehiculo> lista = gestor.obtenerListaVehiculos();
        if (lista.isEmpty()) {
            System.out.println("No hay vehículos para mostrar boletas.");
            return;
        }
        for (Vehiculo v : lista) {
            if (v instanceof Calculable c) {
                c.mostrarBoleta();
            }
        }
    }

    // Lee y valida un número entero desde consola.
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

    // Lee y valida un número decimal desde consola.
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
