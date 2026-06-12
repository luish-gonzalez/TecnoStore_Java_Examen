/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author camper
 */
public class EjecutarReporteGlobal {
    public static void main(String[] args) {
        IReporteService reporteService = ReporteService.getInstancia();

        reporteService.generarArchivoReporteGlobal();

        ReporteService.getInstancia().demostrarStreamAPI();
    }
    
}
