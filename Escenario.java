import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;
import java.awt.geom.*;

/**
 * Esta clase representa un nivel del juego, controla la aparicion de naves 
 * enemigas en el juego.
 * 
 * @author Antonio Ruiz Gutierrez 
 * @version 1.0.0
 */
public class Escenario extends Animable
{
    private static final Random rand = new Random();
    private List<Point> estrellas;
    private ModoDeJuego modo;
    private int navesAparecidas;
    private long horaUltimaNave;
    
    /**
     * Constructor de objetos de clase Escenario.
     * @param modo El nivel de dificultad del escenario.
     * @see ModoDeJuego
     */
    public Escenario(ModoDeJuego modo)
    {
        super(0,0);
        this.modo = modo;
        navesAparecidas = 0;
        estrellas = new ArrayList<Point>();
        for(int i=0;i<200;i++)
            estrellas.add(crearPuntoAleatorio());

    }
    
    /**
     * @return un objeto NaveAlienigena si ha pasado suficiente tiempo 
     * desde la ultima aparicion.
     * La aparicion de naves es aleatoria, puede devolver null.
     */
    public NaveAlienigena siguienteNave()
    {
        NaveAlienigena siguiente = null;
        
        if (saleSiguienteNave()){
            if (rand.nextInt(100) > 50)
                siguiente = new NaveAlienigena(NaveAlienigena.TIPO_A);
            else
                siguiente = new NaveAlienigena(NaveAlienigena.TIPO_B);
                
            siguiente.setVelocidadX(-modo.velocidadNaves);
            navesAparecidas++;
            horaUltimaNave = System.currentTimeMillis();
        }
        
        return siguiente;
    }
    
    /**
     * @return true si ha pasado suficiente tiempo desde la aparicion
     * de la ultima nave enemiga.
     */
    private boolean saleSiguienteNave()
    {
        long horaActual = System.currentTimeMillis();
        boolean esLaHora = horaActual > horaUltimaNave + rand.nextInt(2000)+1000;
        return esLaHora && quedanMasNaves();
    }
    
    /**
     * Crea un punto aleatorio, dentro de los limites de la pantalla.
     */
    private Point crearPuntoAleatorio()
    {
        Dimension dimPantalla = getAreaDePantalla();
        return new Point(rand.nextInt(dimPantalla.width),
                          rand.nextInt(dimPantalla.height));
    }
    
    
    
    /**
     * @return true si aun faltan naves por salir.
     */
    public boolean quedanMasNaves()
    {
        return navesAparecidas < modo.cantidadNaves;
    }
    
    /**
     * Dibuja el fondo de estrellas de la pantalla de juego.
     */
    public void dibujar(Graphics2D g)
    {
        g.setColor(Color.white);
        for (Point estrella : estrellas){
            Ellipse2D e = new Ellipse2D.Double(estrella.getX(),estrella.getY(),2,2);
            g.fill(e);
        }
        
    }
    
    /**
     * Actualiza la posicion de las estrellas.
     */
    public void mover()
    {
        Dimension dimPantalla = getAreaDePantalla();

        for (Point estrella : estrellas){
            estrella.translate(-5,0);
            if (estrella.getX()<0)
                estrella.translate(dimPantalla.width,0);
        }
    }
    
}
