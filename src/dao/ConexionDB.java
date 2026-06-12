/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author PC_WIN11
 */
public class ConexionDB {
    private static final String URL = "jdbc:mysql://mysql-3e32b359-aprendiendomysql.b.aivencloud.com:26338/tecnostore_db";      // "jdbc:mysql://localhost:3306/tecnostore_db";
    private static final String USER = "avnadmin";                                                                          // "root";
    private static final String PASSWORD = "AVNS_Sh45IMlC9RVPG6YKQDQ";                                                      // "";
    
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
