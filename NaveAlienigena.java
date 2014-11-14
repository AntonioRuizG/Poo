import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Random;

/**
 * Esta clase representa a una nave controlada por el ordenador, se puede usar para juegos arcade.
 * 
 * @author Antonio Ruiz Gutierrez 
 * @version 1.0.0
 */
public class NaveAlienigena extends Nave
{
    //este tipo de nave solo se mueve horizotalmente
    public static final int TIPO_A = 0;
    
    //este tipo de nave se mueve horizontal y verticalmente
    public static final int TIPO_B = 1;
    
    //generador de aleatorios
    private Random rand = new Random();
    
    //identificador del tipo de nave
    private String identificador;
    
    /**
     * Construye una nueva nave alienigena colocada en un punto aleatorio del 
     * limite derecho de la pantalla.
     * @param tipoNave El tipo de nave que sera: 
     * TIPO_A la nave solo se mueve horizotalmente
     * TIPO_B la nave se mueve horizontal y verticalmente
     */
    public NaveAlienigena(int tipoNave)
    {
        super();
        
        if (tipoNave == TIPO_B){
            setVelocidadY(10);
            identificador = "Nave_B";
        }
        else{
            identificador = "Nave_A";
        }
        
        int anchoNave = ImageDatabase.getImagen(identificador).getWidth();
        int altoNave = ImageDatabase.getImagen(identificador).getHeight();
        
        //coloca la nave en una posicion aleatoria del limite derecho de la pantalla
        setY(rand.nextInt(getAreaDePantalla().height - altoNave));
        setX(getAreaDePantalla().width + anchoNave);
    }
    
    /**
     * Dibuja la nave en el contexto grafico especificado como parametro.
     * @param g El contexto grafico donde pintar.
     */
    public void dibujar(Graphics2D g)
    {
        //dibuja la nave
        g.drawImage(ImageDatabase.getImagen(identificador),(int)getX(),(int)getY(),null);
        
        //actualiza el area de colision de la nave
        AffineTransform af = new AffineTransform();
        af.setToTranslation(getX(),getY());
        Area inicial = ImageDatabase.getArea(identificador);
        Area transformada = inicial.createTransformedArea(af);
        setArea(transformada);
    }
    
    /**
     * Mueve la nave segun el valor de la velocidad.
     * @see setVelocidadX(), setVelocidadY(), getVelocidadX(), getVelocidadY()
     */
    public void mover()
    {
        super.mover();
        
        //Calcula los limites de movimiento
        int anchoNave = ImageDatabase.getImagen(identificador).getWidth();
        int altoNave = ImageDatabase.getImagen(identificador).getHeight();
        int limiteDerecho = getAreaDePantalla().width + anchoNave;
        int limiteIzquierdo = - anchoNave;
        int limiteInferior = getAreaDePantalla().height + altoNave;
        int limiteSuperior = - altoNave;

        //comprueba que la nave no se salga de los limites
        if (getX() < limiteIzquierdo)
            setX(limiteDerecho);
        
        if (getY() > limiteInferior)
            setY(limiteSuperior);
        else if (getY() < limiteSuperior)
            setY(limiteInferior);
        
        //la nave cambia su velocidad vertical aleatoriamente
        if (rand.nextInt(100) < 2)
            setVelocidadY(-getVelocidadY());
    }
}
