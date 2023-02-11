package Modelo;

/**
 *
 * @author Johnny
 */
public class Ciudades {

    private Integer id;
    private String CityName;
    private TamanioCiudades TaCity;
    private Ubicacion ubicacion;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the CityName
     */
    public String getCityName() {
        return CityName;
    }

    /**
     * @param CityName the CityName to set
     */
    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    /**
     * @return the TaCity
     */
    public TamanioCiudades getTaCity() {
        return TaCity;
    }

    /**
     * @param TaCity the TaCity to set
     */
    public void setTaCity(TamanioCiudades TaCity) {
        this.TaCity = TaCity;
    }

    /**
     * @return the ubicacion
     */
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return getCityName();
    }

}
