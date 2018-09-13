/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contaduriaapp;

import Comparativa.Comparaciones;
import conectividad.ClaseConectora;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jxl.*;
import jxl.write.*;

/**
 *
 * @author ernestosandovalbecerra
 */
public class FXMLDocumentController implements Initializable{

    /*OBJETOS PARA MYSQL*/
    //ClaseConectora cc = new ClaseConectora();
    //Connection conn = cc.getConnection("localhost");
    ClaseConectora cc;
    Connection conn;

    String [][] libroExcel = null;
    int index_libro_col=0;
    int index_libro_fil=-1;//saber en que fila me encuentro actualmente
    int datos = 0;//Saber cuantas filas hay de resultado
    String lastQuery="";
    String ordenar = "SELECT * FROM presupuestos.valores WHERE concepto is not null ORDER BY id ;";
    static String idN,cvoN,cuentaN, subCuentaN,sub2CuentaN,conceptoN,anoN,eneroN,febreroN,marzoN,abrilN,mayoN,junioN
            ,julioN,agostoN,septiembreN,octubreN,noviembreN,diciembreN;
    
    public String cuentaBD="", subCuentaBD="", subSubCuentaBD = "";
    

    @FXML
    private Pane consultasPane,altaPane,consultaEspPane,conexionPane,altaCuentasPane;
    @FXML
    private TextField txtIP;
    @FXML
    private TableView<Std> tabledatos;
    @FXML
    private TableColumn<Std, String> col_clave;
    @FXML
    private TableColumn<Std, String> col_enero;
    @FXML
    private TableColumn<Std, String> col_febrero;
    @FXML
    private TableColumn<Std, String> col_marzo;
    @FXML
    private TableColumn<Std, String> col_concepto;
    @FXML
    private TableColumn<Std, String> col_ano;
    @FXML
    private TableColumn<Std, String> col_abril;
    @FXML
    private TableColumn<Std, String> col_mayo;
    @FXML
    private TableColumn<Std, String> col_junio;
    @FXML
    private TableColumn<Std, String> col_julio;
    @FXML
    private TableColumn<Std, String> col_agosto;
    @FXML
    private TableColumn<Std, String> col_septiembre;
    @FXML
    private TableColumn<Std, String> col_octubre;
    @FXML
    private TableColumn<Std, String> col_noviembre;
    @FXML
    private TableColumn<Std, String> col_diciembre;
    @FXML
    private TextField dataCvo,dataAno,dataEnero,dataFebrero,dataMarzo,dataAbril,dataMayo
            ,dataJunio,dataJulio,dataAgosto,dataSeptiembre,dataOctubre,dataNoviembre,dataDiciembre;
    @FXML
    private TextArea dataConcepto;
    @FXML
    private TextField txtFieldBuscarAno;
    @FXML
    private TextField txtFieldBuscarCVO,txtFieldBuscarMonto,txtFieldBuscarCon;
    @FXML
    private Button btnActualizaCampo,crtExcel,btnEliminar,btnAnadirCuenta;
    @FXML
    private Label labelBusqueda;
    @FXML
    private Menu menuCalculos, menuComparativa = null;
    @FXML
    private SplitMenuButton spltSubCuenta,spltCuenta,spltSubSubCuenta,spltCuentasPane,spltSubCuentasPane,spltSub2CuentasPane;
    @FXML
    private TextField txtFldCuenta,txtFldSubCuenta,txtFldSub2Cuenta;
    @FXML
    private CheckBox checkAnadir;
    @FXML
    private TextField cvoCuenta,anoCuenta,cuentaValEnero,cuentaValFebrero,cuentaValMarzo,cuentaValAbril,cuentaValMayo,cuentaValJunio,cuentaValJulio,
            cuentaValAgosto,cuentaValSeptiembre,cuentaValOctubre,cuentaValNoviembre,cuentaValDiciembre;
   
   
    @FXML
   private void mostrarTabla(ActionEvent event) throws SQLException{
       String sentencia=ordenar;
       lastQuery=sentencia;
       limpiarCampos();
       buscarDatos(sentencia);
   }
   
   @FXML
   private void vistaConexion(ActionEvent event){
       System.out.println("dar de alta");
        altaPane.setVisible(false);
        consultasPane.setVisible(false);
        consultaEspPane.setVisible(false);
        altaCuentasPane.setVisible(false);
        conexionPane.setVisible(true);
   }
   
   @FXML
   private void vistaAltaCuentas(ActionEvent event){
       System.out.println("dar de alta cuenta");
        altaCuentasPane.setVisible(true);
        altaPane.setVisible(false);
        consultasPane.setVisible(false);
        consultaEspPane.setVisible(false);
        conexionPane.setVisible(false);
   }

    @FXML
    private void vistaAlta(ActionEvent event) throws SQLException{
        System.out.println("dar de alta");
        altaPane.setVisible(true);
        consultasPane.setVisible(false);
        consultaEspPane.setVisible(false);
        conexionPane.setVisible(false);
        altaCuentasPane.setVisible(false);
        String sentencia= ordenar;
        buscarDatos(sentencia);
        
    }
    
    @FXML
    private void vistaBaja(ActionEvent event) throws SQLException{
        System.out.println("dar de baja");
        altaCuentasPane.setVisible(false);
        altaPane.setVisible(false);
        consultasPane.setVisible(true);
        consultaEspPane.setVisible(true);
        crtExcel.setVisible(false);
        btnEliminar.setVisible(true);
        btnActualizaCampo.setVisible(false);
        conexionPane.setVisible(false);
        btnActualizaCampo.setDisable(true);
        btnEliminar.setDisable(true);
        labelBusqueda.setText("Eliminar");
        String sentencia=ordenar;
        buscarDatos(sentencia);
    }
    
