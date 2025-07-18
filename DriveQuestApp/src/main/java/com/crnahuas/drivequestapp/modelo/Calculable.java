package com.crnahuas.drivequestapp.modelo;

// Clase que representa los calculos y muestra el detalle de la boleta de arriendo.
public interface Calculable {

    // Constantes.
    double IVA = 0.19;
    double DESCUENTO_CARGA = 0.07;
    double DESCUENTO_PASAJEROS = 0.12;

    // Mostrar el detalle de la boleta.
    void mostrarBoleta();
}
