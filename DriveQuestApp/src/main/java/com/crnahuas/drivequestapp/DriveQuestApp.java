package com.crnahuas.drivequestapp;

// Clase principal del sistema DriveQuest Rentals.
// Punto de entrada del programa, donde se inicia la carga de datos,
// se muestra el menú de usuario y se asegura la correcta ejecución
// según la pauta de evaluación.
public class DriveQuestApp {
    public static void main(String[] args) {
        // Crear instancia del gestor de flotilla
        FlotillaManager flotillaManager = new FlotillaManager();

        // Cargar vehículos desde archivo en un hilo separado
        Thread cargaThread = new Thread(() -> {
            try {
                flotillaManager.cargarVehiculosDesdeArchivo("vehiculos.txt");
            } catch (Exception e) {
                System.out.println("Error al cargar datos de vehículos: " + e.getMessage());
            }
        });

        cargaThread.start();

        try {
            cargaThread.join(); // Esperar que termine la carga
        } catch (InterruptedException e) {
            System.out.println("Carga interrumpida: " + e.getMessage());
        }

        // Iniciar interfaz de usuario por consola
        MenuUI menu = new MenuUI(flotillaManager);
        menu.iniciar();
    }
}