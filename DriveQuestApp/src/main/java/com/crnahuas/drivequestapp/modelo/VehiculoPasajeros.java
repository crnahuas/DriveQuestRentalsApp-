package com.crnahuas.drivequestapp.modelo;

// Clase que representa un vehículo de pasajeros.
public class VehiculoPasajeros extends Vehiculo implements Calculable {

    // Atributo.
    private int cantidadPasajeros;

    // Constructor vacío.
    public VehiculoPasajeros() {
        super();
    }

    // Constructor.
    public VehiculoPasajeros(String patente, String marca, int diasArriendo, int cantidadPasajeros) {
        this.patente = patente;
        this.marca = marca;
        this.diasArriendo = diasArriendo;
        this.cantidadPasajeros = cantidadPasajeros;
    }

    // Getter y Setter.
    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        if (cantidadPasajeros > 0) {
            this.cantidadPasajeros = cantidadPasajeros;
        } else {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser mayor a 0.");
        }
    }

    // Datos vehículos.
    @Override
    public void mostrarDatos() {
        System.out.println("\n--- Vehículo de Pasajeros ---");
        System.out.println("Patente: " + patente);
        System.out.println("Marca: " + marca);
        System.out.println("Días de arriendo: " + diasArriendo);
        System.out.println("Cantidad de pasajeros: " + cantidadPasajeros);
    }

    // Cálculo.
    @Override
    public void mostrarBoleta() {
        System.out.println("\n--- Boleta de Arriendo (Vehículo de Pasajeros) ---");
        double valorBase = diasArriendo * 40000; // Suponemos $40.000 por día de arriendo
        double iva = valorBase * IVA;

        double descuento = 0;
        // Supuesto: se aplica 12% de descuento si la cantidad de pasajeros es mayor a 4
        if (cantidadPasajeros > 4) {
            descuento = valorBase * DESCUENTO_PASAJEROS;
        }

        double total = valorBase + iva - descuento;

        System.out.printf("Valor base: $%.2f\n", valorBase);
        System.out.printf("IVA (19%%): $%.2f\n", iva);
        System.out.printf("Descuento: $%.2f\n", descuento);
        System.out.printf("Total a pagar: $%.2f\n", total);
    }
}
