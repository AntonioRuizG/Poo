import java.awt.*;
import java.awt.geom.*;

/**
 * Representa un disparo de una nave del juego.
 * 
 * @author Antonio Ruiz Gutierrez 
 * @version 1.0.0
 */
public class Misil extends Colisionable
{
    //los misiles desaparecen al superar este limite
    private static int limiteDerecho = getAreaDePantalla().width;

    /**
     * Constructor de objetos de clase Misil
     * @param x La coordenada x
     * @param y La coordenada y
     */
    public Misil(double x, double y)
    {
        super(x,y);
        setVelocidadX(20);
    }
    
    /**
     * Dibuja el misil en el contexto grafico
     * @param g El contexto grafico
     */
    public void dibujar(Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.fill(new Ellipse2D.Double(getX(),getY(),20,6));
    }
    
    /**
     * Mueve el misil, si el misil alcanza el limite de la pantalla, desaparece.
     */
    public void mover()
    {
        super.mover();
        if (getX() > limiteDerecho)
            setVisible(false);
    }
    
    /**
     * El misil ha chocado con algo y desaparece del juego.
     */
    public void colision(Colisionable objeto)
    {
        setVisible(false);
    }
}