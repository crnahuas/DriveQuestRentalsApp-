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

    private final FlotillaManager gestor;
    private final Scanner scanner;

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
                    agregarVehiculo(); // Agrega nuevos vehículos de forma manual.
                case 2 ->
                    gestor.listarVehiculos(); // Vehículos disponibles.
                case 3 ->
                    gestor.mostrarBoletas(); // Muestra boletas de vehículos arrendados.
                case 4 ->
                    gestor.mostrarArriendosLargos(); // Muestra los arriendos de 7 días o más.
                case 5 ->
                    gestor.guardarVehiculosEnArchivo(); // Exporta vehículos arrendados.
                case 6 ->
                    gestor.cargarVehiculosDesdeArchivo("vehiculos_importar.txt"); // Lista de nuevo vehículos.
                case 7 ->
                    arrendarVehiculoDesdeLista(); // Permite arrendar un vehículo.
                case 0 ->
                    System.out.println("Saliendo del sistema...");
                default ->
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    // Muestra el menú principal
    private void mostrarMenu() {
        System.out.println("\n==== MENÚ PRINCIPAL ====\n");
        System.out.println("1. Agregar vehículo");
        System.out.println("2. Listar vehículos");
        System.out.println("3. Mostrar boletas");
        System.out.println("4. Mostrar arriendos largos (Desde 7 días)");
        System.out.println("5. Exportar vehículos");
        System.out.println("6. Importar vehículos");
        System.out.println("7. Arrendar vehículo");
        System.out.println("0. Salir");
    }

    // Permite agregar un nuevo vehículo a la lista (evita duplicados)
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

        switch (tipo) {
            case 1 -> {
                VehiculoCarga vc = new VehiculoCarga(patente, marca, 0, 0);
                if (gestor.agregarVehiculo(vc)) {
                    System.out.println("Vehículo de carga agregado exitosamente.");
                } else {
                    System.out.println("Error: La patente ya está registrada.");
                }
            }
            case 2 -> {
                VehiculoPasajeros vp = new VehiculoPasajeros(patente, marca, 0, 0);
                if (gestor.agregarVehiculo(vp)) {
                    System.out.println("Vehículo de pasajeros agregado exitosamente.");
                } else {
                    System.out.println("Error: La patente ya está registrada.");
                }
            }
            default ->
                System.out.println("Opción inválida.");
        }
    }

    // Permite arrendar un vehículo existente y completar sus datos.
    private void arrendarVehiculoDesdeLista() {
        List<Vehiculo> lista = gestor.obtenerListaVehiculos();
        if (lista.isEmpty()) {
            System.out.println("No hay vehículos disponibles para arriendo.");
            return;
        }

        System.out.println("\n-- Seleccionar Vehículo para Arriendo --");
        for (int i = 0; i < lista.size(); i++) {
            System.out.print("[" + (i + 1) + "] ");
            lista.get(i).mostrarDatos();
        }

        int opcion = leerEntero("Seleccione el número del vehículo a arrendar: ");
        if (opcion < 1 || opcion > lista.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Vehiculo seleccionado = lista.get(opcion - 1);
        int dias = leerEntero("Ingrese la cantidad de días de arriendo: ");
        seleccionado.setDiasArriendo(dias);

        if (seleccionado instanceof VehiculoCarga vc) {
            double nuevaCarga = leerDecimal("Ingrese la carga máxima en kg: ");
            vc.setCargaMaximaKg(nuevaCarga);
        } else if (seleccionado instanceof VehiculoPasajeros vp) {
            int pasajeros = leerEntero("Ingrese la cantidad de pasajeros: ");
            vp.setCantidadPasajeros(pasajeros);
        }

        // Muestra boleta al arrendar
        if (seleccionado instanceof Calculable calc) {
            calc.mostrarBoleta();
        }

        // Elimina el vehículo de la lista original y lo agrega a la lista de arrendados.
        gestor.eliminarVehiculo(seleccionado);
        gestor.registrarVehiculoArrendado(seleccionado);

        System.out.println("El vehículo fue arrendado correctamente.");
    }

    // Lee un número entero desde consola con validación.
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

    // Lee un número decimal desde consola con validación.
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
