/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contaduriaapp;

/**
 *
 * @author ernestosandovalbecerra
 */


import conectividad.ClaseConectora;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class EdicionController implements Initializable{
    
    @FXML
    private TextArea txtConcepto;
    @FXML
    private Label lblCvo,lblAno;
    @FXML
    private TextField txtEnero,txtFebrero,txtMarzo,txtAbril,txtMayo,txtJunio,txtJulio,txtAgosto,txtSeptiembre,
            txtOctubre,txtNoviembre,txtDiciembre;
    @FXML
    private SplitMenuButton spltMenuCuenta,spltMenuSubCuenta,spltMenuSub2Cuenta;
    
    int id_editar;
    String cuentaDB = "",subCuentaBD="", subSubCuentaBD="";
    
    ClaseConectora cc = new ClaseConectora();
    Connection conn;
    
    public void rellenaCampos(String id,String cuenta, String subCuenta, String sub2Cuenta,String cvo,String concepto,String ano,String enero,String febrero,String marzo,String abril,
            String mayo, String junio, String julio, String agosto,String septiembre,String octubre,
            String noviembre, String diciembre) throws SQLException{
        
        if(sub2Cuenta.length()>20){
            sub2Cuenta = sub2Cuenta.substring(0,sub2Cuenta.length()/2);
        }
        if(cuenta.length()>20){
            cuenta = cuenta.substring(0,cuenta.length()/2);
        }
        if(subCuenta.length()>20){
            subCuenta = subCuenta.substring(0,subCuenta.length()/2);
        }
        id_editar = Integer.parseInt(id);
        spltMenuCuenta.setText(cuenta);
        spltMenuSubCuenta.setText(subCuenta);
        spltMenuSub2Cuenta.setText(sub2Cuenta);
        lblCvo.setText(cvo);
        lblAno.setText(ano);
        txtConcepto.setText(concepto);
        txtEnero.setText(enero);
        txtFebrero.setText(febrero);
        txtMarzo.setText(marzo);
        txtAbril.setText(abril);
        txtMayo.setText(mayo);
        txtJunio.setText(junio);
        txtJulio.setText(julio);
        txtAgosto.setText(agosto);
        txtSeptiembre.setText(septiembre);
        txtOctubre.setText(octubre);
        txtNoviembre.setText(noviembre);
        txtDiciembre.setText(diciembre);
        
        Statement st = conn.createStatement(); 
        String sql="SELECT DISTINCT cuenta FROM presupuestos.valores ORDER BY cuenta ASC;";
        ResultSet resultSet_cuentas = st.executeQuery(sql);
                
        while(resultSet_cuentas.next()){
            System.out.println("cuentas");
            MenuItem mi = new MenuItem(resultSet_cuentas.getString(1));
            mi.setId(resultSet_cuentas.getString(1));
            
            mi.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                  cuentaDB = mi.getId();
                  if(cuentaDB.length()>16){
                        cuentaDB = cuentaDB.substring(0,cuentaDB.length()/2);
                    }
                  spltMenuCuenta.setText(cuentaDB);
                }
            });
            spltMenuCuenta.getItems().add(mi);
        }
        
        String sql2="SELECT DISTINCT sub_cuenta FROM presupuestos.valores ORDER BY sub_cuenta ASC;";
        ResultSet resultSet_sub_cuentas = st.executeQuery(sql2);
          while(resultSet_sub_cuentas.next()){
            System.out.println("sub cuentas");
            MenuItem mi = new MenuItem(resultSet_sub_cuentas.getString(1));
            mi.setId(resultSet_sub_cuentas.getString(1));
            
            mi.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                  subCuentaBD = mi.getId();
                  if(subCuentaBD.length()>16){
                        subCuentaBD = subCuentaBD.substring(0,subCuentaBD.length()/2);
                    }
                  spltMenuSubCuenta.setText(subCuentaBD);
                }
            });
            spltMenuSubCuenta.getItems().add(mi);
        }
          
        String sql3="SELECT DISTINCT sub2_cuenta FROM presupuestos.valores ORDER BY sub2_cuenta ASC;";
        ResultSet resultSet_sub2_cuentas = st.executeQuery(sql3);
        

        while(resultSet_sub2_cuentas.next()){
            System.out.println("sub sub cuentas");
            MenuItem mi = new MenuItem(resultSet_sub2_cuentas.getString(1));
            mi.setId(resultSet_sub2_cuentas.getString(1));
            
            mi.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                  subSubCuentaBD = mi.getId();
                  if(subSubCuentaBD.length()>20){
                        subSubCuentaBD = subSubCuentaBD.substring(0,subSubCuentaBD.length()/2);
                    }
                  spltMenuSub2Cuenta.setText(subSubCuentaBD);
                }
            });
            spltMenuSub2Cuenta.getItems().add(mi);
        }
    }
    
    @FXML
    private void actualizaInfo(ActionEvent event) throws SQLException{
        String datoConcepto = txtConcepto.getText();
        String sql="";
        try{
           BigInteger datoEnero = new BigInteger(txtEnero.getText().replace(" ", ""));
           BigInteger datoFebrero = new BigInteger(txtFebrero.getText().replace(" ", ""));
           BigInteger datoMarzo = new BigInteger(txtMarzo.getText().replace(" ", ""));
           BigInteger datoAbril = new BigInteger(txtAbril.getText().replace(" ", ""));
           BigInteger datoMayo = new BigInteger(txtMayo.getText().replace(" ", ""));
           BigInteger datoJunio = new BigInteger(txtJunio.getText().replace(" ", ""));
           BigInteger datoJulio = new BigInteger(txtJulio.getText().replace(" ", ""));
           BigInteger datoAgosto = new BigInteger(txtAgosto.getText().replace(" ", ""));
           BigInteger datoSeptiembre =new BigInteger(txtSeptiembre.getText().replace(" ", ""));
           BigInteger datoOctubre = new BigInteger(txtOctubre.getText().replace(" ", ""));
           BigInteger datoNoviembre = new BigInteger(txtNoviembre.getText().replace(" ", ""));
           BigInteger datoDiciembre = new BigInteger(txtDiciembre.getText().replace(" ", ""));
           //spltMenuCuenta,spltMenuSubCuenta,spltMenuSub2Cuenta;
           cuentaDB = spltMenuCuenta.getText();
           subCuentaBD = spltMenuSubCuenta.getText();
           subSubCuentaBD = spltMenuSub2Cuenta.getText();
            sql="UPDATE valores SET cuenta= \""+cuentaDB+"\", sub_cuenta=\""+subCuentaBD+"\", sub2_cuenta=\""+subSubCuentaBD+"\", concepto=\""+datoConcepto+"\", enero="+datoEnero+", febrero="+datoFebrero+", marzo="+datoMarzo
                    +", abril="+datoAbril+", mayo="+datoMayo+", junio="+datoJunio+", julio="+datoJulio+", agosto="+datoAgosto
                    +", septiembre="+datoSeptiembre+", octubre="+datoOctubre+", noviembre="+datoNoviembre+", diciembre="+datoDiciembre+
                    " WHERE id="+id_editar+";";
            System.out.println(sql);
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("CONFIRMACIÓN");
            alert.setContentText("Los datos se actualizaron correctamente");
            alert.showAndWait();
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Alguno de los campos está vacío o contiene información erronea. Verifique su información");
            alert.showAndWait();
            e.printStackTrace();
        }
        

    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conn = cc.getConnection(ClaseConectora.ip_base_de_datos);
        } catch (IOException ex) {
        }
    }
    
}
