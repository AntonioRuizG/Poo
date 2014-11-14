import java.awt.*;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Esta clase es una ventana para mostrar el juego Rtype.
 * 
 * @author Antonio Ruiz Gutierrez 
 * @version 1.0.0
 */
public class RType extends JFrame
{
    //El panel de menu de opciones
    private JPanel panelDeMenu;
    
    /**
     * Constructor de objetos de clase RType
     */
    public RType()
    {
        super("RType");
        setUndecorated(true);
        ImageDatabase.cargar();
        panelDeMenu = new JPanel();
        panelDeMenu.setBackground(Color.black);
        crearMenuOpciones();
        mostrarMenu();
    }
    
    /**
     * Crea el menu de opciones, consta de un Panel con 4 botones 
     * para elegir la dificultad.
     * Utiliza BoxLayout con los botones centrados en el panel y
     * una separacion entre ellos de 10 pixeles.
     * @see BoxLayout
     * @see Panel
     */
    private void crearMenuOpciones()
    {
        Dimension separacion = new Dimension(0,10);
        
        panelDeMenu.setLayout(new BoxLayout(panelDeMenu, BoxLayout.PAGE_AXIS));
        panelDeMenu.add(Box.createVerticalGlue());
        
        BotonPersonalizado botonFacil = new BotonPersonalizado(200,45,ModoDeJuego.FACIL.toString());
        botonFacil.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e){
                            comenzarPartida(ModoDeJuego.FACIL);}
                    });
        panelDeMenu.add(botonFacil);
        panelDeMenu.add(Box.createRigidArea(separacion));
        
        BotonPersonalizado botonNormal = new BotonPersonalizado(200,45,ModoDeJuego.NORMAL.toString());
        botonNormal.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e){
                            comenzarPartida(ModoDeJuego.NORMAL);}
                    });
        panelDeMenu.add(botonNormal);
        panelDeMenu.add(Box.createRigidArea(separacion));
        
        BotonPersonalizado botonComplicado = new BotonPersonalizado(200,45,ModoDeJuego.COMPLICADO.toString());
        botonComplicado.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e){
                            comenzarPartida(ModoDeJuego.COMPLICADO);}
                    });
        panelDeMenu.add(botonComplicado);
        panelDeMenu.add(Box.createRigidArea(separacion));
        
        BotonPersonalizado botonImposible = new BotonPersonalizado(200,45,ModoDeJuego.IMPOSIBLE.toString());
        botonImposible.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e){
                            comenzarPartida(ModoDeJuego.IMPOSIBLE);}
                    });
        panelDeMenu.add(botonImposible);
        panelDeMenu.add(Box.createRigidArea(separacion));
        
        BotonPersonalizado salir = new BotonPersonalizado(200,45,"Salir");
        salir.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e){System.exit(0);}
                    });
        panelDeMenu.add(salir);
        panelDeMenu.add(Box.createVerticalGlue());
    }
    
    /**
     * Muestra el menu de opciones.
     */
    public void mostrarMenu()
    {
        setContentPane(panelDeMenu);
        setVisible(true);
        repaint();
    }
    
    /**
     * Comienza una partida con el modo de juego pasado como argumento.
     * @param modo El modo de juego.
     */
    private void comenzarPartida(ModoDeJuego modo)
    {
        PanelDeJuego panelDeJuego = new PanelDeJuego(this);
        setContentPane(panelDeJuego);
        setVisible(true);
        panelDeJuego.comenzarPartida(modo);
        panelDeJuego.setFocusable(true);
        panelDeJuego.requestFocus();
    }
    
    /**
     * Metodo de inicio de la aplicacion
     */
    public static void main(String[] args)
    {
        RType juego = new RType();
        juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        juego.setExtendedState(JFrame.MAXIMIZED_BOTH);
        juego.setResizable(false);
        juego.setVisible(true);
    }
        
    /**
     * Clase interna BotonPersonalizado, se trata de un Jbutton con un gradiente de color y
     * un tamaño personalizado
     */
    private class BotonPersonalizado extends JButton {
    
        private int ancho;
        private int alto;
        
        /**
         * Contructor de objetos de tipo BotonPersonalizado.
         * @param ancho El ancho del boton en pixeles.
         * @param alto El alto del boton en pixeles.
         * @param titulo El texto que se mostrara en el boton.
         */
        public BotonPersonalizado(int ancho,int alto,String titulo)
        {
            
            this.ancho = ancho;
            this.alto = alto;
            
            setAlignmentX(Component.CENTER_ALIGNMENT);
            
            Dimension d = new Dimension(ancho,alto);
            super.setMinimumSize(d);
            super.setMaximumSize(d);
            super.setPreferredSize(d);
            super.setText(titulo);
            super.setFont(new Font("Comic Sans MS",Font.BOLD, 2*alto/3));
    
        }
        
        
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                            0.7f);
            g2.setComposite(ac);   
            
            RadialGradientPaint gradiente;
            Point2D centro = new Point2D.Float(ancho/3, alto/3);
            float radio = Math.max(ancho,alto);
            float[] dist = {0.0f, 0.5f};
            Color[] colores = {Color.green, Color.red};
            gradiente = new RadialGradientPaint(centro, radio, dist, colores);
            g2.setPaint(gradiente);
            
            Rectangle2D rectangulo = new Rectangle2D.Double(0,0,ancho,alto);
            g2.fill(rectangulo);
        }
        
        
    }
}
