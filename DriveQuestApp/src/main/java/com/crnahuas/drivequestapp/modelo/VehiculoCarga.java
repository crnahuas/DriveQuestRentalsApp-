package com.crnahuas.drivequestapp.modelo;

// Clase que representa un vehículo de carga.
public class VehiculoCarga extends Vehiculo implements Calculable {

    private double cargaMaximaKg; // Capacidad máxima de carga en kilogramos.

    // Constructor vacío..
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

    public double getCargaMaximaKg() {
        return cargaMaximaKg;
    }

    public void setCargaMaximaKg(double cargaMaximaKg) {
        if (cargaMaximaKg > 0 && cargaMaximaKg <= 30000) { // Suponiendo Por ejemplo, límite razonable para camión.
            this.cargaMaximaKg = cargaMaximaKg;
        } else {
            throw new IllegalArgumentException("La carga máxima debe estar entre 1 y 30000 kg.");
        }
    }

    // Muestra los datos del vehículo de carga.
    @Override
    public void mostrarDatos() {
        System.out.println("[Carga] Patente: " + patente + ", Marca: " + marca + ", Días: " + diasArriendo + " días, Carga: " + cargaMaximaKg + " kg");
    }

    // Calcula y muestra el detalle de la boleta del vehículo de carga.
    @Override
    public void mostrarBoleta() {
        double subtotal = diasArriendo * 50000; // Precio fijo diario para vehículos de carga.
        double iva = subtotal * IVA;
        double descuento = 0;

        // Supuesto: Aplica descuento si la carga máxima es mayor a 500 kg.
        if (cargaMaximaKg > 500) {
            descuento = subtotal * DESCUENTO_CARGA;
        }

        double total = subtotal + iva - descuento;

        System.out.println("\n=== Boleta Vehículo de Carga ===");
        System.out.println("Patente: " + patente);
        System.out.println("Marca: " + marca);
        System.out.println("Días de arriendo: " + diasArriendo);
        System.out.println("Subtotal: $" + String.format("%,.0f", subtotal));
        System.out.println("IVA (19%): $" + String.format("%,.0f", iva));
        System.out.println("Descuento (" + (DESCUENTO_CARGA * 100) + "%): $" + String.format("%,.0f", descuento));
        System.out.println("TOTAL: $" + String.format("%,.0f", total));
    }

    // Exporta los datos del vehículo de carga.
    @Override
    public String exportarComoTexto() {
        return String.format("carga,%s,%s,%d,%.2f", patente, marca, diasArriendo, cargaMaximaKg);
    }
}
