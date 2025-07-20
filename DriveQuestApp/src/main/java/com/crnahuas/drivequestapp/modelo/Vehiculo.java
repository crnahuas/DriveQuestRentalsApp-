package com.crnahuas.drivequestapp.modelo;

// Clase abstracta que representa un Vehículo.
// Clase base abstracta para todos los vehículos.
public abstract class Vehiculo {

    protected String patente;          // Patente del vehículo.
    protected String marca;            // Marca del vehículo.
    protected int diasArriendo;        // Cantidad de días que se arrienda el vehículo.

    // Getters y Setters
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getDiasArriendo() {
        return diasArriendo;
    }

    public void setDiasArriendo(int diasArriendo) {
        this.diasArriendo = diasArriendo;
    }

    // Mostrar la boleta de arriendo.
    public abstract void mostrarBoleta();

    // Mostrar datos básicos.
    public abstract void mostrarDatos();

    // Exportar los datos del vehículo en .txt
    public abstract String exportarComoTexto();
}
