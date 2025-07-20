package com.crnahuas.drivequestapp;

import com.crnahuas.drivequestapp.controlador.FlotillaManager;
import com.crnahuas.drivequestapp.menuui.MenuUI;

// Clase principal del sistema DriveQuest Rentals.
public class DriveQuestApp {

    public static void main(String[] args) {
        System.out.println("\n=== BIENVENIDO A DRIVEQUEST RENTALS ===");

        // Gestión de vehículos.
        FlotillaManager gestor = new FlotillaManager();

        // Cargar vehículos desde archivo si existe.
        gestor.cargarVehiculosDesdeArchivo("vehiculos_base.txt");

        // Interfaz de usuario por consola.
        MenuUI menu = new MenuUI(gestor);
        menu.iniciar();

        System.out.println("\nGracias por utilizar el sistema DriveQuest Rental. Hasta pronto.");
    }
}
