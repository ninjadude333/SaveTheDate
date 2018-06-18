import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.*;

public class pixelMapper 
{
   public static double DistanceSquared(Color a, Color b)
    {
        int deltaR = a.getRed() - b.getRed();
        int deltaG = a.getGreen() - b.getGreen();
        int deltaB = a.getBlue() - b.getBlue();
        int deltaAlpha = a.getAlpha() - b.getAlpha();
        double rgbDistanceSquared = (deltaR * deltaR + deltaG * deltaG + deltaB * deltaB) / 3;
        return deltaAlpha * deltaAlpha / 2.0 + rgbDistanceSquared * a.getAlpha() * b.getAlpha() / (255 * 255);
    }

   public static void main(String[] args) 
   {
    System.out.println("Hello, Pixel");

    BufferedImage img = null;
    Color seekRGB = new Color(-1329040,false);
    //int startX = 0;
    //int startY = 0;

        try 
        {
          img = ImageIO.read(new File("simplePixelContour.jpg"));
          System.out.println("image width:" + img.getWidth());
          System.out.println("image height:" + img.getHeight());
          System.out.println("image type:" + img.getType());
          System.out.println("colorModel type:" + img.getColorModel());

          double tmpDist =   DistanceSquared(seekRGB, new Color(-1329040,false));
          System.out.println("distance calculated is:" + tmpDist);

          for (int i = 0; i < img.getHeight(); i++) 
            {
                for (int j = 0; j < img.getWidth(); j++) 
                {
                    if (img.getRGB(j, i) != -1)
                        {
                            System.out.println("Point Traced: " + j + ":" + i + " ---- " + img.getRGB(j, i));
                            Color myColor = new Color(img.getRGB(j, i),false);
                            System.out.println("RGBa Values : " + myColor.getRed() + "," + myColor.getGreen() + "," + myColor.getBlue());
                        }    
                }
            }

        System.out.println("Thanks, Byebye");

        } 
        catch (IOException e) 
        {
        }
    }
}
