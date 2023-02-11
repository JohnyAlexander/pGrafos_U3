package Controlador.Ciudades;

import Controlador.Grafos.GrafoNoDirijidoEtiquetado;
import Modelo.Ciudades;
import Modelo.TamanioCiudades;
import Modelo.Ubicacion;

/**
 *
 * @author johnny
 */
public class PersonaGrafoController {
    
    private GrafoNoDirijidoEtiquetado<Ciudades> grafoFin;
    private Ciudades ciudad;


    public PersonaGrafoController(Integer nro_vertices) {
        grafoFin= new GrafoNoDirijidoEtiquetado<>(nro_vertices, Ciudades.class);
        for(int i = 1; i <= nro_vertices; i++) {
            Ciudades city = new Ciudades();
            city.setId(i);
            city.setCityName("Persona "+i);
            city.setTaCity(TamanioCiudades.GRANDES);
            Ubicacion location = new Ubicacion();
            location.setId(i);
            location.setLatitud(0.0);
            location.setLongitud(0.0);
            city.setUbicacion(location);
            grafoFin.etiquetarVertice(i, city);
        }
    }

    public GrafoNoDirijidoEtiquetado<Ciudades> getGrafoEND() {
        return grafoFin;
    }

    public void setGrafoEND(GrafoNoDirijidoEtiquetado<Ciudades> grafoFin) {
        this.grafoFin = grafoFin;
    }

    public Ciudades getPersona() {
        if (ciudad==null) {
            ciudad = new Ciudades();           
        }
        return ciudad;
    }

    public void setPersona(Ciudades ciudad) {
        this.ciudad = ciudad;
    }
    
    
    public Double calcularDistancia(Ciudades CyO, Ciudades pd) {
        Double dis = 0.0;
        Double x = CyO.getUbicacion().getLongitud() - pd.getUbicacion().getLongitud();
        Double y = CyO.getUbicacion().getLatitud() - pd.getUbicacion().getLatitud();
        dis = Math.sqrt((x*x) + (y*y));
        return dis;
    }
    
}
