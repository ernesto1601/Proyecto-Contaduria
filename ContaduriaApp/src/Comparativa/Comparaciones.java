/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparativa;

import conectividad.ClaseConectora;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ernestosandovalbecerra
 */
public class Comparaciones {
    
    ClaseConectora cc = new ClaseConectora();
    Connection conn;
    Statement st;
    String [][] tabla = null;
    int count = 1;
    
    String cuenta_actual ="";
    String sub_cuenta_actual = "";
    String sub2_cuenta_actual = "";
    
    
    public String[][] evolucion(int ano) throws SQLException{
        
        try {
            conn = cc.getConnection(ClaseConectora.ip_base_de_datos);
            st = conn.createStatement();
        } catch (Exception ex) {
        }
        
        ArrayList<String> cuentas = obtenerCuentas("cuenta");
        ArrayList<String> sub_cuentas = obtenerCuentas("sub_cuenta");
        ArrayList<String> sub2_cuentas = obtenerCuentas("sub2_cuenta");
        
        //NO ENTIENDO POR QUE HAY QUE SUMAR 10 :'V
        int filas = (cuentas.size() + sub_cuentas.size() + sub2_cuentas.size() + numFilas(ano))+10;
        System.out.println("filas: "+filas);
        tabla = new String[filas][13];
        
        
        String[] cabecera = {"Concepto","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre",
        "Octubre", "Noviembre","Diciembre"};
        
        for(int i=0; i < cabecera.length ; i++){
            tabla[0][i] = cabecera[i];
        }
        /*
            - P1: Selecciono la cuenta de indice i (hecho)
            - P2: Obtengo la suma de esa cuenta por cada mes (hecho)
            - P3: Obtengo las sub_cuentas pertenecientes  a la cuenta de indice i (hecho)
            - P4: Obtengo la suma de la sub_cuenta de indice i por cada mes (hecho)
            - P5: Obtengo las sub2_cuentas pertenecientes a la sub_cuenta de indice i (hecho)
            - P6: Obtengo la suma de la sub2_cuenta de indice i por cada mes (hecho)
            - P7: Agrego el select de concepto que devuelva las 3 condiciones (hecho)
        */
            //System.out.println("AH POR LOS FOR");
            for(int i = 0; i < cuentas.size(); i++){//CONTROL DE LAS CUENTAS
                cuenta_actual = cuentas.get(i);//P1
                tabla[count][i] = cuenta_actual;
                sumasCuentasMes(cuenta_actual, ano);//P2
                String[] subCuentas_de_cuentaActual = obtenerSubCuentas_Cuenta(cuenta_actual);//P3
                System.out.println("cuenta actual: "+cuenta_actual);
                
                for(int x=0; x < subCuentas_de_cuentaActual.length; x++){//CONTROL DE LAS SUB_CUENTAS
                    sub_cuenta_actual = subCuentas_de_cuentaActual[x];
                    tabla[count][i] = sub_cuenta_actual;
                    sumasSubCuentasMes(sub_cuenta_actual, ano);//P4
                    String[] sub2Cuentas_de_subCuentaActual = obtenerSub2Cuentas_subCuenta(sub_cuenta_actual);//P5
                    System.out.println("sub cuenta actual: "+sub_cuenta_actual);
                    
                    for(int y = 0; y < sub2Cuentas_de_subCuentaActual.length; y++){//CONTROL DE LAS SUB2_CUENTAS
                        sub2_cuenta_actual = sub2Cuentas_de_subCuentaActual[y];
                        tabla[count][i] = sub2_cuenta_actual;
                        sumasSub2CuentasMes(sub2_cuenta_actual, ano);//P6
                        rellenarConceptos(ano);//P7
                        System.out.println("sub2 cuenta actual: "+sub2_cuenta_actual);
                    }
                    
                }
                
            }
        System.out.println("TERMINO TODO");
        return tabla;
    }
    
    public void rellenarConceptos(int anno) throws SQLException{
        String sql = "SELECT concepto,enero,febrero,marzo,abril,mayo,junio,julio,agosto,septiembre,octubre,noviembre,diciembre FROM presupuestos.valores WHERE cuenta=\""+cuenta_actual+"\" AND "
                + "sub_cuenta=\""+sub_cuenta_actual+"\" AND sub2_cuenta=\""+sub2_cuenta_actual+"\""+" AND anno="+anno+";";
        System.out.println(sql);
        ResultSet resultSet = st.executeQuery(sql);
        int counter=1;
        while(resultSet.next()){
            tabla[count][0] = resultSet.getString(1);
            tabla[count][1] = resultSet.getString(2);
            tabla[count][2] = resultSet.getString(3);
            tabla[count][3] = resultSet.getString(4);
            tabla[count][4] = resultSet.getString(5);
            tabla[count][5] = resultSet.getString(6);
            tabla[count][6] = resultSet.getString(7);
            tabla[count][7] = resultSet.getString(8);
            tabla[count][8] = resultSet.getString(9);
            tabla[count][9] = resultSet.getString(10);
            tabla[count][10] = resultSet.getString(11);
            tabla[count][11] = resultSet.getString(12);
            tabla[count][12] = resultSet.getString(13);
            counter++;
            count++;
            System.out.println("fila: "+count);
        }
        System.out.println("RETORNO FINAL");
    }
    
