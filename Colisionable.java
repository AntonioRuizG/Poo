import java.awt.geom.*;
import java.awt.*;

/**
 * Representa objetos que pueden detectar una colision con objetos de este tipo..
 * 
 * @author Antonio Ruiz Gutierrez 
 * @version 1.0.0
 */

public abstract class Colisionable extends Animable
{
    //el area de colision
    private Area area;
    
    /**
     * Crea un objeto Colisionable en la posicion (x,y).
     * @param x La coordenada x.
     * @param y La coordenada y.
     */
    public Colisionable(double x, double y)
    {
        super(x,y);
    }
    
    /**
     * @return true si el objeto sobre el que se realiza la llamada colisiona 
     * con el objeto recibido como argumento.
     * @param objeto El objeto con el que comprobar si hay colision
     */
    public boolean hayColision(Colisionable objeto)
    {
        boolean colision = false;
        
        if (!this.esSingular() && !objeto.esSingular()){
            Area interseccion = new Area(this.area);
            interseccion.intersect(objeto.area);
            colision = !interseccion.isEmpty();
        }
        else if (this.esSingular() && !objeto.esSingular()){
             colision = objeto.area.contains(this.getPosicion());
        }
        else if (!this.esSingular() && objeto.esSingular()){
            colision = this.area.contains(objeto.getPosicion());
        }
        
        if (colision){
            this.colision(objeto);
            objeto.colision(this);  
        }
        return colision;
    }
    
    /**
     * Determina como actua un objeto ante una colision
     * @param objeto el objeto contra el que se ha colisionado.
     */
    public abstract void colision(Colisionable objeto);
    
    /**
     * @return El area de colision del objeto.
     */
    public Area getArea()
    {
        return area;
    }
    
    /**
     * Establece el area de colision del objeto.
     * @param area El area.
     * @see hayColision
     */
    public void setArea(Area area)
    {
        this.area = area;
    }
    
    /**
     * @return true si el objeto no tiene area, es un objeto de cero dimensiones
     */
    private boolean esSingular()
    {
        return area == null || area.isEmpty();
    }
    
    /**
     * @return un punto que representa la posicion del objeto.
     */
    private Point getPosicion()
    {
        return new Point((int)getX(),(int)getY());
    }
}


