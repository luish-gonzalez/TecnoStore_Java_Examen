/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import util.ScannerManager;
import java.util.Scanner;
import controlador.ReporteController;

/**
 *
 * @author PC_WIN11
 */
public class MenuReportes {
    private final Scanner scanner;
    private final ReporteController reporteController;
    
    
    public MenuReportes(ReporteController reporteController) {
        this.scanner = ScannerManager.getInstancia().getScanner();
        this.reporteController = reporteController;
    }
    
    public void mostrar() {
        int opcion;

        do {
            System.out.println("=== MENÚ REPORTES ===");
            System.out.println("1. Celulares con stock bajo");
            System.out.println("2. Top 3 celulares más vendidos");
            System.out.println("3. Ventas totales por mes");
            System.out.println("4. Generar reporte_ventas.txt");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion){
                case 1:
                    reporteController.mostrarStockBajo();
                    break;
                case 2:
                    reporteController.mostrarTop3CelularesMasVendidos();
                    break;
                case 3:
                    reporteController.mostrarVentasTotalesPorMes();
                    break;
                case 4:
                    reporteController.generarReporteVentasArchivo();
                    System.out.println("Archivo reporte_ventas.txt generado correctamente.");
                    break;
            }

        } while (opcion != 0);
    }
}
