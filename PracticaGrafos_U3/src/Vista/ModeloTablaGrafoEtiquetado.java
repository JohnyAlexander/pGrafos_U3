package Vista;

import Controlador.Exceptions.VerticeOfSizeException;
import Controlador.Grafos.GrafoDirigidoEtiquetado;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hilar_c9usj1g
 */
public class ModeloTablaGrafoEtiquetado extends AbstractTableModel {

    private GrafoDirigidoEtiquetado<String> grafoED;
    private String[] columnas;

    public GrafoDirigidoEtiquetado getGrafoED() {
        return grafoED;
    }

    public void setGrafoED(GrafoDirigidoEtiquetado grafoED) {
        this.grafoED = grafoED;
        this.generarColumna();
    }

    public String[] getColumnas() {
        return columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    @Override
    public int getRowCount() {
        return grafoED.numVertices();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

   @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        if (arg1==0) {
            return columnas[arg0+1];
        }else{
            try {
                Object [] aux = grafoED.existeArista((arg0+1), arg1);
                if ((Boolean) aux[0]) {
                    return aux[1];
                }else{
                    return "----------";
                }
            } catch (VerticeOfSizeException ex) {
                System.out.println("ERROR EN LA TABLA " + ex);
                return "";
            }
        }
    }
        private String [] generarColumna(){
        columnas = new String [grafoED.numVertices()+1];
        columnas[0] = "/";
        for (int i = 0; i < columnas.length; i++) {
            columnas[i] = String.valueOf(i);
        }
        return columnas;
    }
}
