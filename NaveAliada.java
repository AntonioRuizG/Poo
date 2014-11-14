import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;

/**
 * Esta clase representa una nave que es controlada por el jugador.
 * 
 * @author Antonio Ruiz Gutierrez 
 * @version 1.0.0
 */
public class NaveAliada extends Nave 
{
    //identificador del tipo de nave
    public static final String IDENTIFICADOR = "Nave_Aliada";
    
    //ancho de la nave
    private static int ancho = ImageDatabase.getImagen(IDENTIFICADOR).getWidth();
    
    //alto de la nave
    private static int alto = ImageDatabase.getImagen(IDENTIFICADOR).getHeight();
    
    //limites de movimiento
    private static int limiteDerecho = getAreaDePantalla().width-ancho;
    private static int limiteIzquierdo = 0;
    private static int limiteInferior = getAreaDePantalla().height-alto;
    private static int limiteSuperior = 0;
    
    //Un registro con las teclas que hay presionadas en cada momento
    private ArrayList<Integer> teclasPresionadas;
    
    //la hora del sistema del ultimo disparo de la nave
    private long ultimoDisparo;
    
    /**
     * Constructor de una nave aliada. La posicion inicial de la nave depende
     * del tamaño de la pantalla.
     */
    public NaveAliada()
    {
        //coloca la nave en la posicion inicial
        super(limiteDerecho/4, limiteInferior/2, 1);
        teclasPresionadas = new ArrayList<Integer>();
        
        //la nave esta lista para disparar
        ultimoDisparo = 0;
    }
    
    /**
     * Dibuja la nave en el contexto grafico especificado como parametro.
     * @param g El contexto grafico donde pintar.
     */
    public void dibujar(Graphics2D g)
    {
        //dibuja la nave
        g.drawImage(ImageDatabase.getImagen(IDENTIFICADOR),(int)getX(),(int)getY(),null);
        
        //actualiza el area de colision de la nave
        AffineTransform af = new AffineTransform();
        af.setToTranslation(getX(),getY());
        Area inicial = ImageDatabase.getArea(IDENTIFICADOR);
        Area transformada = new Area(inicial.createTransformedArea(af));
        setArea(transformada);
    }
    
    /**
     * Mueve la nave si alguna de las teclas de movimiento esta presionada.
     */
    public void mover()
    {
        super.mover();
        
        //comprueba que la nave no se salga de los limites
        if (getX() > limiteDerecho)
            setX(limiteDerecho);
        else if (getX() < limiteIzquierdo)
            setX(limiteIzquierdo);
        
        if (getY() > limiteInferior)
            setY(limiteInferior);
        else if (getY() < limiteSuperior)
            setY(limiteSuperior);
    }
    
    /**
     * Devuelve true si la nave ha tenido tiempo de recargar
     * y si la tecla espacio esta presionada.
     */
    private boolean puedeDisparar()
    {
        return ultimoDisparo + 200 < System.currentTimeMillis() && 
        teclasPresionadas.contains(KeyEvent.VK_SPACE);
    }
    
    /**
     * Devuelve un objeto Misil si la nave ha tenido tiempo de recargar
     * y si la tecla espacio esta presionada. Sino devuelve null.
     */
    public Misil disparar()
    {
        if (puedeDisparar() && esVisible()){
            ultimoDisparo = System.currentTimeMillis();
            return new Misil(getX()+160,getY()+49);
        }
        return null;
    }
    
    /**
     * Se informa a la nave que ha habido una pulsacion de teclado.
     * @param e El evento de teclado.
     */
    public void teclaPresionada(KeyEvent e)
    {
        Integer code = e.getKeyCode();
        
        if (teclasPresionadas.contains(code))
            return;
            
        teclasPresionadas.add(code);
        switch (code){
            case KeyEvent.VK_Q: acelerar(0,-10);break;
            case KeyEvent.VK_A: acelerar(0,10);break;
            case KeyEvent.VK_O: acelerar(-10,0);break;
            case KeyEvent.VK_P: acelerar(10,0);break;
        }
    }
    
    /**
     * Se informa a la nave que se ha soltado una tecla que estaba presionada.
     * @param e El evento de teclado.
     */
    public void teclaSoltada(KeyEvent e)
    {
        Integer code = e.getKeyCode();
        
        int pos = teclasPresionadas.indexOf(code);
            if (pos >= 0)
                teclasPresionadas.remove(pos);
            
        switch (code){
            case KeyEvent.VK_Q: acelerar(0,10);break;
            case KeyEvent.VK_A: acelerar(0,-10);break;
            case KeyEvent.VK_O: acelerar(10,0);break;
            case KeyEvent.VK_P: acelerar(-10,0);break;
        }
    }
    
    /**
     * Acelera la nave en el vector (ddx,ddy)
     */
    private void acelerar(double ddx, double ddy)
    {
        setVelocidadX(getVelocidadX()+ddx);
        setVelocidadY(getVelocidadY()+ddy);
    }
}

