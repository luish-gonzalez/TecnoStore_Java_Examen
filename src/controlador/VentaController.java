
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import servicio.GestorVentas;
import servicio.GestorClientes;
import servicio.GestorCelulares;
import modelo.Cliente;
import modelo.Celular;
import modelo.ItemVenta;
import modelo.Venta;
import dao.VentaDAO;
import dao.CelularDAO;

/**
 *
 * @author camper
 */
public class VentaController {
    private final GestorVentas gestorVentas;
    private final GestorClientes gestorClientes;
    private final GestorCelulares gestorCelulares;
    private final VentaDAO ventaDAO;
    private final CelularDAO celularDAO;
    
    public VentaController(GestorVentas gestorVentas,
                       GestorClientes gestorClientes,
                       GestorCelulares gestorCelulares) {
        this.gestorVentas = gestorVentas;
        this.gestorClientes = gestorClientes;
        this.gestorCelulares = gestorCelulares;
        this.ventaDAO = new VentaDAO();
        this.celularDAO = new CelularDAO();
    }
    
    public void listarClientes() {
        gestorClientes.listarClientes();
    }
    
    public void listarCelulares() {
        gestorCelulares.listarCelulares();
    }
    
    public void listarVentas() {
        gestorVentas.listarVentas();
    }
    
    public void registrarVenta(int idVenta, String identificacionCliente,
                           int idCelular, int cantidad) {

        Cliente cliente = gestorClientes.buscarPorIdentificacion(identificacionCliente);
        Celular celular = gestorCelulares.buscarPorId(idCelular);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        if (celular == null) {
            throw new IllegalArgumentException("Celular no encontrado");
        }

        Venta venta = new Venta(idVenta, cliente);
        ItemVenta item = new ItemVenta(celular, cantidad);

        venta.agregarItem(item);
        gestorVentas.registrarVenta(venta);
        
        ventaDAO.insertarVenta(venta);
        venta.getItems().forEach(itemVenta ->
                celularDAO.actualizarStock(
                        itemVenta.getCelular().getId(),
                        itemVenta.getCelular().getStock()
                )
        );       
        
    }
}
