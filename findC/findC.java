import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.*;

public class findC 
{

    public static double calcDistance(Color a, Color b)
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
    System.out.println("Hello, World");

    BufferedImage img = null;
    //int contourColor = -1735102;
    Color contourColorType = new Color(-1735102,false);
    int startX = 0;
    int startY = 0;
    ArrayList<Coords> contourPoints = new ArrayList<Coords>();

        try 
        {
          img = ImageIO.read(new File("contour.jpg"));

          here: for (int i = img.getHeight() / 2; i < img.getHeight(); i++) 
            {
                for (int j = img.getWidth() / 2; j < img.getWidth(); j--) 
                {
                    Color currentPixelColor = new Color(img.getRGB(j, i),false);
                    if (calcDistance(currentPixelColor, contourColorType) < 3.0)
                        {
                            System.out.println("Entrypoint Pixel is " + j + ":" + i + "  ===  Start Tracing...");
                            startX = j+1;
                            startY = i+1;
                            System.out.println("RGBa Red Value : " + currentPixelColor.getRed());
                            System.out.println("RGBa Green Value : " + currentPixelColor.getGreen());
                            System.out.println("RGBa Blue Value : " + currentPixelColor.getBlue());
                            System.out.println("RGBa Alpha Value : " + currentPixelColor.getAlpha());
                            break here;
                        }    
                }
            }

        boolean fullCircle = false;
        int width = img.getWidth();
        int height = img.getHeight();
        int currentpointX = startX;
        int currentpointY = startY;
        String direction = "up";
        Color currentPixelColor;

        while (!fullCircle)
        {
            if (direction.equals("up") && ((currentpointY + 1) <= height))
            {
                System.out.println("direction = " + direction + " currentpointY = " + currentpointY + " height = " + height);
                currentPixelColor = new Color(img.getRGB(currentpointX, currentpointY));
                System.out.println("RGBa Red Value : " + currentPixelColor.getRed());
                System.out.println("RGBa Green Value : " + currentPixelColor.getGreen());
                System.out.println("RGBa Blue Value : " + currentPixelColor.getBlue());
                System.out.println("RGBa Alpha Value : " + currentPixelColor.getAlpha());

                currentPixelColor = new Color(img.getRGB(currentpointX,currentpointY));
                if (calcDistance(currentPixelColor, contourColorType) < 5.0)
                    {
                        System.out.println(calcDistance(currentPixelColor, contourColorType));
                        System.out.println("adding coordinate : " + currentpointX + ":" + currentpointY);
                        contourPoints.add(new Coords(currentpointX, currentpointY));
                    }
                else
                    {
                        System.out.println("calcDistance = " + calcDistance(currentPixelColor, contourColorType));
                        System.out.println("setting Direction to RIGHT");
                        direction = "right";
                    }
            } 
            else if (direction.equals("right") && ((currentpointX + 1) <= width))
                    {
                        System.out.println("direction = " + direction + " currentpointY = " + currentpointY + " height = " + height);
                        currentPixelColor = new Color(img.getRGB(currentpointX,currentpointY));
                        if (calcDistance(currentPixelColor, contourColorType) < 3.0)
                        {
                            System.out.println("calcDistance = " + calcDistance(currentPixelColor, contourColorType));
                            System.out.println("adding coordinate : " + currentpointX + ":" + currentpointY);
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

class Coords 
{
    int x;
    int y;

    public boolean equals(Object o)
    {
        Coords c = (Coords) o;
        return c.x == x && c.y == y;
    }

    public Coords(int x, int y) 
    {
        super();
        this.x = x;
        this.y = y;
    }

    public int hashCode() 
    {
        return new Integer(x + ":" + y);
    }
}