     @FXML
    private void vistaEditar(ActionEvent event) throws SQLException{
        System.out.println("editar conceptos");
        altaCuentasPane.setVisible(false);
        altaPane.setVisible(false);
        consultasPane.setVisible(true);
        consultaEspPane.setVisible(true);
        crtExcel.setVisible(false);
        btnEliminar.setVisible(false);
        btnActualizaCampo.setVisible(true);
        conexionPane.setVisible(false);
        btnActualizaCampo.setDisable(true);
        btnEliminar.setDisable(true);
        labelBusqueda.setText("Editar");
        String sentencia=ordenar;
        buscarDatos(sentencia);
    }
    
    @FXML
    private void vistaEspConsulta(ActionEvent event) throws SQLException{
        altaPane.setVisible(false);
        altaCuentasPane.setVisible(false);
        consultasPane.setVisible(true);
        consultaEspPane.setVisible(true);
        crtExcel.setVisible(true);
        btnEliminar.setVisible(false);
        btnActualizaCampo.setVisible(false);
        conexionPane.setVisible(false);
        labelBusqueda.setText("");
        limpiarCampos();
        String sentencia=ordenar;
        buscarDatos(sentencia);
    }
    
    @FXML
    private void EliminarDatos(ActionEvent event) throws SQLException{
        
        Statement st = conn.createStatement();
        String sql="DELETE FROM valores WHERE id="+idN+";";
        st.executeUpdate(sql);
        if(lastQuery.equals(""))
            buscarDatos(ordenar);
        else
            buscarDatos(lastQuery);

        btnEliminar.setDisable(true);
        
    }
    
