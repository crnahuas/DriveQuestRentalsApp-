package com.crnahuas.drivequestapp.modelo;

// Clase que representa un vehículo de pasajeros.
public class VehiculoPasajeros extends Vehiculo implements Calculable {

    private int cantidadPasajeros; // Número máximo de pasajeros permitidos.

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

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        if (cantidadPasajeros > 0 && cantidadPasajeros <= 45) { // Suponiendo máximo 45, límite razonable para bus.
            this.cantidadPasajeros = cantidadPasajeros;
        } else {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser entre 1 y 45.");
        }
    }

    // Muestra los datos del vehículo de pasajeros.
    @Override
    public void mostrarDatos() {
        System.out.println("[Pasajeros] Patente: " + patente + ", Marca: " + marca + ", Días: " + diasArriendo + " días, Pasajeros: " + cantidadPasajeros);
    }

    // Calcula y muestra el detalle de la boleta del vehículo de pasajeros.
    @Override
    public void mostrarBoleta() {
        double subtotal = diasArriendo * 40000; // Precio fijo diario para pasajeros.
        double iva = subtotal * IVA;
        double descuento = 0;

        // Supuesto: Aplica descuento si hay más de 4 pasajeros.
        if (cantidadPasajeros > 4) {
            descuento = subtotal * DESCUENTO_PASAJEROS;
        }

        double total = subtotal + iva - descuento;

        System.out.println("\n=== Boleta Vehículo de Pasajeros ===");
        System.out.println("Patente: " + patente);
        System.out.println("Marca: " + marca);
        System.out.println("Días de arriendo: " + diasArriendo);
        System.out.println("Subtotal: $" + String.format("%,.0f", subtotal));
        System.out.println("IVA (19%): $" + String.format("%,.0f", iva));
        System.out.println("Descuento (" + (DESCUENTO_PASAJEROS * 100) + "%): $" + String.format("%,.0f", descuento));
        System.out.println("TOTAL: $" + String.format("%,.0f", total));
    }

    // Exporta los datos del vehículo de pasajeros.
    @Override
    public String exportarComoTexto() {
        return String.format("pasajeros,%s,%s,%d,%d", patente, marca, diasArriendo, cantidadPasajeros);
    }
}
