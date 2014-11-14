import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.font.*;

/**
 * Esta clase representa una partida del juego Rtype.
 * 
 * @author Antonio Ruiz Gutierrez 
 * @version 1.0.0
 */
public class Partida
{
    //Lista de misiles en la partida
    private LinkedList<Colisionable> misiles;
    
    //Lista de naves enemigas
    private LinkedList<Colisionable> navesEnemigas;
    
    //Todos los objetos que se dibujan en la pantalla
    private Collection<Animable> animables;
    
    //La nave aliada
    private NaveAliada naveAliada;
    
    //El escenario de la partida
    private Escenario escenario;
    
    private boolean finalizada;
    
    /**
     * Constructor de objetos de tipo partida
     * @param modo Elmodo de juego de la partida.
     * @see ModoDeJuego
     */
    public Partida(ModoDeJuego modo)
    {
        finalizada = false;
        naveAliada = new NaveAliada();
        navesEnemigas = new LinkedList<Colisionable>();
        escenario = new Escenario(modo);
        misiles = new LinkedList<Colisionable>();
        animables = new LinkedList<Animable>();
        misiles = new LinkedList<Colisionable>();
        animables.add(escenario);
        animables.add(naveAliada);
    }
    
    /**
     * A este metodo se le llama cuando se produce un evento de teclado
     * @param e El evento de teclado
     */
    public void teclaPresionada(KeyEvent e)
    {
        naveAliada.teclaPresionada(e);
    }
    
    /**
     * A este metodo se le llama cuando se produce un evento de teclado
     * @param e El evento de teclado
     */
    public void teclaSoltada(KeyEvent e)
    {
        naveAliada.teclaSoltada(e);
    }
    
    /**
     * Actualiza el estado de los objetos de la partida.
     */
    public void step()
    {
        comprobarColisiones();
        eliminarInvisibles(navesEnemigas);
        eliminarInvisibles(misiles);
        eliminarInvisibles(animables);
        
        for (Animable animable:animables)
            animable.mover();
        
        if (!finalizada){
            anadirNuevos();
            if(navesEnemigas.size()==0&&!escenario.quedanMasNaves())
                felicitacion();
        }
        
    }
    
    /**
     * @return la lista de objetos animables de la partida.
     */
    public Collection<Animable> getAnimables()
    {
        return animables;
    }
    
    /**
     * @return true si la partida ha acabado.
     */
    public boolean estaFinalizada()
    {
        return finalizada;
    }
    
    /**
     * Añade nuevos objetos a la partida, misiles, naves...
     */
    private void anadirNuevos()
    {
        Colisionable nuevo = naveAliada.disparar();
        if (nuevo != null){
            misiles.add(nuevo);
            animables.add(nuevo);
        }

        Colisionable siguiente = escenario.siguienteNave();
        if (siguiente != null){
            navesEnemigas.add(siguiente);
            animables.add(siguiente);
        }
    }
    
    /**
     * Elimina los objetos invisibles de la lista recibida.
     * @param lista La lista de objetos Animable.
     */
    private <U extends Animable> void eliminarInvisibles(Collection<U> lista)
    {
        if (lista == null) return;
        Iterator<U> it = lista.iterator();
        while(it.hasNext())
            if(!it.next().esVisible())
                it.remove();
    }
    
    /**
     * Comprueba colisiones entre naves y misiles.
     */
    private void comprobarColisiones()
    {
        for(Colisionable nave : navesEnemigas){
            if (naveAliada.esVisible()){
                if (naveAliada.hayColision(nave)){
                    gameOver();
                }
            }
            for(Colisionable misil : misiles){
                misil.hayColision(nave);
            }
        }
    }
    
    /**
     * Muestra un mensaje de Game over y finaliza la partida.
     */
    private void gameOver()
    {
        finalizada = true;
        animables.add(new Mensaje("GAME OVER", Color.yellow));
    }
    
    /**
     * Muestra un mensaje de felicitacion y finaliza la partida.
     */
    private void felicitacion()
    {
        finalizada = true;
        animables.add(new Mensaje("ENHORABUENA", Color.yellow));
    }
    
    /**
     * Clase interna Mensaje, representa un mensaje de texto que aparece en la pantalla
     */
    private class Mensaje extends Animable
    {
        private String texto;
        private Color color;
        
        public Mensaje(String texto, Color color)
        {
            super(0,0);
            this.texto = texto;
            this.color = color;
        }
        
        public void dibujar(Graphics2D g)
        {
            g.setColor(color);
            Dimension limites = getAreaDePantalla();
            FontRenderContext frc = g.getFontRenderContext();
            TextLayout tl;
            Font f;
            f = new Font("Arial", Font.BOLD, limites.width/9);
            tl = new TextLayout(texto, f, frc);
            tl.draw(g, (float)(limites.width - tl.getBounds().getWidth())/2,
            limites.height/2.0f);
        }
    }
}