    public void buscarDatos(String sentencia) throws SQLException{
        
        libroExcel = null;
        List<Std> listaNueva = new ArrayList();//Lista de objetos para la tabla
        
        Statement st = conn.createStatement();
        
        String sql=sentencia;
        ResultSet resultSet = st.executeQuery(sql);
        
        datos=0;
        index_libro_fil=-1;
        while(resultSet.next()){
            datos++;
        }
        System.out.println("Datos: "+datos);
        
        if(datos>0){
          libroExcel = new String [datos][15];  
        }
        
        resultSet = st.executeQuery(sql);
        while(resultSet.next()){
            String id = resultSet.getString(1);
            String clave = resultSet.getString(2)+" ";
            String cuenta = resultSet.getString(3)+" ";
            String sub_cuenta = resultSet.getString(4)+" ";
            String sub2_cuenta = resultSet.getString(5)+" ";
            String concepto = resultSet.getString(6)+" ";
            String ano = resultSet.getString(7)+" ";
            String enero = resultSet.getString(8)+" ";
            String febrero = resultSet.getString(9);
            String marzo = resultSet.getString(10);
            String abril = resultSet.getString(11);
            String mayo = resultSet.getString(12);
            String junio = resultSet.getString(13);
            String julio = resultSet.getString(14);
            String agosto = resultSet.getString(15);
            String septiembre = resultSet.getString(16);
            String octubre = resultSet.getString(17);
            String noviembre = resultSet.getString(18);
            String diciembre = resultSet.getString(19);

            index_libro_fil++;
            listaNueva.add(new Std(id,cuenta, sub_cuenta, sub2_cuenta,clave,concepto,ano,enero,febrero,marzo,abril,mayo,junio,julio,
                    agosto,septiembre,octubre,noviembre,diciembre));
            
            if(datos>0){
                guardaLibroExcel(index_libro_fil, clave, concepto, ano, enero, febrero, marzo, abril, mayo,
                    junio, julio, agosto, septiembre, octubre, noviembre, diciembre);
            }
            
        }
        System.out.println("index de fila totales: "+index_libro_fil);
        ObservableList list = FXCollections.observableList(listaNueva);
        
        tabledatos.setItems(list);
        tabledatos.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                cvoN = tabledatos.getSelectionModel().getSelectedItem().getClave();
                cuentaN = tabledatos.getSelectionModel().getSelectedItem().getCuenta();
                subCuentaN = tabledatos.getSelectionModel().getSelectedItem().getSubCuenta();
                sub2CuentaN = tabledatos.getSelectionModel().getSelectedItem().getSub2Cuenta();
                conceptoN = tabledatos.getSelectionModel().getSelectedItem().getConcepto();
                anoN = tabledatos.getSelectionModel().getSelectedItem().getAno();
                eneroN = tabledatos.getSelectionModel().getSelectedItem().getEnero();
                febreroN = tabledatos.getSelectionModel().getSelectedItem().getFebrero();
                marzoN = tabledatos.getSelectionModel().getSelectedItem().getMarzo();
                abrilN = tabledatos.getSelectionModel().getSelectedItem().getAbril();
                mayoN = tabledatos.getSelectionModel().getSelectedItem().getMayo();
                junioN = tabledatos.getSelectionModel().getSelectedItem().getJunio();
                julioN = tabledatos.getSelectionModel().getSelectedItem().getJulio();
                agostoN = tabledatos.getSelectionModel().getSelectedItem().getAgosto();
                septiembreN = tabledatos.getSelectionModel().getSelectedItem().getSeptiembre();
                octubreN = tabledatos.getSelectionModel().getSelectedItem().getOctubre();
                noviembreN = tabledatos.getSelectionModel().getSelectedItem().getNoviembre();
                diciembreN = tabledatos.getSelectionModel().getSelectedItem().getDiciembre();
                
                idN = tabledatos.getSelectionModel().getSelectedItem().getId();
                btnActualizaCampo.setDisable(false);
                btnEliminar.setDisable(false);
            }
        });
        
        tabledatos.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                cvoN = tabledatos.getSelectionModel().getSelectedItem().getClave();
                cuentaN = tabledatos.getSelectionModel().getSelectedItem().getCuenta();
                subCuentaN = tabledatos.getSelectionModel().getSelectedItem().getSubCuenta();
                sub2CuentaN = tabledatos.getSelectionModel().getSelectedItem().getSub2Cuenta();
                conceptoN = tabledatos.getSelectionModel().getSelectedItem().getConcepto();
                anoN = tabledatos.getSelectionModel().getSelectedItem().getAno();
                eneroN = tabledatos.getSelectionModel().getSelectedItem().getEnero();
                febreroN = tabledatos.getSelectionModel().getSelectedItem().getFebrero();
                marzoN = tabledatos.getSelectionModel().getSelectedItem().getMarzo();
                abrilN = tabledatos.getSelectionModel().getSelectedItem().getAbril();
                mayoN = tabledatos.getSelectionModel().getSelectedItem().getMayo();
                junioN = tabledatos.getSelectionModel().getSelectedItem().getJunio();
                julioN = tabledatos.getSelectionModel().getSelectedItem().getJulio();
                agostoN = tabledatos.getSelectionModel().getSelectedItem().getAgosto();
                septiembreN = tabledatos.getSelectionModel().getSelectedItem().getSeptiembre();
                octubreN = tabledatos.getSelectionModel().getSelectedItem().getOctubre();
                noviembreN = tabledatos.getSelectionModel().getSelectedItem().getNoviembre();
                diciembreN = tabledatos.getSelectionModel().getSelectedItem().getDiciembre();
                
                idN = tabledatos.getSelectionModel().getSelectedItem().getId();
                btnActualizaCampo.setDisable(false);
                btnEliminar.setDisable(false);
            }
            
        });
    }
    
    
    public void guardaLibroExcel(int fila, String cvo, String concepto, String ano, String enero, String febrero
    , String marzo, String abril, String mayo, String junio, String julio, String agosto, String septiembre
    , String octubre, String noviembre, String diciembre){
        //(fila, columna)
        String[] inf = {cvo,concepto,ano,enero,febrero,marzo,abril,mayo,junio,julio,agosto,septiembre
        ,octubre,noviembre,diciembre};
        //libroExcel = new String [datos][15];
        
        for(int x=0; x<15; x++){
                libroExcel [fila][x] = inf[x];
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cc = new ClaseConectora();
        try {
            System.out.println("Buscar IP");
            conn = cc.getConnection("localhost");
        } catch (IOException ex) {
        }
        altaPane.setVisible(true);
        altaCuentasPane.setVisible(false);
        consultasPane.setVisible(false);
        consultaEspPane.setVisible(false);
        conexionPane.setVisible(false);
        
        crtExcel.setVisible(false);
        btnEliminar.setVisible(false);
        btnActualizaCampo.setVisible(false);
        
        col_clave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        col_concepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        col_ano.setCellValueFactory(new PropertyValueFactory<>("ano"));
        col_enero.setCellValueFactory(new PropertyValueFactory<>("enero"));
        col_febrero.setCellValueFactory(new PropertyValueFactory<>("febrero"));
        col_marzo.setCellValueFactory(new PropertyValueFactory<>("marzo"));
        col_abril.setCellValueFactory(new PropertyValueFactory<>("abril"));
        col_mayo.setCellValueFactory(new PropertyValueFactory<>("mayo"));
        col_junio.setCellValueFactory(new PropertyValueFactory<>("junio"));
        col_julio.setCellValueFactory(new PropertyValueFactory<>("julio"));
        col_agosto.setCellValueFactory(new PropertyValueFactory<>("agosto"));
        col_septiembre.setCellValueFactory(new PropertyValueFactory<>("septiembre"));
        col_octubre.setCellValueFactory(new PropertyValueFactory<>("octubre"));
        col_noviembre.setCellValueFactory(new PropertyValueFactory<>("noviembre"));
        col_diciembre.setCellValueFactory(new PropertyValueFactory<>("diciembre"));
        
        try {
            if(conn != null){
                colocarMenuItems();//generar pestaña de tablas por año
                colocarMenuItemsComparativas();//generar pestaña de reporte excel comparativo
                rellenarSpltMenusSubCategorias();
            }
                
        } catch (SQLException ex) {
             
        }
        
    }  
    
    public void rellenarSpltMenusSubCategorias() throws SQLException{
        
        spltCuenta.getItems().clear();
        spltCuentasPane.getItems().clear();
        spltSubCuenta.getItems().clear();
        spltSubCuentasPane.getItems().clear();
        spltSubSubCuenta.getItems().clear();
        spltSub2CuentasPane.getItems().clear();
       
        Statement st = conn.createStatement(); 
        String sql="SELECT DISTINCT cuenta FROM presupuestos.valores ORDER BY cuenta ASC;";
        ResultSet resultSet_cuentas = st.executeQuery(sql);
                
        while(resultSet_cuentas.next()){
            MenuItem mi = new MenuItem(resultSet_cuentas.getString(1));
            MenuItem mi2 = new MenuItem(resultSet_cuentas.getString(1));
            mi.setId(resultSet_cuentas.getString(1));
            mi2.setId(resultSet_cuentas.getString(1));
            
            mi.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                  cuentaBD = mi.getId();
                  spltCuenta.setText(cuentaBD);
                }
            });
            mi2.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                  txtFldCuenta.setText(mi2.getId());
                  
                }
            });
            spltCuenta.getItems().add(mi);
            spltCuentasPane.getItems().add(mi2);
        }
        String sql2="SELECT DISTINCT sub_cuenta FROM presupuestos.valores ORDER BY sub_cuenta ASC;";
        ResultSet resultSet_sub_cuentas = st.executeQuery(sql2);
        
          while(resultSet_sub_cuentas.next()){
            MenuItem mi = new MenuItem(resultSet_sub_cuentas.getString(1));
            MenuItem mi2 = new MenuItem(resultSet_sub_cuentas.getString(1));
            mi.setId(resultSet_sub_cuentas.getString(1));
            mi2.setId(resultSet_sub_cuentas.getString(1));
            
            mi.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                  subCuentaBD = mi.getId();
                  spltSubCuenta.setText(subCuentaBD);
                }
            });
            mi2.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                  txtFldSubCuenta.setText(mi2.getId());
                  
                }
            });
            spltSubCuenta.getItems().add(mi);
            spltSubCuentasPane.getItems().add(mi2);
        }
        String sql3="SELECT DISTINCT sub2_cuenta FROM presupuestos.valores ORDER BY sub2_cuenta ASC;";
        ResultSet resultSet_sub2_cuentas = st.executeQuery(sql3);
        

        while(resultSet_sub2_cuentas.next()){
            MenuItem mi = new MenuItem(resultSet_sub2_cuentas.getString(1));
            MenuItem mi2 = new MenuItem(resultSet_sub2_cuentas.getString(1));
            mi.setId(resultSet_sub2_cuentas.getString(1));
            mi2.setId(resultSet_sub2_cuentas.getString(1));
            
            mi.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                  subSubCuentaBD = mi.getId();
                  spltSubSubCuenta.setText(subSubCuentaBD);
                }
            });
            mi2.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                  txtFldSub2Cuenta.setText(mi2.getId());
                  
                }
            });
            spltSubSubCuenta.getItems().add(mi);
            spltSub2CuentasPane.getItems().add(mi2);
        }
    }

    
    private void generarComparativa(File file, String rango, String mes ) throws IOException, WriteException, SQLException{
        System.out.println("El mes elejido es: "+mes);
        String[] rangos = rango.split(":");
        //presupuesto-ingreso
        int ano_presupuesto = Integer.parseInt(rangos[0]);
        int ano_ingreso = Integer.parseInt(rangos[1]);// OBTENER EVOLUCION DE INGRESO
        System.out.println(ano_ingreso);//2018
        Comparaciones cp = new Comparaciones();
        String[][] tabla_evolucion = cp.evolucion(ano_ingreso);
        
        String filename = file.getAbsolutePath();
        WritableWorkbook wb = Workbook.createWorkbook(new File(filename));
        WritableSheet sheet = wb.createSheet("Evolución "+ano_ingreso, 0);
        
        //fila,columna,contenido => Excel
        for(int x =0; x<tabla_evolucion.length; x++){//filas
            for(int y=0; y<13; y++){//columnas
               // System.out.println(libroExcel[x][y]);
                    sheet.addCell(new jxl.write.Label(y,x,tabla_evolucion[x][y]));
    
            }
        }
                
        wb.write();
        wb.close();

    }
    
    
    private void colocarMenuItemsComparativas() throws SQLException{
        Statement st = conn.createStatement();      
        String sql="SELECT DISTINCT anno FROM presupuestos.valores ORDER BY anno DESC;";
        ResultSet resultSet = st.executeQuery(sql);
        ArrayList<Integer> anos = new ArrayList<Integer>();
        
        int auxi = 0;
        while(resultSet.next()){
            anos.add(Integer.parseInt(resultSet.getString(1)));
            System.out.println(anos.get(auxi));
            auxi++;
        }
        
        int tamano = anos.size();//3
        int index = anos.size()-1;//2
        
        while(tamano!=0){
            if(tamano-1 != 0){ 
                MenuItem mi = new MenuItem(anos.get(index-1)+ " - "+anos.get(index));
                mi.setId(anos.get(index)+ ":"+anos.get(index-1));
                
                mi.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                    FileChooser fc = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All files", "*.xls");
                    fc.getExtensionFilters().add(extFilter);
                    File file = fc.showSaveDialog(null);
                    
                    try {
                        List<String> choices = new ArrayList<>();
                        choices.add("Enero");
                        choices.add("Febrero");
                        choices.add("Marzo");
                        choices.add("Abril");
                        choices.add("Mayo");
                        choices.add("Junio");
                        choices.add("Julio");
                        choices.add("Agosto");
                        choices.add("Septiembre");
                        choices.add("Octubre");
                        choices.add("Noviembre");
                        choices.add("Diciembre");

                        ChoiceDialog<String> dialog = new ChoiceDialog<>("Mes", choices);
                        dialog.setTitle("Choice Dialog");
                        dialog.setHeaderText("Look, a Choice Dialog");
                        dialog.setContentText("Choose your letter:");

                        // Traditional way to get the response value.
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()){
                            String mes = result.get().toLowerCase();
                            generarComparativa(file, mi.getId(),mes);
                        }

                    } catch (Exception ex) {
                    
                    }
                }
            });
                menuComparativa.getItems().add(mi);
            } 
            tamano--;
            index--;
        }
    }
    
    private void colocarMenuItems() throws SQLException{
        Statement st = conn.createStatement();      
        String sql="SELECT DISTINCT anno FROM presupuestos.valores ORDER BY anno ASC;";
        ResultSet resultSet = st.executeQuery(sql);
        
        while(resultSet.next()){
            MenuItem mi = new MenuItem(resultSet.getString(1));
            mi.setId(resultSet.getString(1));
            
            mi.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override public void handle(ActionEvent e) {
                    FXMLLoader fxl = new FXMLLoader(getClass().getResource("FXMLMostrarResultados.fxml"));
                    Parent root1 = null;
                    try {
                        root1 = fxl.load();
                    } catch (IOException ex) {
                        System.out.println("Error ventana");
                    }
                    Calculos c = fxl.getController();
                    Stage stage = new Stage();
                    stage.setTitle("Cálculo "+mi.getId());
                    stage.setScene(new Scene(root1));
                    stage.setResizable(false);
                    stage.show(); 
                    try {
                        c.Calcular(mi.getId());
                    } catch (SQLException ex) {
                    }
                }
            });
            menuCalculos.getItems().add(mi);
        }
            
    }
    
    @FXML
    private void abrirVentanaEdicion(ActionEvent event){
        System.out.println("Abrir ventana");
        try{
            FXMLLoader fxl = new FXMLLoader(getClass().getResource("FXMLEditarCampos.fxml"));
            Parent root1 = fxl.load();
            EdicionController ec = fxl.getController();
            Stage stage = new Stage();
            stage.setTitle("Actualizar");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(ContaduriaApp.stagePrincipal);
            //cuentaBD="", subCuentaBD="", subSubCuentaBD = "";
            stage.show(); 
            ec.rellenaCampos(idN,cuentaN,subCuentaN, sub2CuentaN,cvoN,conceptoN, anoN, eneroN, febreroN, marzoN, abrilN, mayoN, junioN, julioN, agostoN, septiembreN, octubreN, noviembreN, diciembreN);
            btnActualizaCampo.setDisable(true);
            stage.setOnCloseRequest(event3 ->
            {
                try {
                    if(lastQuery.equals(""))
                        buscarDatos(ordenar);
                    else
                        buscarDatos(lastQuery);
                    //colocarMenuItems();
                } catch (SQLException ex) {
                    
                }
                
            });
                    
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void limpiarCampos(){
        
        dataConcepto.setText("");
        txtFieldBuscarAno.setText("");
        txtFieldBuscarCVO.setText("");
        txtFieldBuscarMonto.setText("");
        
    }
    
 /* FUNCIONES DE LA VENTANA DE ALTAS DE CUENTAS*/
    
    private void limpiarCamposAltaCuentas(){
        txtFldCuenta.setText("");
        txtFldSubCuenta.setText("");
        txtFldSub2Cuenta.setText("");
        anoCuenta.setText("");
        cvoCuenta.setText("");
        
        cuentaValEnero.setText("0");cuentaValFebrero.setText("0");cuentaValMarzo.setText("0");cuentaValAbril.setText("0");
        cuentaValMayo.setText("0");cuentaValJunio.setText("0");cuentaValJulio.setText("0");cuentaValAgosto.setText("0");
        cuentaValSeptiembre.setText("0");cuentaValOctubre.setText("0");cuentaValNoviembre.setText("0");cuentaValDiciembre.setText("0");
    }
    
    @FXML
    private void cuentasValoresDisable(MouseEvent event){
        
        if(checkAnadir.isSelected()){
            cuentaValEnero.setDisable(false);cuentaValFebrero.setDisable(false);cuentaValMarzo.setDisable(false);
                cuentaValAbril.setDisable(false);cuentaValMayo.setDisable(false);cuentaValJunio.setDisable(false);
                cuentaValJulio.setDisable(false);cuentaValAgosto.setDisable(false);cuentaValSeptiembre.setDisable(false);
                cuentaValOctubre.setDisable(false);cuentaValNoviembre.setDisable(false);cuentaValDiciembre.setDisable(false);
        }else{
            cuentaValEnero.setDisable(true);cuentaValFebrero.setDisable(true);cuentaValMarzo.setDisable(true);
                cuentaValAbril.setDisable(true);cuentaValMayo.setDisable(true);cuentaValJunio.setDisable(true);
                cuentaValJulio.setDisable(true);cuentaValAgosto.setDisable(true);cuentaValSeptiembre.setDisable(true);
                cuentaValOctubre.setDisable(true);cuentaValNoviembre.setDisable(true);cuentaValDiciembre.setDisable(true);
        }
    }
    
    @FXML
    private void darAltaCuenta(ActionEvent event){
        
        String cvo = "\""+cvoCuenta.getText()+"\""+",";
        String cuenta = "\""+txtFldCuenta.getText()+"\""+",";
        String subCuenta = "\""+txtFldSubCuenta.getText()+"\""+",";
        String sub2Cuenta = "\""+txtFldSub2Cuenta.getText()+"\""+",";
        String anno = "\""+anoCuenta.getText()+"\""+",";
        
        BigInteger enero=new BigInteger("0"),febrero=new BigInteger("0"),marzo=new BigInteger("0"),
                abril =new BigInteger("0"),mayo=new BigInteger("0"),junio=new BigInteger("0"),
                julio=new BigInteger("0"),agosto=new BigInteger("0"),septiembre=new BigInteger("0"),
                octubre=new BigInteger("0"),noviembre=new BigInteger("0"),diciembre=new BigInteger("0");
        
        try{
            if(cuenta.equals("\"\",")){
                int fail = 1/0;
            }
            
            if(checkAnadir.isSelected()){//entra si esta seleccionado

                enero = new BigInteger(cuentaValEnero.getText());febrero = new BigInteger(cuentaValFebrero.getText());
                marzo = new BigInteger(cuentaValMarzo.getText());abril = new BigInteger(cuentaValAbril.getText());
                mayo = new BigInteger(cuentaValMayo.getText());junio = new BigInteger(cuentaValJunio.getText());
                julio = new BigInteger(cuentaValJulio.getText());agosto = new BigInteger(cuentaValAgosto.getText());
                septiembre = new BigInteger(cuentaValSeptiembre.getText());octubre = new BigInteger(cuentaValOctubre.getText());
                noviembre = new BigInteger(cuentaValNoviembre.getText());diciembre = new BigInteger(cuentaValDiciembre.getText());
                
                String sql = "INSERT INTO presupuestos.valores(cvo,cuenta,sub_cuenta,sub2_cuenta,anno,enero,febrero,marzo,abril,mayo,junio,julio,agosto,septiembre,octubre,noviembre,diciembre) "
                        + "VALUES ("+cvo+cuenta+subCuenta+sub2Cuenta+anno+enero+","+febrero+","+marzo+","+abril+","+mayo+","+junio+","+julio+","+agosto+","+septiembre+","+octubre+","+noviembre+","+diciembre+");";
                System.out.println(sql);
                Statement st = conn.createStatement();
                st.executeUpdate(sql);
                limpiarCamposAltaCuentas();
            }else{
                
                String sql = "INSERT INTO presupuestos.valores(cvo,cuenta,sub_cuenta,sub2_cuenta,anno,enero,febrero,marzo,abril,mayo,junio,julio,agosto,septiembre,octubre,noviembre,diciembre) "
                        + "VALUES ("+cvo+cuenta+subCuenta+sub2Cuenta+anno+enero+","+febrero+","+marzo+","+abril+","+mayo+","+junio+","+julio+","+agosto+","+septiembre+","+octubre+","+noviembre+","+diciembre+");";
                System.out.println(sql);
                Statement st = conn.createStatement();
                st.executeUpdate(sql);
                limpiarCamposAltaCuentas();
                rellenarSpltMenusSubCategorias();
            }
    
        }catch(Exception e){
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Alguno de los campos está vacío o no contiene la información requerida."
                    + " Por favor verifíque e intente nuevamente");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

/* FUNCIONES DE LA VENTANA DE ALTAS*/
    @FXML
    private void darAltaConcepto(ActionEvent event) throws SQLException{
        
        String concepto = "\""+dataConcepto.getText()+"\""+",";
        String cvo = "\""+dataCvo.getText()+"\"";
        String cuenta_aqui = cuentaBD;
        String subCuenta_aqui = subCuentaBD;
        String sub2Cuenta_aqui = subSubCuentaBD;
        BigInteger enero=new BigInteger("0"),febrero=new BigInteger("0"),marzo=new BigInteger("0"),
                abril =new BigInteger("0"),mayo=new BigInteger("0"),junio=new BigInteger("0"),
                julio=new BigInteger("0"),agosto=new BigInteger("0"),septiembre=new BigInteger("0"),
                octubre=new BigInteger("0"),noviembre=new BigInteger("0"),diciembre=new BigInteger("0");
        try{
            //int cvo = Integer.parseInt(dataCvo.getText());
            if(cuenta_aqui.equals("") || subCuenta_aqui.equals("") || sub2Cuenta_aqui.equals("")){
                int fail = 1/0;
            }
                
            int ano = Integer.parseInt(dataAno.getText()); 

            Statement st = conn.createStatement();
           
            String sql_busca = "SELECT * FROM valores WHERE cvo="+cvo+" AND anno="+ano+";";
            System.out.println(sql_busca);
            ResultSet resultSet = st.executeQuery(sql_busca); 
            
            if(resultSet.next()){// Si existe un registro con estas condiciones entra al if
                
                if(dataEnero.getText().equals(""))
                    enero = new BigInteger(resultSet.getString(8));
                else
                    enero = new BigInteger(dataEnero.getText());
                if(dataFebrero.getText().equals(""))
                    febrero = new BigInteger(resultSet.getString(9));
                else
                    febrero = new BigInteger(dataFebrero.getText());
                if(dataMarzo.getText().equals(""))
                    marzo = new BigInteger(resultSet.getString(10));
                else
                    marzo = new BigInteger(dataMarzo.getText());
                if(dataAbril.getText().equals(""))
                    abril = new BigInteger(resultSet.getString(11));
                else
                    abril = new BigInteger(dataAbril.getText());
                if(dataMayo.getText().equals(""))
                    mayo = new BigInteger(resultSet.getString(12));
                else
                    mayo = new BigInteger(dataMayo.getText());
                if(dataJunio.getText().equals(""))
                    junio = new BigInteger(resultSet.getString(13));
                else
                    junio = new BigInteger(dataJunio.getText());
                if(dataJulio.getText().equals(""))
                    julio = new BigInteger(resultSet.getString(14));
                else
                    julio = new BigInteger(dataJulio.getText());
                if(dataAgosto.getText().equals(""))
                    agosto = new BigInteger(resultSet.getString(15));
                else
                    agosto = new BigInteger(dataAgosto.getText());
                if(dataSeptiembre.getText().equals(""))
                    septiembre = new BigInteger(resultSet.getString(16));
                else
                    septiembre = new BigInteger(dataSeptiembre.getText());
                if(dataOctubre.getText().equals(""))
                    octubre = new BigInteger(resultSet.getString(17));
                else
                    octubre = new BigInteger(dataOctubre.getText());
                if(dataNoviembre.getText().equals(""))
                    noviembre = new BigInteger(resultSet.getString(18));
                else
                    noviembre = new BigInteger(dataNoviembre.getText());
                if(dataDiciembre.getText().equals(""))
                    diciembre = new BigInteger(resultSet.getString(19));
                else
                    diciembre = new BigInteger(dataDiciembre.getText());
            
                String id = resultSet.getString(1);
                String sql_update = "UPDATE valores SET cvo="+cvo+", cuenta="+cuenta_aqui+", sub_cuenta="+subCuenta_aqui+" "
                        + "sub2_cuenta="+sub2Cuenta_aqui+" concepto="+concepto+"anno="+ano+", enero="+enero+", febrero="+febrero+", marzo="+marzo+
                        ", abril="+abril+", mayo="+mayo+", junio="+junio+", julio="+julio+", agosto="+agosto+", septiembre="+septiembre+
                        ", octubre="+octubre+", noviembre="+noviembre+", diciembre="+diciembre+" WHERE id="+id+";";
                st.executeUpdate(sql_update);
                System.out.println(sql_update);
            }else{
                if(!dataEnero.getText().equals(""))
                    enero = new BigInteger(dataEnero.getText());
                if(!dataFebrero.getText().equals(""))
                    febrero = new BigInteger(dataFebrero.getText());
                if(!dataMarzo.getText().equals(""))
                    marzo = new BigInteger(dataMarzo.getText());
                if(!dataAbril.getText().equals(""))
                    abril = new BigInteger(dataAbril.getText());
                if(!dataMayo.getText().equals(""))
                    mayo = new BigInteger(dataMayo.getText());
                if(!dataJunio.getText().equals(""))
                    junio = new BigInteger(dataJunio.getText());
                if(!dataJulio.getText().equals(""))
                    julio = new BigInteger(dataJulio.getText());
                if(!dataAgosto.getText().equals(""))
                    agosto = new BigInteger(dataAgosto.getText());
                if(!dataSeptiembre.getText().equals(""))
                    septiembre = new BigInteger(dataSeptiembre.getText());
                if(!dataOctubre.getText().equals(""))
                    octubre = new BigInteger(dataOctubre.getText());
                if(!dataNoviembre.getText().equals(""))
                    noviembre = new BigInteger(dataNoviembre.getText());
                if(!dataDiciembre.getText().equals(""))
                    diciembre = new BigInteger(dataDiciembre.getText());
                
                String sql="INSERT INTO valores(cvo,cuenta,sub_cuenta,sub2_cuenta,concepto,anno,enero,febrero,marzo,abril,mayo,junio,julio,"
                + "agosto,septiembre,octubre,noviembre,diciembre)"+" VALUES("+cvo+",\""+cuentaBD+"\",\""+subCuentaBD+"\",\""+subSubCuentaBD+"\","+concepto+""+ano+","+
                enero+","+febrero+","+marzo+","+abril+","+mayo+","+junio+","+julio+","+
                agosto+","+septiembre+","+octubre+","+noviembre+","+diciembre+")"+";";
                System.out.println(sql);
                st.executeUpdate(sql);
            }
          
            dataCvo.setText("");
            dataAno.setText("");
            cuentaBD = "";
            subCuentaBD="";
            subSubCuentaBD="";
            dataConcepto.setText("");
            dataEnero.setText("");
            dataFebrero.setText("");
            dataMarzo.setText("");
            dataAbril.setText("");
            dataMayo.setText("");
            dataJunio.setText("");
            dataJulio.setText("");
            dataAgosto.setText("");
            dataSeptiembre.setText("");
            dataOctubre.setText("");
            dataNoviembre.setText("");
            dataDiciembre.setText("");
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Alguno de los campos está vacío o no contiene la información requerida."
                    + " Por favor verifíque e intente nuevamente");
            alert.showAndWait();
            e.printStackTrace();
        }
            
       
    }
    
    @FXML
    private void cambiarIP() throws SQLException, IOException{

        System.out.println("IP: "+txtIP.getText());
        ClaseConectora.ip_base_de_datos = txtIP.getText();
        conn = cc.getConnection(txtIP.getText());
        if(conn != null){
            menuCalculos.getItems().clear();
            menuComparativa.getItems().clear();
            colocarMenuItems();
        }
            
    }

    
   /* FUNCIONES DE LA VENTANA DE BÚSQUEDA PERSONALIZADA*/  
    @FXML
    private void buscarPersonalizado(ActionEvent event){
        
        String ano = txtFieldBuscarAno.getText();
        String cvo = txtFieldBuscarCVO.getText();
        String monto = txtFieldBuscarMonto.getText();
        String concepto = txtFieldBuscarCon.getText();
        
        if(ano.equals("") & cvo.equals("") & monto.equals("") & concepto.equals(""))
            return;
        
        try{
            String sql = "SELECT * FROM valores WHERE ";
            int agregar_and  =0;

            if(!ano.equals("")){
                int ano_int = Integer.parseInt(ano);
                //sql +="concepto LIKE \"%"+concepto+"%\"";
                sql +="anno="+ano;
                agregar_and++;
            }
            if(!cvo.equals("")){
                String cvo_string = "cvo=\""+cvo+"\"";
                if(agregar_and>0)
                    sql += " AND ";
                sql +=cvo_string;
                agregar_and++;
            }
            if(!monto.equals("")){
                BigInteger monto_int = new BigInteger(monto);
                if(agregar_and>0)
                    sql += " OR ";
                sql +="enero="+monto+" OR febrero="+monto+" OR marzo="+monto+" OR abril="+monto+" OR mayo="+monto
                        +" OR junio="+monto+" OR julio="+monto+" OR agosto="+monto+" OR septiembre="+monto+" OR octubre="+monto
                        +" OR noviembre="+monto+" OR diciembre="+monto;
            }
            if(!concepto.equals("")){
                
                if(agregar_and>0)
                    sql += " AND ";
                sql +="concepto LIKE \"%"+concepto+"%\"";
                agregar_and++;
            }
            sql += ";";
            System.out.println(sql);
            lastQuery=sql;
            buscarDatos(sql);
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Alguno de los campos no contiene el valor esperado. Recuerde que estos deben ser "
                    + "numéricos");
            alert.showAndWait();
            e.printStackTrace();
        }
            
    }
    
    @FXML
    private void generaExcel() throws IOException, WriteException{
        
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All files", "*.xls");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showSaveDialog(null);
        
        if(file == null){
            return;
        }
       
        
        String filename = file.getAbsolutePath();
        WritableWorkbook wb = Workbook.createWorkbook(new File(filename));
        WritableSheet sheet = wb.createSheet("Sheet1", 0);

        String[] column_names = {"Cvo","Concepto","Año","Enero","Febrero","Marzo","Abril","Mayo",
        "Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        for(int x=0; x<15; x++){
            sheet.addCell(new jxl.write.Label(x,0,column_names[x]) {});
            
        }
        
        for(int x =0; x<=datos-1; x++){//filas
            for(int y=0; y<15; y++){//columnas
               // System.out.println(libroExcel[x][y]);
                    sheet.addCell(new jxl.write.Label(y,x+1,libroExcel[x][y]));
    
            }
        }
                
        wb.write();
        wb.close();
    }

    
    
    public class Std{
        
        SimpleStringProperty id;
        SimpleStringProperty clave;
        SimpleStringProperty cuenta;
        SimpleStringProperty sub_cuenta;
        SimpleStringProperty sub2_cuenta;
        SimpleStringProperty concepto;
        SimpleStringProperty ano;
        SimpleStringProperty enero;
        SimpleStringProperty febrero;
        SimpleStringProperty marzo;
        SimpleStringProperty abril;
        SimpleStringProperty mayo;
        SimpleStringProperty junio;
        SimpleStringProperty julio;
        SimpleStringProperty agosto;
        SimpleStringProperty septiembre;
        SimpleStringProperty octubre;
        SimpleStringProperty noviembre;
        SimpleStringProperty diciembre;
        
        public Std(String id,String cuenta, String sub_cuenta,String sub2_cuenta, String clave, String concepto,String ano, String enero, String febrero,
                String marzo, String abril, String mayo, String junio, String julio, String agosto,
                String septiembre, String octubre, String noviembre, String diciembre){
            this.id = new SimpleStringProperty(id);
            this.clave = new SimpleStringProperty(clave);
            this.cuenta = new SimpleStringProperty(cuenta);
            this.sub_cuenta = new SimpleStringProperty(sub_cuenta);
            this.sub2_cuenta = new SimpleStringProperty(sub2_cuenta);
            this.concepto = new SimpleStringProperty(concepto);
            this.ano = new SimpleStringProperty(ano);
            this.enero = new SimpleStringProperty(enero);
            this.febrero = new SimpleStringProperty(febrero);
            this.marzo = new SimpleStringProperty(marzo);
            this.abril = new SimpleStringProperty(abril);
            this.mayo = new SimpleStringProperty(mayo);
            this.junio = new SimpleStringProperty(junio);
            this.julio = new SimpleStringProperty(julio);
            this.agosto = new SimpleStringProperty(agosto);
            this.septiembre = new SimpleStringProperty(septiembre);
            this.octubre = new SimpleStringProperty(octubre);
            this.noviembre = new SimpleStringProperty(noviembre);
            this.diciembre = new SimpleStringProperty(diciembre);
        }
        public String getId() {
            return id.get();
        }
        public String getCuenta() {
            return cuenta.get();
        }
        public String getSubCuenta() {
            return sub_cuenta.get();
        }
        public String getSub2Cuenta() {
            return sub2_cuenta.get();
        }
        public String getClave() {
            return clave.get();
        }

        public String getConcepto() {
            return concepto.get();
        }
        public String getAno() {
            return ano.get();
        }

        public String getEnero() {
            return enero.get();
        }

        public String getFebrero() {
            return febrero.get();
        }

        public String getMarzo() {
            return marzo.get();
        }
        public String getAbril() {
            return abril.get();
        }
        public String getMayo() {
            return mayo.get();
        }
        public String getJunio() {
            return junio.get();
        }
        public String getJulio() {
            return julio.get();
        }
        public String getAgosto() {
            return agosto.get();
        }
        public String getSeptiembre() {
            return septiembre.get();
        }
        public String getOctubre() {
            return octubre.get();
        }
        public String getNoviembre() {
            return noviembre.get();
        }
        public String getDiciembre() {
            return diciembre.get();
        }
        
    }
    
}