    private void sumasSub2CuentasMes(String sub2_cuenta_actual, int anno) throws SQLException{
        
        String[] meses = {"enero","febrero","marzo","abril","mayo","junio","julio",
            "agosto","septiembre","octubre","noviembre","diciembre"};
        ResultSet resultSet = null;
        String sql = "";
        for(int i=1; i <= meses.length; i++){
            sql = "SELECT SUM("+meses[i-1]+") FROM presupuestos.valores WHERE anno="+anno+" AND sub2_cuenta=\""+sub2_cuenta_actual+"\";";
            //System.out.println(sql);
            resultSet = st.executeQuery(sql);
            resultSet.next();
            tabla[count][i] = resultSet.getString(1);
        }
        //System.out.println(sql);
        count++;
    }
    
    private String[] obtenerSub2Cuentas_subCuenta(String sub_cuenta) throws SQLException{
        String[] resultados= null;
        ResultSet resultSet = null;
        String sql_count = "SELECT  COUNT(op.sub2_cuenta) FROM (SELECT  DISTINCT sub2_cuenta FROM presupuestos.valores WHERE sub_cuenta=\""+sub_cuenta+"\") AS op;";
        //System.out.println(sql_count);
        resultSet = st.executeQuery(sql_count);
        resultSet.next();
        int tamano = Integer.parseInt(resultSet.getString(1));
        resultados = new String[tamano];
        
        String sql = "SELECT DISTINCT sub2_cuenta FROM presupuestos.valores WHERE sub_cuenta = \""+sub_cuenta+"\";";
        //System.out.println(sql);
        resultSet = st.executeQuery(sql);
        
        int index = 0;
        if(tamano==1){
            System.out.println("solo hay 1");
            resultSet.next();
            resultados[index] = resultSet.getString(1);
        }else{
            while(resultSet.next()){

                resultados[index] = resultSet.getString(1);
                index++;
            }
        }
            //System.out.println("DE REGRESO PAPU");
        return resultados;
    }
    
    private void sumasSubCuentasMes(String sub_cuenta_actual, int anno) throws SQLException{
        
        String[] meses = {"enero","febrero","marzo","abril","mayo","junio","julio",
            "agosto","septiembre","octubre","noviembre","diciembre"};
        ResultSet resultSet = null;
        String sql = "";
        for(int i=1; i <= meses.length; i++){
            sql = "SELECT SUM("+meses[i-1]+") FROM presupuestos.valores WHERE anno="+anno+" AND sub_cuenta=\""+sub_cuenta_actual+"\";";
            //System.out.println(sql);
            resultSet = st.executeQuery(sql);
            resultSet.next();
            tabla[count][i] = resultSet.getString(1);
        }
        //System.out.println(sql);
        count++;
    }
    
    //Obtiene las sub_cuentas que derivan de la cuenta del parámetro
    private String[] obtenerSubCuentas_Cuenta(String cuenta) throws SQLException{
        
        String[] resultados= null;
        ResultSet resultSet = null;
        String sql_count = "SELECT  COUNT(op.sub_cuenta) FROM (SELECT  DISTINCT sub_cuenta FROM presupuestos.valores WHERE cuenta=\""+cuenta+"\") AS op;";
        //System.out.println(sql_count);
        resultSet = st.executeQuery(sql_count);
        resultSet.next();
        resultados = new String[Integer.parseInt(resultSet.getString(1))];
        
        String sql = "SELECT DISTINCT sub_cuenta FROM presupuestos.valores WHERE cuenta = \""+cuenta+"\";";
        //System.out.println(sql);
        resultSet = st.executeQuery(sql);
        
        int index = 0;
        while(resultSet.next()){
            
            resultados[index] = resultSet.getString(1);
            index++;
            //System.out.println(resultSet.getString(1));
        }
        return resultados;
    }
    
   //Realiza la suma de cada uno de los meses de la cuenta que se pasa por parámetro y los introduce en la tabla
    private void sumasCuentasMes(String cuenta_actual, int anno) throws SQLException{
        
        String[] meses = {"enero","febrero","marzo","abril","mayo","junio","julio",
            "agosto","septiembre","octubre","noviembre","diciembre"};
        ResultSet resultSet = null;
        String sql = "";
        for(int i=1; i <= meses.length; i++){
            sql = "SELECT SUM("+meses[i-1]+") FROM presupuestos.valores WHERE anno="+anno+" AND cuenta=\""+cuenta_actual+"\";";
            //System.out.println(sql);
            resultSet = st.executeQuery(sql);
            resultSet.next();
            tabla[count][i] = resultSet.getString(1);
        }
        //System.out.println(sql);
        count++;
    }
    
    //Obtiene el número de conceptos de la tabla
    private int numFilas(int anno) throws SQLException{
        String sql = "SELECT COUNT(enero) FROM presupuestos.valores WHERE anno="+anno+";";
        //System.out.println(sql);
        ResultSet resultSet = st.executeQuery(sql);
        resultSet.next();
        System.out.println("tamano de num Filas: "+resultSet.getString(1));
        return Integer.parseInt(resultSet.getString(1));
    }
    
    public ArrayList<String> obtenerCuentas(String concepto) throws SQLException{
               
        String sql = "SELECT DISTINCT "+concepto+" FROM presupuestos.valores;";
        System.out.println(sql);
        ResultSet resultSet = st.executeQuery(sql);
        ArrayList <String> cuentas = new ArrayList<String>();
        
        while(resultSet.next()){
            String valor = resultSet.getString(1);
            //System.out.println(valor);
            cuentas.add(valor);
            
        }
        System.out.println(concepto+" TAMANO: "+cuentas.size());
        return cuentas;
    }

}
