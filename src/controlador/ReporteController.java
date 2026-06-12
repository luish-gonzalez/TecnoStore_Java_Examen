/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import servicio.GestorCelulares;
import servicio.GestorVentas;
import util.ReporteUtils;
import util.ArchivoUtils;

/**
 *
 * @author PC_WIN11
 */
public class ReporteController {
    private final GestorCelulares gestorCelulares;
    private final GestorVentas gestorVentas;
    
    public ReporteController(GestorCelulares gestorCelulares, GestorVentas gestorVentas) {
        this.gestorCelulares = gestorCelulares;
        this.gestorVentas = gestorVentas;
    }
    
    public void mostrarStockBajo() {
        ReporteUtils.mostrarStockBajo(gestorCelulares.getCelulares());
    }
    
    public void mostrarTop3CelularesMasVendidos() {
        ReporteUtils.mostrarTop3CelularesMasVendidos(gestorVentas.getVentas());
    }
    
    public void mostrarVentasTotalesPorMes() {
        ReporteUtils.mostrarVentasTotalesPorMes(gestorVentas.getVentas());
    }
    
    public void generarReporteVentasArchivo() {
        ArchivoUtils.generarReporteVentas(gestorVentas.getVentas());
    }
}
