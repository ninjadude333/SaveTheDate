import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.*;

public class findC 
{
   public static void main(String[] args) 
   {
    System.out.println("Hello, World");

    BufferedImage img = null;
    int contourColor = -1735102;
    int startX = 0;
    int startY = 0;
    ArrayList<Coords> contourPoints = new ArrayList<Coords>();

        try 
        {
          img = ImageIO.read(new File("contour.jpg"));

          for (int i = 0; i < img.getHeight(); i++) 
            {
                for (int j = 0; j < img.getWidth(); j++) 
                {
                    if (img.getRGB(j, i) == contourColor)
                        {
                            System.out.println("Point Traced: " + j + ":" + i);
                        }    
                }
            }


          for (int i = img.getHeight() / 2; i < img.getHeight(); i++) 
            {
                for (int j = img.getWidth() / 2; j < img.getWidth(); j++) 
                {
                    if (img.getRGB(j, i) == contourColor)
                        {
                            System.out.println("Entrypoint Pixel is " + j + ":" + i + "  ===  Start Tracing...");
                            startX = j;
                            startY = i;
                            Color myColor = new Color(img.getRGB(j, i),true);
                            System.out.println("RGBa Red Value : " + myColor.getRed());
                            System.out.println("RGBa Green Value : " + myColor.getGreen());
                            System.out.println("RGBa Blue Value : " + myColor.getBlue());
                            System.out.println("RGBa Alpha Value : " + myColor.getAlpha());
                            break;
                        }    
                }
            }

        boolean fullCircle = false;
        int width = img.getWidth();
        int height = img.getHeight();
        int currentpointX = startX;
        int currentpointY = startY;

        while (!fullCircle)
        {
            if ( (currentpointY + 1 <= height) && (img.getRGB(currentpointX, currentpointY + 1) == contourColor))
            {
                currentpointY = currentpointY + 1;
                System.out.println(currentpointX + ":" + currentpointY);
                contourPoints.add(new Coords(currentpointX, currentpointY));
            } 
            else 
            {
				if ( (currentpointX + 1 <= width) && (img.getRGB(currentpointX + 1, currentpointY) == contourColor))
                    {
                        currentpointX = currentpointX + 1;
                        System.out.println(currentpointX + ":" + currentpointY);
                        contourPoints.add(new Coords(currentpointX, currentpointY));
                    }
			}

            if (currentpointX == startX && currentpointY == startY)
                fullCircle = true;
        }

        System.out.println("Traced Points list:");
        for (Coords point : contourPoints)
        {
            System.out.println(point.x + ":" + point.y);
        }

        } 
        catch (IOException e) 
        {
        }
    }
}

class Coords {
    int x;
    int y;

    public boolean equals(Object o) {
        Coords c = (Coords) o;
        return c.x == x && c.y == y;
    }

    public Coords(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int hashCode() {
        return new Integer(x + ":" + y);
    }
}