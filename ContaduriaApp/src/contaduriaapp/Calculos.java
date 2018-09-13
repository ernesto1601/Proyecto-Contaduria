/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contaduriaapp;

import conectividad.ClaseConectora;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author ernestosandovalbecerra
 */
public class Calculos implements Initializable{

    @FXML
    private ImageView img_enero,img_febrero,img_marzo,img_abril,img_mayo,img_junio,img_julio,img_agosto,img_septiembre,img_octubre,img_noviembre,img_diciembre,img_totalizado;
    @FXML
    private Label enero_impuesto,enero_derechos,enero_productos,enero_aprovechamientos,enero_ingresos,enero_participaciones,enero_ayuda,enero_total;
    @FXML
    private Label febrero_impuesto,febrero_derechos,febrero_productos,febrero_aprovechamientos,febrero_ingresos,febrero_participaciones,febrero_ayuda,febrero_total;
    @FXML
    private Label marzo_impuesto,marzo_derechos,marzo_productos,marzo_aprovechamientos,marzo_ingresos,marzo_participaciones,marzo_ayuda,marzo_total;
    @FXML
    private Label abril_impuesto,abril_derechos,abril_productos,abril_aprovechamientos,abril_ingresos,abril_participaciones,abril_ayuda,abril_total;
    @FXML
    private Label mayo_impuesto,mayo_derechos,mayo_productos,mayo_aprovechamientos,mayo_ingresos,mayo_participaciones,mayo_ayuda,mayo_total;
    @FXML
    private Label junio_impuesto,junio_derechos,junio_productos,junio_aprovechamientos,junio_ingresos,junio_participaciones,junio_ayuda,junio_total;
    @FXML
    private Label julio_impuesto,julio_derechos,julio_productos,julio_aprovechamientos,julio_ingresos,julio_participaciones,julio_ayuda,julio_total;
    @FXML
    private Label agosto_impuesto,agosto_derechos,agosto_productos,agosto_aprovechamientos,agosto_ingresos,agosto_participaciones,agosto_ayuda,agosto_total;
    @FXML
    private Label septiembre_impuesto,septiembre_derechos,septiembre_productos,septiembre_aprovechamientos,septiembre_ingresos,septiembre_participaciones,septiembre_ayuda,septiembre_total;
    @FXML
    private Label octubre_impuesto,octubre_derechos,octubre_productos,octubre_aprovechamientos,octubre_ingresos,octubre_participaciones,octubre_ayuda,octubre_total;
    @FXML
    private Label noviembre_impuesto,noviembre_derechos,noviembre_productos,noviembre_aprovechamientos,noviembre_ingresos,noviembre_participaciones,noviembre_ayuda,noviembre_total;
    @FXML
    private Label diciembre_impuesto,diciembre_derechos,diciembre_productos,diciembre_aprovechamientos,diciembre_ingresos,diciembre_participaciones,diciembre_ayuda,diciembre_total;
    @FXML
    private Label enero_deriv,febrero_deriv,marzo_deriv,abril_deriv,mayo_deriv,junio_deriv,julio_deriv,agosto_deriv,septiembre_deriv,octubre_deriv,noviembre_deriv,diciembre_deriv,totalizado_deriv;
    @FXML
    private Label totalizado_impuesto,totalizado_derechos,totalizado_productos,totalizado_aprovechamientos,totalizado_ingresos,totalizado_participaciones,totalizado_ayuda,totalizado_total;
    @FXML
    private Button grar_reporte;
    
    ClaseConectora cc = new ClaseConectora();
    Connection conn;
    String[] libroExcel = new String[130];
    
    @FXML
    private void generarReporte(ActionEvent event) throws IOException, WriteException{
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All files", "*.xls");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showSaveDialog(null);
        
        if(file == null){
            return;
        }
        
        String filename = file.getAbsolutePath();
        WritableWorkbook wb = Workbook.createWorkbook(new File(filename));
        WritableSheet sheet = wb.createSheet("Cálculos", 0);
        
        String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo",
        "Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        String[] importes = {"Ingresos Estatales","Impuestos","Derechos","Productos","Aprovechamientos",
            "Ingresor por venta de Vienes y Servicios","Participaciones y Aportaciones","Transferencias, Asignaciones y Otras Ayudas",
        "Ingresos Derivados de Financiamiento","Total"};
        
        int manejador = 0;
        int fila_enum = 0;
        int index_libro=0;
        for(int x =0; x<meses.length; x++){//columnas,fila
            sheet.addCell(new jxl.write.Label(0,fila_enum,meses[x]));
            fila_enum++;
            for(int y=0; y<importes.length; y++){//filas

                    sheet.addCell(new jxl.write.Label(manejador,fila_enum,importes[y]));
                    sheet.addCell(new jxl.write.Label(manejador+1,fila_enum,libroExcel[index_libro]));
                
                fila_enum++;
                index_libro++;
            }
            sheet.addCell(new jxl.write.Label(0,fila_enum,null));
            fila_enum++;
        }
                
