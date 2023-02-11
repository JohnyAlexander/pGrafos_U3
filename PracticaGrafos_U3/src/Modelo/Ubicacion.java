package Modelo;

/**
 *
 * @author Johnny
 */
public class Ubicacion {
    
    private Integer id;
    private Double longitud;
    private Double latitud;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return "Longitud=" + longitud + ", Latitud=" + latitud + '}';
    }
    
    
    
}
