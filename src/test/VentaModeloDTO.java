/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author camper
 */
public class VentaModeloDTO {
    private String modelo;
    private int cantidad;

    public VentaModeloDTO(String modelo, int cantidad) {
        this.modelo = modelo;
        this.cantidad = cantidad;
    }

    public String getModelo() {
        return modelo;
    }

    public int getCantidad() {
        return cantidad;
    }
}
