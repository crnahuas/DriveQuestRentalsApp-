package com.crnahuas.drivequestapp.modelo;

// Clase que representa un vehículo de pasajeros.
public class VehiculoPasajeros extends Vehiculo implements Calculable {

    private int cantidadPasajeros; // Cantidad de personas que puede transportar.
    
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
        if (cantidadPasajeros > 0 && cantidadPasajeros <= 45) {
            this.cantidadPasajeros = cantidadPasajeros;
        } else {
            throw new IllegalArgumentException("Debe ser entre 1 y 45 pasajeros.");
        }
    }

    // Muestra la información detallada del vehículo.
    @Override
    public void mostrarDatos() {
        System.out.println("[Pasajeros] Patente: " + patente + ", Marca: " + marca);
    }

    // Muestra boleta con IVA y descuento si corresponde.
    @Override
    public void mostrarBoleta() {
        double subtotal = diasArriendo * 40000;
        double iva = subtotal * IVA;
        double descuento = (cantidadPasajeros > 4) ? subtotal * DESCUENTO_PASAJEROS : 0;
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

    // Exporta el contenido en .txt
    @Override
    public String exportarComoTexto() {
        return String.format("pasajeros,%s,%s,%d,%d", patente, marca, diasArriendo, cantidadPasajeros);
    }
}
