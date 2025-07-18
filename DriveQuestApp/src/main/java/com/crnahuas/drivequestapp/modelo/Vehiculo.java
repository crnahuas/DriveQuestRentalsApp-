package com.crnahuas.drivequestapp.modelo;

// Clase abstracta que representa un Vehículo.
public abstract class Vehiculo {

    protected String patente; // Identificador único del vehículo
    protected String marca; // Marca del vehículo
    protected int diasArriendo; // Días por los que se arrienda el vehículo

    // Constructor vacío requerido por la pauta.
    public Vehiculo() {
    }

    // Constructor sobrecargado para inicializar los atributos comunes del vehículo.
    public Vehiculo(String patente, String marca, int diasArriendo) {
        this.patente = patente;
        this.marca = marca;
        this.diasArriendo = diasArriendo;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        // Validación básica del formato de patente chilena (ejemplo: CLPM23)
        if (patente != null && patente.matches("^[A-Z]{4}[0-9]{2}$")) {
            this.patente = patente;
        } else {
            throw new IllegalArgumentException("Patente inválida. Use el formato chileno: 4 letras y 2 números, ej. CLPM23.");
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

    // Método abstracto que debe implementar cada tipo de vehículo para mostrar sus datos.
    public abstract void mostrarDatos();

    // Método abstracto que debe implementar cada tipo de vehículo para exportarse como texto.
    public abstract String exportarComoTexto();
}
