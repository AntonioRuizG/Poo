import java.awt.*;
import java.awt.geom.*;

/**
 * Representa una nave que se mueve por la pantalla y 
 * puede detectar colisiones con otras naves, puede usarse para juegos arcade.
 * 
 * @author Antonio Ruiz Gutierrez 
 * @version 1.0.0
 */
public abstract class Nave extends Colisionable
{
    //puntos de vida de la nave
    private int puntosDeVida;
    
    /**
     * Construye una nave nueva
     * @param x La coordenada x.
     * @param y La coordenada y.
     * @param puntosDeVida los puntos de vida de la nave.
     */
    public Nave(double x, double y, int puntosDeVida)
    {
        super(x,y);
        this.puntosDeVida = puntosDeVida;
    }
    
    /**
     * Construye una nave nueva, en la posicion 0,0 y con 1 sola vida
     */
    public Nave()
    {
        super(0,0);
        this.puntosDeVida = 1;
    }
    
    /**
     * Establece los puntos de vida de la nave.
     * @param
     */
    public void setPuntosDeVida(int puntosDeVida)
    {
        this.puntosDeVida = puntosDeVida;
    }
    
    /**
     * @return Los puntos de vida de la nave
     */
    public int getPuntosDeVida()
    {
        return puntosDeVida;
    }
    
    /**
     * Establece una colision, la nave pierde un punto de vida cada
     * vez que recibe un golpe, si se le acaban los puntos de vida desaparece.
     * @param objeto El objeto contra el que ha colisionado la nave.
     * @see getPuntosDeVida, setPuntosDeVida
     */
    public void colision(Colisionable objeto)
    {
        if (--puntosDeVida <= 0)
            setVisible(false);
    }
    
}
