/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.ConexionDB;
import java.sql.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author camper
 */
public class ReporteService implements IReporteService {
    private static ReporteService instancia;
    
    private ReporteService() {
    }
    
    public static ReporteService getInstancia() {
        if (instancia == null) {
            instancia = new ReporteService();
        }
        return instancia;
    }
    
    public double obtenerTotalVentas() {
        String sql = "SELECT SUM(total) AS total_ventas FROM ventas";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total_ventas");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener total de ventas: " + e.getMessage());
        }

        return 0;
    }
    
    public String obtenerCelularesVendidosPorModelo() {
        StringBuilder resultado = new StringBuilder();

        String sql = "SELECT c.marca, c.modelo, SUM(dv.cantidad) AS total_vendido " +
                "FROM detalle_ventas dv " +
                "INNER JOIN celulares c ON dv.id_celular = c.id " +
                "GROUP BY c.marca, c.modelo";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.append("- ")
                        .append(rs.getString("marca"))
                        .append(" ")
                        .append(rs.getString("modelo"))
                        .append(": ")
                        .append(rs.getInt("total_vendido"))
                        .append(" unidades\n");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener celulares vendidos: " + e.getMessage());
        }

        if (resultado.length() == 0) {
            resultado.append("No existen celulares vendidos.\n");
        }

        return resultado.toString();
    }
    
    public String obtenerClientesConCreditos() {
        StringBuilder resultado = new StringBuilder();

        String sql = "SELECT c.nombre, cr.saldo " +
                "FROM creditos cr " +
                "INNER JOIN clientes c ON cr.id_cliente = c.id " +
                "WHERE cr.saldo > 0";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.append("- ")
                        .append(rs.getString("nombre"))
                        .append(" → $")
                        .append(rs.getDouble("saldo"))
                        .append("\n");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener créditos: " + e.getMessage());
        }

        if (resultado.length() == 0) {
            resultado.append("No existen créditos pendientes.\n");
        }

        return resultado.toString();
    }
    
    public String obtenerStockActual(int stockMinimo) {
        StringBuilder resultado = new StringBuilder();

        String sql = "SELECT marca, modelo, stock FROM celulares";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int stock = rs.getInt("stock");

                resultado.append("- ")
                        .append(rs.getString("marca"))
                        .append(" ")
                        .append(rs.getString("modelo"))
                        .append(": ")
                        .append(stock)
                        .append(" unidades");

                if (stock < stockMinimo) {
                    resultado.append(" (ALERTA: bajo stock)");
                }

                resultado.append("\n");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener stock: " + e.getMessage());
        }

        if (resultado.length() == 0) {
            resultado.append("No existen productos registrados.\n");
        }

        return resultado.toString();
    }
    
    @Override
    public String generarTextoReporteGlobal() {
        StringBuilder reporte = new StringBuilder();

        reporte.append("--- REPORTE GLOBAL TECNOSTORE ---\n");

        reporte.append("Total de ventas: $")
                .append(obtenerTotalVentas())
                .append("\n\n");

        reporte.append("Celulares vendidos por modelo:\n");
        reporte.append(obtenerCelularesVendidosPorModelo());
        reporte.append("\n");

        reporte.append("Clientes con créditos pendientes:\n");
        reporte.append(obtenerClientesConCreditos());
        reporte.append("\n");

        reporte.append("Stock actual:\n");
        reporte.append(obtenerStockActual(5));

        return reporte.toString();
    }
    
    @Override
    public void generarArchivoReporteGlobal() {
        String reporte = generarTextoReporteGlobal();

        try (PrintWriter writer = new PrintWriter(new FileWriter("reporte_global.txt"))) {
            writer.print(reporte);
            System.out.println(reporte);
            System.out.println("Reporte guardado en: reporte_global.txt");

        } catch (IOException e) {
            System.out.println("Error al escribir reporte_global.txt: " + e.getMessage());
        }
    }
    
    public void demostrarStreamAPI() {
        List<VentaModeloDTO> ventas = new ArrayList<>();

        String sql = """
                SELECT c.modelo,
                       dv.cantidad
                FROM detalle_ventas dv
                INNER JOIN celulares c
                        ON dv.id_celular = c.id
                """;

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ventas.add(
                        new VentaModeloDTO(
                                rs.getString("modelo"),
                                rs.getInt("cantidad")
                        )
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Map<String, Integer> agrupados =
                ventas.stream()
                        .collect(Collectors.groupingBy(
                                VentaModeloDTO::getModelo,
                                Collectors.summingInt(
                                        VentaModeloDTO::getCantidad
                                )
                        ));

        System.out.println("\n=== DEMOSTRACIÓN STREAM API ===");

        agrupados.forEach(
                (modelo, cantidad) ->
                        System.out.println(modelo + ": " + cantidad)
        );
    } 
    
    
    
    
}
