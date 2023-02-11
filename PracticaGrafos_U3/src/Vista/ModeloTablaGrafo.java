package Vista;

import Controlador.Exceptions.VerticeOfSizeException;
import Controlador.Grafos.GrafoDirigido;
import Controlador.Grafos.GrafoDirigidoEtiquetado;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hilar_c9usj1g
 */
public class ModeloTablaGrafo extends AbstractTableModel{
    
    private GrafoDirigidoEtiquetado grafoDE;
    private String[] columnas;

    private String [] generarColumna() throws Exception{
        columnas = new String [grafoDE.numVertices()+1];
        columnas[0] = "/";
        for (int i = 0; i < columnas.length; i++) {
            columnas[i] = String.valueOf(grafoDE.obtenerEtiqueta(i));
        }
        return columnas;
    }

    public GrafoDirigido getGrafoD() {
        return grafoDE;
    }

    public void setGrafoED(GrafoDirigidoEtiquetado grafoD) throws Exception {
        this.grafoDE = grafoD;
        generarColumna();
    }
    
    @Override
    public int getRowCount() {
        return grafoDE.numVertices();
    }

    @Override
    public int getColumnCount() {
        return grafoDE.numVertices()+1;
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
                Object [] aux = grafoDE.existeArista((arg0+1), arg1);
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
    
}
