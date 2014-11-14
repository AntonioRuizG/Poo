import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * Clase abstracta Animable, representa objetos que se puden dibujar y
 * mover por la pantalla.
 * 
 * @author Antonio Ruiz Gutierrez
 * @version 1.0.0
 */
public abstract class Animable
{
    //Area de la pantalla
    private static Dimension areaDePantalla;
    
    //Indica si el objeto es visible en la pantalla
    private boolean visible;
    
    //La coordenada x de la posicion del objeto
    private double x;
    
    //La coordenada y de la posicion del objeto
    private double y;
    
    //Velocidad de movimiento en el eje x
    private double velocidadX;
    
    //Velocidad de movimiento en el eje y
    private double velocidadY;
    
    
    
    /**
     * Construye un objeto Animable.
     * @param x La coordenada x de la posicion del objeto.
     * @param y La coordenada y de la posicion del objeto.
     * @throws IllegalStateException si se intenta instanciar un objeto animable
     * antes de inicializar el area de pantalla.
     * @see setAreaDePantalla
     */
    public Animable(double x, double y)
    {
        //comprueba que el area de pantalla esta inicializada
        if (areaDePantalla==null)
            throw new IllegalStateException("El area de pantalla no esta inicializada"
                                +". Debe llamar a Animable.setAreaDePantalla()");
        
        visible = true;
        this.x = x;
        this.y = y;
        velocidadX = 0;
        velocidadY = 0;
    }
    
    /**
     * Dibuja el objeto en el contexto grafico especificado como parametro.
     * @param g El contexto grafico donde pintar.
     */
    public abstract void dibujar(Graphics2D g);
    
    /**
     * Establece la visiblidad del objeto.
     * @param valor Valor a establecer.
     */
    public void setVisible(boolean valor)
    {
        visible = valor;
    }
    
    /**
     * @return true si el objeto es visible, false en caso contrario
     */
    public boolean esVisible()
    {
        return visible;
    }
    
    /**
     * Establece la velocidad de movimiento del objeto en el eje x.
     * @param dx La nueva velocidad.
     * @see mover
     */
    public void setVelocidadX(double dx)
    {
        velocidadX = dx;
    }
    
    /**
     * Establece la velocidad de movimiento del objeto en el eje y.
     * @param dy La nueva velocidad.
     * @see mover
     */
    public void setVelocidadY(double dy)
    {
        velocidadY = dy;
    }
    
    /**
     * @return La velocidad de movimiento del objeto en el eje x.
     * @see mover
     */
    public double getVelocidadX()
    {
        return velocidadX;
    }
    
    /**
     * @return La velocidad de movimiento del objeto en el eje y.
     * @see mover
     */
    public double getVelocidadY()
    {
        return velocidadY;
    }
    
    /**
     * @return la coordenada x del objeto.
     */
    public double getX()
    {
        return x;
    }
    
    /**
     * @return la coordenada y del objeto.
     */
    public double getY()
    {
        return y;
    }
    
    /**
     * Establece la coordenada x del objeto
     * @param x La coordenada x.
     */
    public void setX(double x)
    {
        this.x = x;
    }
    
    /**
     * Establece la coordenada y del objeto
     * @param y La coordenada y.
     */
    public void setY(double y)
    {
        this.y = y;
    }
    
    /**
     * Mueve el objeto el valor de la velocidad.
     * @see setVelocidadX, setVelocidadY, getVelocidadX, getVelocidadY
     */
    public void mover()
    {
        x += velocidadX;
        y += velocidadY;
    }
    
    /**
     * Establece el area de pantalla por donde se moveran los objetos Animables.
     * @param dimension Un objeto dimension que especifica el area de pantallla.
     */
    public static void setAreaDePantalla(Dimension dimension)
    {
        areaDePantalla = dimension;
    }
    
    /**
     * @return Un objeto dimension que especifica el area de pantalla.
     */
    public static Dimension getAreaDePantalla()
    {
        return areaDePantalla;
    }
}
