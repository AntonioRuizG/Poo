import java.io.FileReader;
import java.io.BufferedReader;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Una base de datos de imagenes que se cargan del disco.
 * 
 * @author Antonio Ruiz Gutierrez 
 * @version 1.0.0
 */
public class ImageDatabase
{
    private static final String ARCHIVO = "config.txt";
    private static HashMap<String,BufferedImage> imagenes;
    private static HashMap<String,Area> areas;
    
    /**
     * Carga las imagenes en la base de datos.
     */
    public static void cargar()
    {
        BufferedReader buffer;
        imagenes = new HashMap<String,BufferedImage>();
        areas = new HashMap<String,Area>();
        try{
            FileReader fileReader = new FileReader(ARCHIVO);
            buffer = new BufferedReader(fileReader);
            cargarImagenes(buffer);
        }
        catch(Exception e){
            System.out.println("Error en el archivo: " + ARCHIVO);
        }
        
    }
    
    private static void cargarImagenes(BufferedReader buffer)
    {
        try{
            String linea = buffer.readLine();
            while(linea != null){
                String[] palabras = linea.split("\t");
                cargarImagen(palabras[0],palabras[1]);
                linea = buffer.readLine();
            }
        }
        catch (Exception e){}
    }
    
    /**
     * Guarda una imagen en la base de datos.
     * @param id El identificador que se le asigna a la imagen
     * @param nombreArchivo El nombre del archivo de iamgen.
     */
    private static void cargarImagen(String id, String nombreArchivo)
    {
        File archivo = new File(nombreArchivo);
        try{
            BufferedImage imagen = ImageIO.read(archivo);
            imagen = procesarImagen(imagen);
            imagenes.put(id,imagen);
            areas.put(id,crearArea(imagen));
        }
        catch(Exception e){}
    }
    
    /**
     * Cambia los pixeles blancos por pixeles transparentes de la imagen recibida
     */
    private static BufferedImage procesarImagen(BufferedImage imagen)
    {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();
        
        //crea un filtro que cambia el color blanco por transparente
        ImageFilter filtro = new RGBImageFilter() {
            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if (rgb == 0xFFFFFFFF) {
                    return 0x00FFFFFF;
                } 
                return rgb;
            }
        };
        FilteredImageSource fis = new FilteredImageSource(imagen.getSource(), filtro);
        Image nuevaImagen = Toolkit.getDefaultToolkit().createImage(fis);
        imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagen.createGraphics();
        g2.drawImage(nuevaImagen, 0, 0, null);
        g2.dispose();
        return imagen;
    }
    
    /**
     * @return Un objeto bufferedImage con la imagen que corresponde al identificador.
     * @param id El identificador de la imagen.
     */
    public static BufferedImage getImagen(String id)
    {
        return imagenes.get(id);
    }

    /**
     * @return Un objeto Area con el area que corresponde al identificador.
     * @param id El identificador del area.
     */
    public static Area getArea(String id)
    {
        return areas.get(id);
    }
    
    /**
     * Crea un objeto Area a partir de los pixeles no transparentes de un 
     * objeto BufferedImage.
     * @param imagen La imagen inicial.
     * @return El area obtenida.
     */
    public static Area crearArea(BufferedImage imagen)
    {
        Area area = new Area();
        Rectangle rectangulo = new Rectangle();
        for (int x = 0; x < imagen.getWidth(); x+=5) {
            for (int y = 0; y < imagen.getHeight(); y+=5) {
                if (imagen.getRGB(x, y) != 0) {
                    int y1=y;
                    while(y < imagen.getHeight() && imagen.getRGB(x, y)!=0)
                        y+=5;
                    rectangulo.setBounds(x, y1, 5, y-y1);
                    area.add(new Area(rectangulo));
                }
            }
        }
        return area;
    }
}
