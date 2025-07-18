package com.crnahuas.drivequestapp.modelo;

// Clase abstracta que representa un Vehículo.
public abstract class Vehiculo {

    // Atributos comunes
    protected String patente;       // Identificador único del vehículo.
    protected String marca;         // Marca del vehículo.
    protected int diasArriendo;     // Días que el vehículo será arrendado.

    // Constructor vacío.
    public Vehiculo() {
    }

    // Constructor.
    public Vehiculo(String patente, String marca, int diasArriendo) {
        this.patente = patente;
        this.marca = marca;
        this.diasArriendo = diasArriendo;
    }

    // Getters y Setters.
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        // Validación formato patente chilena: CLPM23.
        if (patente != null && patente.toUpperCase().matches("^[B-DF-HJ-NP-TV-Z]{2}[B-DF-HJ-NP-TV-Z]{2}\\d{2}$")) {
            this.patente = patente.toUpperCase();
        } else {
            throw new IllegalArgumentException("Formato de patente inválido. Ejemplo válido: CLPM23");
        }
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca != null && !marca.trim().isEmpty()) {
            this.marca = marca.trim();
        } else {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }
    }

    public int getDiasArriendo() {
        return diasArriendo;
    }

    public void setDiasArriendo(int diasArriendo) {
        if (diasArriendo > 0) {
            this.diasArriendo = diasArriendo;
        } else {
            throw new IllegalArgumentException("Los días de arriendo deben ser mayores a cero.");
        }
    }

    // Mostrar los datos del vehículo.
    public abstract void mostrarDatos();
}