        wb.write();
        wb.close();
    }
    
    private int limiteImpuestos(int ano) throws SQLException{
        String sql ="SELECT COUNT(concepto) FROM presupuestos.valores WHERE cuenta=\"Ingresos Estatales\" AND sub_cuenta=\" Impuestos \" AND anno="+ano+";";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }
    private int limiteDerechos(int ano) throws SQLException{
        String sql ="SELECT COUNT(concepto) FROM presupuestos.valores WHERE cuenta=\"Ingresos Estatales\" AND sub_cuenta=\" Derechos \" AND anno="+ano+";";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }
    private int limiteProductos(int ano) throws SQLException{
        String sql ="SELECT COUNT(concepto) FROM presupuestos.valores WHERE cuenta=\"Ingresos Estatales\" AND sub_cuenta=\" Productos \" AND anno="+ano+";";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }
    private int limiteAprovechamientos(int ano) throws SQLException{
        String sql ="SELECT COUNT(concepto) FROM presupuestos.valores WHERE cuenta=\"Ingresos Estatales\" AND sub_cuenta=\" Aprovechamientos \" AND anno="+ano+";";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }
    private int limiteIngVentas(int ano) throws SQLException{
        String sql ="SELECT COUNT(concepto) FROM presupuestos.valores WHERE cuenta=\"Ingresos Estatales\" AND sub_cuenta=\" Ingresos por Venta de Bienes y Servicios \" AND anno="+ano+";";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }
    private int limitePartAport(int ano) throws SQLException{
        String sql ="SELECT COUNT(concepto) FROM presupuestos.valores WHERE cuenta=\"Participaciones y Aportaciones\" AND anno="+ano+";";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }
    private int limiteTransferencias(int ano) throws SQLException{
        String sql ="SELECT COUNT(concepto) FROM presupuestos.valores WHERE cuenta=\"Transferencias  Asignaciones  Subsidios y Otras Ayudas\" AND anno="+ano+";";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }
    private int limiteIngresos(int ano) throws SQLException{
        String sql ="SELECT COUNT(concepto) FROM presupuestos.valores WHERE cuenta=\"Ingresos Derivados de Financiamientos\" AND anno="+ano+";";
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        resultSet.next();
        return Integer.parseInt(resultSet.getString(1));
    }
    
    
    public void Calcular(String ano) throws SQLException{
        img_enero.setImage(new Image("imagenes/enero.png"));
        img_febrero.setImage(new Image("imagenes/febrero.png"));
        img_marzo.setImage(new Image("imagenes/marzo.png"));
        img_abril.setImage(new Image("imagenes/abril.png"));
        img_mayo.setImage(new Image("imagenes/mayo.png"));
        img_junio.setImage(new Image("imagenes/JUNIO.png"));
        img_julio.setImage(new Image("imagenes/JULIO.png"));
        img_agosto.setImage(new Image("imagenes/AGOSTO.png"));
        img_septiembre.setImage(new Image("imagenes/SEPTIEMBRE.png"));
        img_octubre.setImage(new Image("imagenes/OCTUBRE.png"));
        img_noviembre.setImage(new Image("imagenes/NOVIEMBRE.png"));
        img_diciembre.setImage(new Image("imagenes/DICIEMBRE.png"));
        img_totalizado.setImage(new Image("imagenes/total.png"));
        BigInteger impuestos_enero=new BigInteger("0"),impuestos_febrero=new BigInteger("0"),impuestos_marzo=new BigInteger("0"),impuestos_abril=new BigInteger("0"),impuestos_mayo=new BigInteger("0"),impuestos_junio=new BigInteger("0"),impuestos_julio=new BigInteger("0"),impuestos_agosto=new BigInteger("0"),impuestos_septiembre=new BigInteger("0"),impuestos_octubre=new BigInteger("0"),impuestos_noviembre=new BigInteger("0"),impuestos_diciembre=new BigInteger("0");
        BigInteger derechos_enero=new BigInteger("0"),derechos_febrero=new BigInteger("0"),derechos_marzo=new BigInteger("0"),derechos_abril=new BigInteger("0"),derechos_mayo=new BigInteger("0"),derechos_junio=new BigInteger("0"),derechos_julio=new BigInteger("0"),derechos_agosto=new BigInteger("0"),derechos_septiembre=new BigInteger("0"),derechos_octubre=new BigInteger("0"),derechos_noviembre=new BigInteger("0"),derechos_diciembre=new BigInteger("0");
        BigInteger productos_enero=new BigInteger("0"),productos_febrero=new BigInteger("0"),productos_marzo=new BigInteger("0"),productos_abril=new BigInteger("0"),productos_mayo=new BigInteger("0"),productos_junio=new BigInteger("0"),productos_julio=new BigInteger("0"),productos_agosto=new BigInteger("0"),productos_septiembre=new BigInteger("0"),productos_octubre=new BigInteger("0"),productos_noviembre=new BigInteger("0"),productos_diciembre=new BigInteger("0");
        BigInteger aprovechamientos_enero=new BigInteger("0"),aprovechamientos_febrero=new BigInteger("0"),aprovechamientos_marzo=new BigInteger("0"),aprovechamientos_abril=new BigInteger("0"),aprovechamientos_mayo=new BigInteger("0"),aprovechamientos_junio=new BigInteger("0"),aprovechamientos_julio=new BigInteger("0"),aprovechamientos_agosto=new BigInteger("0"),aprovechamientos_septiembre=new BigInteger("0"),aprovechamientos_octubre=new BigInteger("0"),aprovechamientos_noviembre=new BigInteger("0"),aprovechamientos_diciembre=new BigInteger("0");
        BigInteger bienes_serv_enero=new BigInteger("0"),bienes_serv_febrero=new BigInteger("0"),bienes_serv_marzo=new BigInteger("0"),bienes_serv_abril=new BigInteger("0"),bienes_serv_mayo=new BigInteger("0"),bienes_serv_junio=new BigInteger("0"),bienes_serv_julio=new BigInteger("0"),bienes_serv_agosto=new BigInteger("0"),bienes_serv_septiembre=new BigInteger("0"),bienes_serv_octubre=new BigInteger("0"),bienes_serv_noviembre=new BigInteger("0"),bienes_serv_diciembre=new BigInteger("0");
        BigInteger part_aport_enero=new BigInteger("0"),part_aport_febrero=new BigInteger("0"),part_aport_marzo=new BigInteger("0"),part_aport_abril=new BigInteger("0"),part_aport_mayo=new BigInteger("0"),part_aport_junio=new BigInteger("0"),part_aport_julio=new BigInteger("0"),part_aport_agosto=new BigInteger("0"),part_aport_septiembre=new BigInteger("0"),part_aport_octubre=new BigInteger("0"),part_aport_noviembre=new BigInteger("0"),part_aport_diciembre=new BigInteger("0");
        BigInteger ayuda_enero=new BigInteger("0"),ayuda_febrero=new BigInteger("0"),ayuda_marzo=new BigInteger("0"),ayuda_abril=new BigInteger("0"),ayuda_mayo=new BigInteger("0"),ayuda_junio=new BigInteger("0"),ayuda_julio=new BigInteger("0"),ayuda_agosto=new BigInteger("0"),ayuda_septiembre=new BigInteger("0"),ayuda_octubre=new BigInteger("0"),ayuda_noviembre=new BigInteger("0"),ayuda_diciembre=new BigInteger("0");
        BigInteger derivados_enero=new BigInteger("0"),derivados_febrero=new BigInteger("0"),derivados_marzo=new BigInteger("0"),derivados_abril=new BigInteger("0"),derivados_mayo=new BigInteger("0"),derivados_junio=new BigInteger("0"),derivados_julio=new BigInteger("0"),derivados_agosto=new BigInteger("0"),derivados_septiembre=new BigInteger("0"),derivados_octubre=new BigInteger("0"),derivados_noviembre=new BigInteger("0"),derivados_diciembre=new BigInteger("0");

        
        BigInteger subtotal_enero=new BigInteger("0"),subtotal_febrero=new BigInteger("0"),subtotal_marzo=new BigInteger("0"),subtotal_abril=new BigInteger("0"),subtotal_mayo=new BigInteger("0"),subtotal_junio=new BigInteger("0"),subtotal_julio=new BigInteger("0"),subtotal_agosto=new BigInteger("0"),subtotal_septiembre=new BigInteger("0"),subtotal_octubre=new BigInteger("0"),subtotal_noviembre=new BigInteger("0"),subtotal_diciembre=new BigInteger("0");
        BigInteger total_impuestos=new BigInteger("0"),total_derechos=new BigInteger("0"),total_productos=new BigInteger("0"),total_aprovechamientos=new BigInteger("0"),total_bienes_serv=new BigInteger("0"),total_part_aport=new BigInteger("0"),total_ayuda=new BigInteger("0"),total_derivados=new BigInteger("0");
        BigInteger total_total=new BigInteger("0");
        
        String sql = "SELECT enero,febrero,marzo,abril,mayo,junio,julio,agosto,septiembre,octubre,noviembre,diciembre FROM presupuestos.valores WHERE anno="+ano+" AND concepto IS NOT null;";
        System.out.println(sql);
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        int contador = 0;
        
        int ano_curso = Integer.parseInt(ano);
        
        int limite_impuesto = limiteImpuestos(ano_curso);
        int limite_derechos = limiteDerechos(ano_curso) + limite_impuesto;
        int limite_productos = limiteProductos(ano_curso) + limite_derechos;
        int limite_aprovechamiento = limiteAprovechamientos(ano_curso) + limite_productos;
        int limite_ing_ventas = limiteIngVentas(ano_curso) + limite_aprovechamiento;
        int limite_part_aport = limitePartAport(ano_curso) + limite_ing_ventas;
        int limite_transferencias = limiteTransferencias(ano_curso) + limite_part_aport;
        int limite_ingresos = limiteIngresos(ano_curso) + limite_transferencias;

        while(resultSet.next()){
            BigInteger enero = new BigInteger(resultSet.getString(1));
            BigInteger febrero = new BigInteger(resultSet.getString(2));
            BigInteger marzo = new BigInteger(resultSet.getString(3));
            BigInteger abril = new BigInteger(resultSet.getString(4));
            BigInteger mayo = new BigInteger(resultSet.getString(5));
            BigInteger junio = new BigInteger(resultSet.getString(6));
            BigInteger julio = new BigInteger(resultSet.getString(7));
            BigInteger agosto = new BigInteger(resultSet.getString(8));
            BigInteger septiembre = new BigInteger(resultSet.getString(9));
            BigInteger octubre = new BigInteger(resultSet.getString(10));
            BigInteger noviembre = new BigInteger(resultSet.getString(11));
            BigInteger diciembre = new BigInteger(resultSet.getString(12));
            
            if(contador>=0 && contador<limite_impuesto){//impuestos
                impuestos_enero =impuestos_enero.add(enero);
                impuestos_febrero=impuestos_febrero.add(febrero);
                impuestos_marzo=impuestos_marzo.add(marzo);
                impuestos_abril=impuestos_abril.add(abril);
                impuestos_mayo=impuestos_mayo.add(mayo);
                impuestos_junio=impuestos_junio.add(junio);
                impuestos_julio=impuestos_julio.add(julio);
                impuestos_agosto=impuestos_agosto.add(agosto);
                impuestos_septiembre=impuestos_septiembre.add(septiembre);
                impuestos_octubre=impuestos_octubre.add(octubre);
                impuestos_noviembre=impuestos_noviembre.add(noviembre);
                impuestos_diciembre=impuestos_diciembre.add(diciembre);
                
                total_impuestos=total_impuestos.add(enero);
                total_impuestos=total_impuestos.add(febrero);
                total_impuestos=total_impuestos.add(marzo);
                total_impuestos=total_impuestos.add(abril);
                total_impuestos=total_impuestos.add(mayo);
                total_impuestos=total_impuestos.add(junio);
                total_impuestos=total_impuestos.add(julio);
                total_impuestos=total_impuestos.add(agosto);
                total_impuestos=total_impuestos.add(septiembre);
                total_impuestos=total_impuestos.add(octubre);
                total_impuestos=total_impuestos.add(noviembre);
                total_impuestos=total_impuestos.add(diciembre);
                System.out.println(total_impuestos);
            }
            if(contador>=limite_impuesto && contador<limite_derechos){//derechos
                derechos_enero=derechos_enero.add(enero);
                derechos_febrero=derechos_febrero.add(febrero);
                derechos_marzo=derechos_marzo.add(marzo);
                derechos_abril=derechos_abril.add(abril);
                derechos_mayo=derechos_mayo.add(mayo);
                derechos_junio=derechos_junio.add(junio);
                derechos_julio=derechos_julio.add(julio);
                derechos_agosto=derechos_agosto.add(agosto);
                derechos_septiembre=derechos_septiembre.add(septiembre);
                derechos_octubre=derechos_octubre.add(octubre);
                derechos_noviembre=derechos_noviembre.add(noviembre);
                derechos_diciembre=derechos_diciembre.add(diciembre);
                
                total_derechos=total_derechos.add(enero);
                total_derechos=total_derechos.add(febrero);
                total_derechos=total_derechos.add(marzo);
                total_derechos=total_derechos.add(abril);
                total_derechos=total_derechos.add(mayo);
                total_derechos=total_derechos.add(junio);
                total_derechos=total_derechos.add(julio);
                total_derechos=total_derechos.add(agosto);
                total_derechos=total_derechos.add(septiembre);
                total_derechos=total_derechos.add(octubre);
                total_derechos=total_derechos.add(noviembre);
                total_derechos=total_derechos.add(diciembre);

            }
            if(contador>=limite_derechos && contador<limite_productos){//productos
                productos_enero=productos_enero.add(enero);
                productos_febrero=productos_febrero.add(febrero);
                productos_marzo=productos_marzo.add(marzo);
                productos_abril=productos_abril.add(abril);
                productos_mayo=productos_mayo.add(mayo);
                productos_junio=productos_junio.add(junio);
                productos_julio=productos_julio.add(julio);
                productos_agosto=productos_agosto.add(agosto);
                productos_septiembre=productos_septiembre.add(septiembre);
                productos_octubre=productos_octubre.add(octubre);
                productos_noviembre=productos_noviembre.add(noviembre);
                productos_diciembre=productos_diciembre.add(diciembre);
                
                total_productos=total_productos.add(enero);
                total_productos=total_productos.add(febrero);
                total_productos=total_productos.add(marzo);
                total_productos=total_productos.add(abril);
                total_productos=total_productos.add(mayo);
                total_productos=total_productos.add(junio);
                total_productos=total_productos.add(julio);
                total_productos=total_productos.add(agosto);
                total_productos=total_productos.add(septiembre);
                total_productos=total_productos.add(octubre);
                total_productos=total_productos.add(noviembre);
                total_productos=total_productos.add(diciembre);

            }
            if(contador>=limite_productos && contador<limite_aprovechamiento){//aprovechamientos
                aprovechamientos_enero=aprovechamientos_enero.add(enero);
                aprovechamientos_febrero=aprovechamientos_febrero.add(febrero);
                aprovechamientos_marzo=aprovechamientos_marzo.add(marzo);
                aprovechamientos_abril=aprovechamientos_abril.add(abril);
                aprovechamientos_mayo=aprovechamientos_mayo.add(mayo);
                aprovechamientos_junio=aprovechamientos_junio.add(junio);
                aprovechamientos_julio=aprovechamientos_julio.add(julio);
                aprovechamientos_agosto=aprovechamientos_agosto.add(agosto);
                aprovechamientos_septiembre=aprovechamientos_septiembre.add(septiembre);
                aprovechamientos_octubre=aprovechamientos_octubre.add(octubre);
                aprovechamientos_noviembre=aprovechamientos_noviembre.add(noviembre);
                aprovechamientos_diciembre=aprovechamientos_diciembre.add(diciembre);
                
                total_aprovechamientos=total_aprovechamientos.add(enero);
                total_aprovechamientos=total_aprovechamientos.add(febrero);
                total_aprovechamientos=total_aprovechamientos.add(marzo);
                total_aprovechamientos=total_aprovechamientos.add(abril);
                total_aprovechamientos=total_aprovechamientos.add(mayo);
                total_aprovechamientos=total_aprovechamientos.add(junio);
                total_aprovechamientos=total_aprovechamientos.add(julio);
                total_aprovechamientos=total_aprovechamientos.add(agosto);
                total_aprovechamientos=total_aprovechamientos.add(septiembre);
                total_aprovechamientos=total_aprovechamientos.add(octubre);
                total_aprovechamientos=total_aprovechamientos.add(noviembre);
                total_aprovechamientos=total_aprovechamientos.add(diciembre);
            }
            if(contador>=limite_aprovechamiento && contador< limite_ing_ventas){//ingresos por venta de bienes y servicios
                bienes_serv_enero=bienes_serv_enero.add(enero);
                bienes_serv_febrero=bienes_serv_febrero.add(febrero);
                bienes_serv_marzo=bienes_serv_marzo.add(marzo);
                bienes_serv_abril=bienes_serv_abril.add(abril);
                bienes_serv_mayo=bienes_serv_mayo.add(mayo);
                bienes_serv_junio=bienes_serv_junio.add(junio);
                bienes_serv_julio=bienes_serv_julio.add(julio);
                bienes_serv_agosto=bienes_serv_agosto.add(agosto);
                bienes_serv_septiembre=bienes_serv_septiembre.add(septiembre);
                bienes_serv_octubre=bienes_serv_octubre.add(octubre);
                bienes_serv_noviembre=bienes_serv_noviembre.add(noviembre);
                bienes_serv_diciembre=bienes_serv_diciembre.add(diciembre);
                
                total_bienes_serv=total_bienes_serv.add(enero);
                total_bienes_serv=total_bienes_serv.add(febrero);
                total_bienes_serv=total_bienes_serv.add(marzo);
                total_bienes_serv=total_bienes_serv.add(abril);
                total_bienes_serv=total_bienes_serv.add(mayo);
                total_bienes_serv=total_bienes_serv.add(junio);
                total_bienes_serv=total_bienes_serv.add(julio);
                total_bienes_serv=total_bienes_serv.add(agosto);
                total_bienes_serv=total_bienes_serv.add(septiembre);
                total_bienes_serv=total_bienes_serv.add(octubre);
                total_bienes_serv=total_bienes_serv.add(noviembre);
                total_bienes_serv=total_bienes_serv.add(diciembre);
            }
            if(contador>=limite_ing_ventas && contador<limite_part_aport){//participaciones y aportaciones
                part_aport_enero=part_aport_enero.add(enero);
                part_aport_febrero=part_aport_febrero.add(febrero);
                part_aport_marzo=part_aport_marzo.add(marzo);
                part_aport_abril=part_aport_abril.add(abril);
                part_aport_mayo=part_aport_mayo.add(mayo);
                part_aport_junio=part_aport_junio.add(junio);
                part_aport_julio=part_aport_julio.add(julio);
                part_aport_agosto=part_aport_agosto.add(agosto);
                part_aport_septiembre=part_aport_septiembre.add(septiembre);
                part_aport_octubre=part_aport_octubre.add(octubre);
                part_aport_noviembre=part_aport_noviembre.add(noviembre);
                part_aport_diciembre=part_aport_diciembre.add(diciembre);    
                
                total_part_aport=total_part_aport.add(enero);
                total_part_aport=total_part_aport.add(febrero);
                total_part_aport=total_part_aport.add(marzo);
                total_part_aport=total_part_aport.add(abril);
                total_part_aport=total_part_aport.add(mayo);
                total_part_aport=total_part_aport.add(junio);
                total_part_aport=total_part_aport.add(julio);
                total_part_aport=total_part_aport.add(agosto);
                total_part_aport=total_part_aport.add(septiembre);
                total_part_aport=total_part_aport.add(octubre);
                total_part_aport=total_part_aport.add(noviembre);
                total_part_aport=total_part_aport.add(diciembre);
            }
            if(contador>=limite_part_aport && contador<limite_transferencias){//transferencias, asignaciones, subsidios y otras ayudas.
                ayuda_enero=ayuda_enero.add(enero);
                ayuda_febrero=ayuda_febrero.add(febrero);
                ayuda_marzo=ayuda_marzo.add(marzo);
                ayuda_abril=ayuda_abril.add(abril);
                ayuda_mayo=ayuda_mayo.add(mayo);
                ayuda_junio=ayuda_junio.add(junio);
                ayuda_julio=ayuda_julio.add(julio);
                ayuda_agosto=ayuda_agosto.add(agosto);
                ayuda_septiembre=ayuda_septiembre.add(septiembre);
                ayuda_octubre=ayuda_octubre.add(octubre);
                ayuda_noviembre=ayuda_noviembre.add(noviembre);
                ayuda_diciembre=ayuda_diciembre.add(diciembre);
                
                total_ayuda=total_ayuda.add(enero);
                total_ayuda=total_ayuda.add(febrero);
                total_ayuda=total_ayuda.add(marzo);
                total_ayuda=total_ayuda.add(abril);
                total_ayuda=total_ayuda.add(mayo);
                total_ayuda=total_ayuda.add(junio);
                total_ayuda=total_ayuda.add(julio);
                total_ayuda=total_ayuda.add(agosto);
                total_ayuda=total_ayuda.add(septiembre);
                total_ayuda=total_ayuda.add(octubre);
                total_ayuda=total_ayuda.add(noviembre);
                total_ayuda=total_ayuda.add(diciembre);
            }
            if(contador>=limite_transferencias && contador<limite_ingresos){//derivados
                derivados_enero=derivados_enero.add(enero);
                derivados_febrero=derivados_febrero.add(febrero);
                derivados_marzo=derivados_marzo.add(marzo);
                derivados_abril=derivados_abril.add(abril);
                derivados_mayo=derivados_mayo.add(mayo);
                derivados_junio=derivados_junio.add(junio);
                derivados_julio=derivados_julio.add(julio);
                derivados_agosto=derivados_agosto.add(agosto);
                derivados_septiembre=derivados_septiembre.add(septiembre);
                derivados_octubre=derivados_octubre.add(octubre);
                derivados_noviembre=derivados_noviembre.add(noviembre);
                derivados_diciembre=derivados_diciembre.add(diciembre);
                
                total_derivados=total_derivados.add(enero);
                total_derivados=total_derivados.add(febrero);
                total_derivados=total_derivados.add(marzo);
                total_derivados=total_derivados.add(abril);
                total_derivados=total_derivados.add(mayo);
                total_derivados=total_derivados.add(junio);
                total_derivados=total_derivados.add(julio);
                total_derivados=total_derivados.add(agosto);
                total_derivados=total_derivados.add(septiembre);
                total_derivados=total_derivados.add(octubre);
                total_derivados=total_derivados.add(noviembre);
                total_derivados=total_derivados.add(diciembre);
            }

            contador++;
        }
        
            subtotal_enero=subtotal_enero.add(impuestos_enero);
            subtotal_enero=subtotal_enero.add(derechos_enero);
            subtotal_enero=subtotal_enero.add(productos_enero);
            subtotal_enero=subtotal_enero.add(aprovechamientos_enero);
            subtotal_enero=subtotal_enero.add(bienes_serv_enero);
            subtotal_enero=subtotal_enero.add(part_aport_enero);
            subtotal_enero=subtotal_enero.add(ayuda_enero);
            subtotal_enero=subtotal_enero.add(derivados_enero);
            
            subtotal_febrero=subtotal_febrero.add(impuestos_febrero);
            subtotal_febrero=subtotal_febrero.add(derechos_febrero);
            subtotal_febrero=subtotal_febrero.add(productos_febrero);
            subtotal_febrero=subtotal_febrero.add(aprovechamientos_febrero);
            subtotal_febrero=subtotal_febrero.add(bienes_serv_febrero);
            subtotal_febrero=subtotal_febrero.add(part_aport_febrero);
            subtotal_febrero=subtotal_febrero.add(ayuda_febrero);
            subtotal_febrero=subtotal_febrero.add(derivados_febrero);
            
            subtotal_marzo=subtotal_marzo.add(impuestos_marzo);
            subtotal_marzo=subtotal_marzo.add(derechos_marzo);
            subtotal_marzo=subtotal_marzo.add(productos_marzo);
            subtotal_marzo=subtotal_marzo.add(aprovechamientos_marzo);
            subtotal_marzo=subtotal_marzo.add(bienes_serv_marzo);
            subtotal_marzo=subtotal_marzo.add(part_aport_marzo);
            subtotal_marzo=subtotal_marzo.add(ayuda_marzo);
            subtotal_marzo=subtotal_marzo.add(derivados_marzo);
            
            subtotal_abril=subtotal_abril.add(impuestos_abril);
            subtotal_abril=subtotal_abril.add(derechos_abril);
            subtotal_abril=subtotal_abril.add(productos_abril);
            subtotal_abril=subtotal_abril.add(aprovechamientos_abril);
            subtotal_abril=subtotal_abril.add(bienes_serv_abril);
            subtotal_abril=subtotal_abril.add(part_aport_abril);
            subtotal_abril=subtotal_abril.add(ayuda_abril);
            subtotal_abril=subtotal_abril.add(derivados_abril);
            
            subtotal_mayo=subtotal_mayo.add(impuestos_mayo);
            subtotal_mayo=subtotal_mayo.add(derechos_mayo);
            subtotal_mayo=subtotal_mayo.add(productos_mayo);
            subtotal_mayo=subtotal_mayo.add(aprovechamientos_mayo);
            subtotal_mayo=subtotal_mayo.add(bienes_serv_mayo);
            subtotal_mayo=subtotal_mayo.add(part_aport_mayo);
            subtotal_mayo=subtotal_mayo.add(ayuda_mayo);
            subtotal_mayo=subtotal_mayo.add(derivados_mayo);
            
            subtotal_junio=subtotal_junio.add(impuestos_junio);
            subtotal_junio=subtotal_junio.add(derechos_junio);
            subtotal_junio=subtotal_junio.add(productos_junio);
            subtotal_junio=subtotal_junio.add(aprovechamientos_junio);
            subtotal_junio=subtotal_junio.add(bienes_serv_junio);
            subtotal_junio=subtotal_junio.add(part_aport_junio);
            subtotal_junio=subtotal_junio.add(ayuda_junio);
            subtotal_junio=subtotal_junio.add(derivados_junio);
            
            subtotal_julio=subtotal_julio.add(impuestos_julio);
            subtotal_julio=subtotal_julio.add(derechos_julio);
            subtotal_julio=subtotal_julio.add(productos_julio);
            subtotal_julio=subtotal_julio.add(aprovechamientos_julio);
            subtotal_julio=subtotal_julio.add(bienes_serv_julio);
            subtotal_julio=subtotal_julio.add(part_aport_julio);
            subtotal_julio=subtotal_julio.add(ayuda_julio);
            subtotal_julio=subtotal_julio.add(derivados_julio);
            
            subtotal_agosto=subtotal_agosto.add(impuestos_agosto);
            subtotal_agosto=subtotal_agosto.add(derechos_agosto);
            subtotal_agosto=subtotal_agosto.add(productos_agosto);
            subtotal_agosto=subtotal_agosto.add(aprovechamientos_agosto);
            subtotal_agosto=subtotal_agosto.add(bienes_serv_agosto);
            subtotal_agosto=subtotal_agosto.add(part_aport_agosto);
            subtotal_agosto=subtotal_agosto.add(ayuda_agosto);
            subtotal_agosto=subtotal_agosto.add(derivados_agosto);
            
            subtotal_septiembre=subtotal_septiembre.add(impuestos_septiembre);
            subtotal_septiembre=subtotal_septiembre.add(derechos_septiembre);
            subtotal_septiembre=subtotal_septiembre.add(productos_septiembre);
            subtotal_septiembre=subtotal_septiembre.add(aprovechamientos_septiembre);
            subtotal_septiembre=subtotal_septiembre.add(bienes_serv_septiembre);
            subtotal_septiembre=subtotal_septiembre.add(part_aport_septiembre);
            subtotal_septiembre=subtotal_septiembre.add(ayuda_septiembre);
            subtotal_septiembre=subtotal_septiembre.add(derivados_septiembre);
            
            subtotal_octubre=subtotal_octubre.add(impuestos_octubre);
            subtotal_octubre=subtotal_octubre.add(derechos_octubre);
            subtotal_octubre=subtotal_octubre.add(productos_octubre);
            subtotal_octubre=subtotal_octubre.add(aprovechamientos_octubre);
            subtotal_octubre=subtotal_octubre.add(bienes_serv_octubre);
            subtotal_octubre=subtotal_octubre.add(part_aport_octubre);
            subtotal_octubre=subtotal_octubre.add(ayuda_octubre);
            subtotal_octubre=subtotal_octubre.add(derivados_octubre);
            
            subtotal_noviembre=subtotal_noviembre.add(impuestos_noviembre);
            subtotal_noviembre=subtotal_noviembre.add(derechos_noviembre);
            subtotal_noviembre=subtotal_noviembre.add(productos_noviembre);
            subtotal_noviembre=subtotal_noviembre.add(aprovechamientos_noviembre);
            subtotal_noviembre=subtotal_noviembre.add(bienes_serv_noviembre);
            subtotal_noviembre=subtotal_noviembre.add(part_aport_noviembre);
            subtotal_noviembre=subtotal_noviembre.add(ayuda_noviembre);
            subtotal_noviembre=subtotal_noviembre.add(derivados_noviembre);
            
            subtotal_diciembre=subtotal_diciembre.add(impuestos_diciembre);
            subtotal_diciembre=subtotal_diciembre.add(derechos_diciembre);
            subtotal_diciembre=subtotal_diciembre.add(productos_diciembre);
            subtotal_diciembre=subtotal_diciembre.add(aprovechamientos_diciembre);
            subtotal_diciembre=subtotal_diciembre.add(bienes_serv_diciembre);
            subtotal_diciembre=subtotal_diciembre.add(part_aport_diciembre);
            subtotal_diciembre=subtotal_diciembre.add(ayuda_diciembre);
            subtotal_diciembre=subtotal_diciembre.add(derivados_diciembre);
            
        total_total=total_total.add(total_aprovechamientos);
        total_total=total_total.add(total_ayuda);
        total_total=total_total.add(total_bienes_serv);
        total_total=total_total.add(total_derechos);
        total_total=total_total.add(total_impuestos);
        total_total=total_total.add(total_part_aport);
        total_total=total_total.add(total_productos);
        total_total=total_total.add(total_derivados);
        
        libroExcel[0] = "Ingresos";
        enero_impuesto.setText(impuestos_enero.toString());
        libroExcel[1] = impuestos_enero.toString();
        enero_derechos.setText(derechos_enero.toString());
        libroExcel[2] = derechos_enero.toString();
        enero_productos.setText(productos_enero.toString());
        libroExcel[3] = productos_enero.toString();
        enero_aprovechamientos.setText(productos_enero.toString());
        libroExcel[4] = productos_enero.toString();
        enero_ingresos.setText(bienes_serv_enero.toString());
        libroExcel[5] = bienes_serv_enero.toString();
        enero_participaciones.setText(part_aport_enero.toString());
        libroExcel[6] = part_aport_enero.toString();
        enero_ayuda.setText(ayuda_enero.toString());
        libroExcel[7] = ayuda_enero.toString();
        enero_deriv.setText(derivados_enero.toString());
        libroExcel[8] = derivados_enero.toString();
        enero_total.setText(subtotal_enero.toString());
        libroExcel[9] = subtotal_enero.toString();
        
        libroExcel[10] = "Ingresos";
        febrero_impuesto.setText(impuestos_febrero.toString());
        libroExcel[11] = impuestos_febrero.toString();
        febrero_derechos.setText(derechos_febrero.toString());
        libroExcel[12] = derechos_febrero.toString();
        febrero_productos.setText(productos_febrero.toString());
        libroExcel[13] = productos_febrero.toString();
        febrero_aprovechamientos.setText(aprovechamientos_febrero.toString());
        libroExcel[14] = aprovechamientos_febrero.toString();
        febrero_ingresos.setText(bienes_serv_febrero.toString());
        libroExcel[15] = bienes_serv_febrero.toString();
        febrero_participaciones.setText(part_aport_febrero.toString());
        libroExcel[16] = part_aport_febrero.toString();
        febrero_ayuda.setText(ayuda_febrero.toString());
        libroExcel[17] = ayuda_febrero.toString();
        febrero_deriv.setText(derivados_febrero.toString());
        libroExcel[18] = derivados_febrero.toString();
        febrero_total.setText(subtotal_febrero.toString());
        libroExcel[19] = subtotal_febrero.toString();
        
        libroExcel[20] = "Ingresos";
        marzo_impuesto.setText(impuestos_marzo.toString());
        libroExcel[21] = impuestos_marzo.toString();
        marzo_derechos.setText(derechos_marzo.toString());
        libroExcel[22] = derechos_marzo.toString();
        marzo_productos.setText(productos_marzo.toString());
        libroExcel[23] = productos_marzo.toString();
        marzo_aprovechamientos.setText(aprovechamientos_marzo.toString());
        libroExcel[24] = aprovechamientos_marzo.toString();
        marzo_ingresos.setText(bienes_serv_marzo.toString());
        libroExcel[25] = bienes_serv_marzo.toString();
        marzo_participaciones.setText(part_aport_marzo.toString());
        libroExcel[26] = part_aport_marzo.toString();
        marzo_ayuda.setText(ayuda_marzo.toString());
        libroExcel[27] = ayuda_marzo.toString();
        marzo_deriv.setText(derivados_marzo.toString());
        libroExcel[28] = derivados_marzo.toString();
        marzo_total.setText(subtotal_marzo.toString());
        libroExcel[29] = subtotal_marzo.toString();
        
        libroExcel[30] = "Ingresos";
        abril_impuesto.setText(impuestos_abril.toString());
        libroExcel[31] = impuestos_abril.toString();
        abril_derechos.setText(derechos_abril.toString());
        libroExcel[32] = derechos_abril.toString();
        abril_productos.setText(productos_abril.toString());
        libroExcel[33] = productos_abril.toString();
        abril_aprovechamientos.setText(aprovechamientos_abril.toString());
        libroExcel[34] = aprovechamientos_abril.toString();
        abril_ingresos.setText(bienes_serv_abril.toString());
        libroExcel[35] = bienes_serv_abril.toString();
        abril_participaciones.setText(part_aport_abril.toString());
        libroExcel[36] = part_aport_abril.toString();
        abril_ayuda.setText(ayuda_abril.toString());
        libroExcel[37] = ayuda_abril.toString();
        abril_deriv.setText(derivados_abril.toString());
        libroExcel[38] = derivados_abril.toString();
        abril_total.setText(subtotal_abril.toString());
        libroExcel[39] = subtotal_abril.toString();
        
        libroExcel[40] = "Ingresos";
        mayo_impuesto.setText(impuestos_mayo.toString());
        libroExcel[41] = impuestos_mayo.toString();
        mayo_derechos.setText(derechos_mayo.toString());
        libroExcel[42] = derechos_mayo.toString();
        mayo_productos.setText(productos_mayo.toString());
        libroExcel[43] = productos_mayo.toString();
        mayo_aprovechamientos.setText(aprovechamientos_mayo.toString());
        libroExcel[44] = aprovechamientos_mayo.toString();
        mayo_ingresos.setText(bienes_serv_mayo.toString());
        libroExcel[45] = bienes_serv_mayo.toString();
        mayo_participaciones.setText(part_aport_mayo.toString());
        libroExcel[46] = part_aport_mayo.toString();
        mayo_ayuda.setText(ayuda_mayo.toString());
        libroExcel[47] = ayuda_mayo.toString();
        mayo_deriv.setText(derivados_mayo.toString());
        libroExcel[48] = derivados_mayo.toString();
        mayo_total.setText(subtotal_mayo.toString());
        libroExcel[49] = subtotal_mayo.toString();
        
        libroExcel[50] = "Ingresos";
        junio_impuesto.setText(impuestos_junio.toString());
        libroExcel[51] = impuestos_junio.toString();
        junio_derechos.setText(derechos_junio.toString());
        libroExcel[52] = derechos_junio.toString();
        junio_productos.setText(productos_junio.toString());
        libroExcel[53] = productos_junio.toString();
        junio_aprovechamientos.setText(aprovechamientos_junio.toString());
        libroExcel[54] = aprovechamientos_junio.toString();
        junio_ingresos.setText(bienes_serv_junio.toString());
        libroExcel[55] = bienes_serv_junio.toString();
        junio_participaciones.setText(part_aport_junio.toString());
        libroExcel[56] = part_aport_junio.toString();
        junio_ayuda.setText(ayuda_junio.toString());
        libroExcel[57] = ayuda_junio.toString();
        junio_deriv.setText(derivados_junio.toString());
        libroExcel[58] = derivados_junio.toString();
        junio_total.setText(subtotal_junio.toString());
        libroExcel[59] = subtotal_junio.toString();
        
        libroExcel[60] = "Ingresos";
        julio_impuesto.setText(impuestos_julio.toString());
        libroExcel[61] = impuestos_julio.toString();
        julio_derechos.setText(derechos_julio.toString());
        libroExcel[62] = derechos_julio.toString();
        julio_productos.setText(productos_julio.toString());
        libroExcel[63] = productos_julio.toString();
        julio_aprovechamientos.setText(aprovechamientos_julio.toString());
        libroExcel[64] = aprovechamientos_julio.toString();
        julio_ingresos.setText(bienes_serv_julio.toString());
        libroExcel[65] = bienes_serv_julio.toString();
        julio_participaciones.setText(part_aport_julio.toString());
        libroExcel[66] = part_aport_julio.toString();
        julio_ayuda.setText(ayuda_julio.toString());
        libroExcel[67] = ayuda_julio.toString();
        julio_deriv.setText(derivados_julio.toString());
        libroExcel[68] = derivados_julio.toString();
        julio_total.setText(subtotal_julio.toString());
        libroExcel[69] = subtotal_julio.toString();
        
        libroExcel[70] = "Ingresos";
        agosto_impuesto.setText(impuestos_agosto.toString());
        libroExcel[71] = impuestos_agosto.toString();
        agosto_derechos.setText(derechos_agosto.toString());
        libroExcel[72] = derechos_agosto.toString();
        agosto_productos.setText(productos_agosto.toString());
        libroExcel[73] = productos_agosto.toString();
        agosto_aprovechamientos.setText(aprovechamientos_agosto.toString());
        libroExcel[74] = aprovechamientos_agosto.toString();
        agosto_ingresos.setText(bienes_serv_agosto.toString());
        libroExcel[75] = bienes_serv_agosto.toString();
        agosto_participaciones.setText(part_aport_agosto.toString());
        libroExcel[76] = part_aport_agosto.toString();
        agosto_ayuda.setText(ayuda_agosto.toString());
        libroExcel[77] = ayuda_agosto.toString();
        agosto_deriv.setText(derivados_agosto.toString());
        libroExcel[78] = derivados_agosto.toString();
        agosto_total.setText(subtotal_agosto.toString());
        libroExcel[79] = subtotal_agosto.toString();
        
        libroExcel[80] = "Ingresos";
        septiembre_impuesto.setText(impuestos_septiembre.toString());
        libroExcel[81] = impuestos_septiembre.toString();
        septiembre_derechos.setText(derechos_septiembre.toString());
        libroExcel[82] = derechos_septiembre.toString();
        septiembre_productos.setText(productos_septiembre.toString());
        libroExcel[83] = productos_septiembre.toString();
        septiembre_aprovechamientos.setText(aprovechamientos_septiembre.toString());
        libroExcel[84] = aprovechamientos_septiembre.toString();
        septiembre_ingresos.setText(bienes_serv_septiembre.toString());
        libroExcel[85] = bienes_serv_septiembre.toString();
        septiembre_participaciones.setText(part_aport_septiembre.toString());
        libroExcel[86] = part_aport_septiembre.toString();
        septiembre_ayuda.setText(ayuda_septiembre.toString());
        libroExcel[87] = ayuda_septiembre.toString();
        septiembre_deriv.setText(derivados_septiembre.toString());
        libroExcel[88] = derivados_septiembre.toString();
        septiembre_total.setText(subtotal_septiembre.toString());
        libroExcel[89] = subtotal_septiembre.toString();
        
        libroExcel[90] = "Ingresos";
        octubre_impuesto.setText(impuestos_octubre.toString());
        libroExcel[91] = impuestos_octubre.toString();
        octubre_derechos.setText(derechos_octubre.toString());
        libroExcel[92] = derechos_octubre.toString();
        octubre_productos.setText(productos_octubre.toString());
        libroExcel[93] = productos_octubre.toString();
        octubre_aprovechamientos.setText(aprovechamientos_octubre.toString());
        libroExcel[94] = aprovechamientos_octubre.toString();
        octubre_ingresos.setText(bienes_serv_octubre.toString());
        libroExcel[95] = bienes_serv_octubre.toString();
        octubre_participaciones.setText(part_aport_octubre.toString());
        libroExcel[96] = part_aport_octubre.toString();
        octubre_ayuda.setText(ayuda_octubre.toString());
        libroExcel[97] = ayuda_octubre.toString();
        octubre_deriv.setText(derivados_octubre.toString());
        libroExcel[98] = derivados_octubre.toString();
        octubre_total.setText(subtotal_octubre.toString());
        libroExcel[99] = subtotal_octubre.toString();
        
        libroExcel[100] = "Ingresos";
        noviembre_impuesto.setText(impuestos_noviembre.toString());
        libroExcel[101] = impuestos_noviembre.toString();
        noviembre_derechos.setText(derechos_noviembre.toString());
        libroExcel[102] = derechos_noviembre.toString();
        noviembre_productos.setText(productos_noviembre.toString());
        libroExcel[103] = productos_noviembre.toString();
        noviembre_aprovechamientos.setText(aprovechamientos_noviembre.toString());
        libroExcel[104] = aprovechamientos_noviembre.toString();
        noviembre_ingresos.setText(bienes_serv_noviembre.toString());
        libroExcel[105] = bienes_serv_noviembre.toString();
        noviembre_participaciones.setText(part_aport_noviembre.toString());
        libroExcel[106] = part_aport_noviembre.toString();
        noviembre_ayuda.setText(ayuda_noviembre.toString());
        libroExcel[107] = ayuda_noviembre.toString();
        noviembre_deriv.setText(derivados_noviembre.toString());
        libroExcel[108] = derivados_noviembre.toString();
        noviembre_total.setText(subtotal_noviembre.toString());
        libroExcel[109] = subtotal_noviembre.toString();
        
        libroExcel[110] = "Ingresos";
        diciembre_impuesto.setText(impuestos_diciembre.toString());
        libroExcel[111] = impuestos_diciembre.toString();
        diciembre_derechos.setText(derechos_diciembre.toString());
        libroExcel[112] = derechos_diciembre.toString();
        diciembre_productos.setText(productos_diciembre.toString());
        libroExcel[113] = productos_diciembre.toString();
        diciembre_aprovechamientos.setText(aprovechamientos_diciembre.toString());
        libroExcel[114] = aprovechamientos_diciembre.toString();
        diciembre_ingresos.setText(bienes_serv_diciembre.toString());
        libroExcel[115] = bienes_serv_diciembre.toString();
        diciembre_participaciones.setText(part_aport_diciembre.toString());
        libroExcel[116] = part_aport_diciembre.toString();
        diciembre_ayuda.setText(ayuda_diciembre.toString());
        libroExcel[117] = ayuda_diciembre.toString();
        diciembre_deriv.setText(derivados_diciembre.toString());
        libroExcel[118] = derivados_diciembre.toString();
        diciembre_total.setText(subtotal_diciembre.toString());
        libroExcel[119] = subtotal_diciembre.toString();
        
        libroExcel[120] = "Ingresos";
        totalizado_impuesto.setText(total_impuestos.toString());
        libroExcel[121] = total_impuestos.toString();
        totalizado_derechos.setText(total_derechos.toString());
        libroExcel[122] = total_derechos.toString();
        totalizado_productos.setText(total_productos.toString());
        libroExcel[123] = total_productos.toString();
        totalizado_aprovechamientos.setText(total_aprovechamientos.toString());
        libroExcel[124] = total_aprovechamientos.toString();
        totalizado_ingresos.setText(total_bienes_serv.toString());
        libroExcel[125] = total_bienes_serv.toString();
        totalizado_participaciones.setText(total_part_aport.toString());
        libroExcel[126] = total_part_aport.toString();
        totalizado_ayuda.setText(total_ayuda.toString());
        libroExcel[127] = total_ayuda.toString();
        totalizado_deriv.setText(total_derivados.toString());
        libroExcel[128] = total_derivados.toString();
        totalizado_total.setText(total_total.toString());
        libroExcel[129] = total_total.toString();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conn = cc.getConnection(ClaseConectora.ip_base_de_datos);
        } catch (IOException ex) {
        }
    }
    
}
