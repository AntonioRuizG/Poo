import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Collection;
import java.util.Iterator;
import java.awt.image.*;

/**
 * Write a description of class Panel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PanelDeJuego extends JPanel implements ActionListener
{
    //El objeto Rtype contenedor del panel de juego
    private RType contenedor;
    
    //El timer del juego
    private Timer timer;
    
    //Coleccion de objetos animables del juego
    private Collection<Animable> animables;
    
    //La partida actual
    private Partida partida;
    
    //Nº de pasos que quedan para mostrar el menu una vez finalizada la partida
    private int acabando;
    
    /**
     * Constructor de objetos de clase PanelDeJuego
     * @param contenedor La ventana que contiene al panel de juego.
     */
    public PanelDeJuego(RType contenedor)
    {
        this.contenedor=contenedor;
        setDoubleBuffered(true);
        setBackground(Color.BLACK);
        timer = new Timer(20,this);
        addKeyListener(new TecladoListener());
        acabando=200;
    }
    
    /**
     * Pinta el panel de juego.
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        Toolkit.getDefaultToolkit().sync();
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(rh);
        
        //dibuja todos los objetos de la partida
        if (animables != null)
            for (Animable animable : animables)
                animable.dibujar(g2);
                
        g2.dispose();
    }
    
    /**
     * Metodo que rse ejecuta al recibir un evento.
     */
    public void actionPerformed(ActionEvent e)
    {
        if (partida.estaFinalizada()){
            if (acabando <= 0){
                timer.stop();
                contenedor.mostrarMenu();
            }
            acabando--;
        }
        partida.step();
        repaint();
    }
    
    /**
     * Comienza una partida con el modo de juego seleccionado.
     * @param modo El modo de juego.
     */
    public void comenzarPartida(ModoDeJuego modo)
    {
        Animable.setAreaDePantalla(getSize());
        partida = new Partida(modo);
        animables = partida.getAnimables();
        timer.start();
    }
    
    
    /**
     * Clase interna para escuchar eventos del teclado, extiende a KeyAdapter.
     * @see KeyAdapter
     * 
     * @author Antonio Ruiz Gutierrez 
     * @version 1.0.0
     */
    private class TecladoListener extends KeyAdapter 
    {
        /**
         * Este evento se lanza al presionar una tecla.
         */
        public void keyPressed(KeyEvent e) 
        {
            Integer code = e.getKeyCode();
            
            if(partida!=null)
                partida.teclaPresionada(e);
                
            //si la tecla presionada es escape se muestra el menu
            if (code==27){
                timer.stop();
                contenedor.mostrarMenu();
            }
        }
        
        /**
         * Este evento se lanza al soltar una tecla.
         */
        public void keyReleased(KeyEvent e)
        {
            if(partida!=null)
                partida.teclaSoltada(e);
        }
    }

    
    
}
