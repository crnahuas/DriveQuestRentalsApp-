package com.crnahuas.drivequestapp.modelo;

// Clase que representa un vehículo de carga.
public class VehiculoCarga extends Vehiculo implements Calculable {

    // Atributo.
    private double cargaMaximaKg;

    // Constructor vacío.
    public VehiculoCarga() {
        super();
    }

    // Constructor.
    public VehiculoCarga(String patente, String marca, int diasArriendo, double cargaMaximaKg) {
        this.patente = patente;
        this.marca = marca;
        this.diasArriendo = diasArriendo;
        this.cargaMaximaKg = cargaMaximaKg;
    }

    // Getter y Setter.
    public double getCargaMaximaKg() {
        return cargaMaximaKg;
    }

    public void setCargaMaximaKg(double cargaMaximaKg) {
        if (cargaMaximaKg > 0) {
            this.cargaMaximaKg = cargaMaximaKg;
        } else {
            throw new IllegalArgumentException("La carga máxima debe ser mayor a 0.");
        }
    }

    // Datos vehículos.
    @Override
    public void mostrarDatos() {
        System.out.println("\n--- Vehículo de Carga ---");
        System.out.println("Patente: " + patente);
        System.out.println("Marca: " + marca);
        System.out.println("Días de arriendo: " + diasArriendo);
        System.out.println("Carga máxima: " + cargaMaximaKg + " kg");
    }

    // Cálculo.
    @Override
    public void mostrarBoleta() {
        System.out.println("\n--- Boleta de Arriendo (Vehículo de Carga) ---");
        double valorBase = diasArriendo * 50000; // Suponemos $50.000 por día de arriendo.
        double iva = valorBase * IVA;

        double descuento = 0;
        // Supuesto: se aplica 7% de descuento si la carga supera los 500 kg.
        if (cargaMaximaKg > 500) {
            descuento = valorBase * DESCUENTO_CARGA;
        }

        double total = valorBase + iva - descuento;

        System.out.printf("Valor base: $%.2f\n", valorBase);
        System.out.printf("IVA (19%%): $%.2f\n", iva);
        System.out.printf("Descuento: $%.2f\n", descuento);
        System.out.printf("Total a pagar: $%.2f\n", total);
    }
}
