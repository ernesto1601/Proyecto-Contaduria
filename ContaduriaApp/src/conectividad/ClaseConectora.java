/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conectividad;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import javafx.scene.control.Alert;

/**
 *
 * @author ernestosandovalbecerra
 */

public class ClaseConectora {
    
    public static String ip_base_de_datos="";
    public Connection connection;
    
    public Connection getConnection(String ip) throws IOException{
        
        ip_base_de_datos=ip;
        String dbName="presupuestos";
        String userName="root";
        String password="root";
                       
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("jdbc:mysql://"+ip+"/"+dbName+"?autoReconnect=true&useSSL=false");
            //ip_base_de_datos= "jdbc:mysql://"+ip+":3306"+"/"+dbName+"?autoReconnect=true&useSSL=false";
            connection = DriverManager.getConnection("jdbc:mysql://"+ip+":3306"+"/"+dbName+"?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false",userName,password);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("La base de datos a la que trata de acceder es irreconocible, por favor introduzca la IP Correcta");
            alert.showAndWait();
            return null;
        }
            return connection;
   
    }
    
    
}
