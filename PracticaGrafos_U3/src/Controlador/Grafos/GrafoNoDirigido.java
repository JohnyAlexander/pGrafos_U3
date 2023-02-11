package Controlador.Grafos;

import Controlador.Exceptions.VerticeOfSizeException;

/**
 *
 * @author johnny
 */
public class GrafoNoDirigido extends GrafoDirigido{

    public GrafoNoDirigido(Integer numV) {
        super(numV);
    }
    
    @Override
    public void insertarArista(Integer i, Integer j, Double peso) throws VerticeOfSizeException {
        if (i > 0 && j > 0 &&i <= numV && j <= numV) {
            Object[] existe = existeArista(i, j);
            if (!((Boolean) existe[0])) {
                numA++;
                listaAdyacente[i].insertarCabecera(new Adycencia(j, peso));
                listaAdyacente[j].insertarCabecera(new Adycencia(i, peso));
            }
        } else {
            throw new VerticeOfSizeException("Algun vertice ingresado no existe");
        }
    }
}
