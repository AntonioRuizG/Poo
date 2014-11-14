
/**
 * Enumerado ModoDeJuego.
 * Enumera los modos de juego disponibles.
 * 
 * @author Antonio Ruiz Gutierrez
 * @version 1.0.0
 */
public enum ModoDeJuego
{
    FACIL(10,10,"Facil"),NORMAL(12,15,"Normal"),
    COMPLICADO(16,20,"Complicado"),IMPOSIBLE(29,30,"Imposible");
    
    public int velocidadNaves;
    public int cantidadNaves;
    public String string;
    
    ModoDeJuego(int velocidadNaves, int cantidadNaves, String string)
    {
        this.velocidadNaves = velocidadNaves;
        this.cantidadNaves = cantidadNaves;
        this.string = string;
    }
    
    /**
     * @return Una cadena de texto representativa del modo de juego
     */
    public String toString()
    {
        return string;
    }

}
