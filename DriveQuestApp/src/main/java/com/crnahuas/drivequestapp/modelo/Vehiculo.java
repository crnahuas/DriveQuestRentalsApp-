package com.crnahuas.drivequestapp.modelo;

// Clase abstracta que representa un Vehículo.
public abstract class Vehiculo {

    protected String patente; // Patente del vehículo.
    protected String marca; // Marca del vehículo.
    protected int diasArriendo; // Días de arriendo del vehículo.

    // Constructor vacío.
    public Vehiculo() {
    }

    // Constructor.
    public Vehiculo(String patente, String marca, int diasArriendo) {
        this.patente = patente;
        this.marca = marca;
        this.diasArriendo = diasArriendo;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        // Validación básica patente (ejemplo: CLPM23).
        if (patente != null && patente.matches("^[A-Z]{4}[0-9]{2}$")) {
            this.patente = patente;
        } else {
            throw new IllegalArgumentException("Patente inválida. Ejemplo de formato: CLPM23.");
        }
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
        if (diasArriendo > 0) {
            this.diasArriendo = diasArriendo;
        } else {
            throw new IllegalArgumentException("La cantidad de días de arriendo debe ser mayor a 0.");
        }
    }

    // Método abstracto para mostrar datos del vehículo.
    public abstract void mostrarDatos();

    // Método abstracto de vehículos para exportarse como txt.
    public abstract String exportarComoTexto();
}
