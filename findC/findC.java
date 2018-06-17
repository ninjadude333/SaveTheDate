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
    BufferedImage img = null;
    //int contourColor = -4063232;
    double detectionThreshold = 5.0;
    Color contourColorType = new Color(-4063232,false);
    int startX = 0;
    int startY = 0;
    ArrayList<Coords> contourPoints = new ArrayList<Coords>();

        try 
        {
          img = ImageIO.read(new File("contourSimple.jpg"));

          here: for (int i = img.getHeight() / 2; i < img.getHeight(); i++) 
            {
                for (int j = img.getWidth() / 2; j >=0; j--) 
                {
                    Color currentPixelColor = new Color(img.getRGB(j, i),false);
                    if (calcDistance(currentPixelColor, contourColorType) == 0.0)
                        {
                            System.out.println("Entrypoint Pixel is " + j + ":" + i + "  ===  Start Tracing...");
                            startX = j;
                            startY = i;
                            System.out.println("RGBa Red Value : " + currentPixelColor.getRed());
                            System.out.println("RGBa Green Value : " + currentPixelColor.getGreen());
                            System.out.println("RGBa Blue Value : " + currentPixelColor.getBlue());
                            System.out.println("RGBa Alpha Value : " + currentPixelColor.getAlpha());
                            contourPoints.add(new Coords(startX, startY));
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

        while ( !fullCircle && currentpointX + 1 != width && currentpointY + 1 != height )
        {
            if (direction.equals("up") && ((currentpointY + 1) <= height))
            {
                currentpointY++;
                System.out.println("direction = " + direction + " currentpointY = " + currentpointY + " height = " + height);
                currentPixelColor = new Color(img.getRGB(currentpointX, currentpointY));
                System.out.println("RGBa Red Value : " + currentPixelColor.getRed());
                System.out.println("RGBa Green Value : " + currentPixelColor.getGreen());
                System.out.println("RGBa Blue Value : " + currentPixelColor.getBlue());
                System.out.println("RGBa Alpha Value : " + currentPixelColor.getAlpha());

                if (calcDistance(currentPixelColor, contourColorType) < detectionThreshold)
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
                        currentpointX++;
                        System.out.println("direction = " + direction + " currentpointX = " + currentpointX + " width = " + width);
                        currentPixelColor = new Color(img.getRGB(currentpointX,currentpointY));
                        if (calcDistance(currentPixelColor, contourColorType) < detectionThreshold)
                        {
                            System.out.println("calcDistance = " + calcDistance(currentPixelColor, contourColorType));
                            System.out.println("adding coordinate : " + currentpointX + ":" + currentpointY);
                            contourPoints.add(new Coords(currentpointX, currentpointY));
                        }
                        else
                        {
                            System.out.println("calcDistance = " + calcDistance(currentPixelColor, contourColorType));
                            System.out.println("setting Direction to DOWN");
                            direction = "down";
                        }
                    }
                    else if (direction.equals("down") && ((currentpointY - 1) >= 0))
                    {
                        currentpointY--;
                        System.out.println("direction = " + direction + " currentpointY = " + currentpointY + " height = " + height);
                        currentPixelColor = new Color(img.getRGB(currentpointX,currentpointY));
                        if (calcDistance(currentPixelColor, contourColorType) < detectionThreshold)
                        {
                            System.out.println("calcDistance = " + calcDistance(currentPixelColor, contourColorType));
                            System.out.println("adding coordinate : " + currentpointX + ":" + currentpointY);
                            contourPoints.add(new Coords(currentpointX, currentpointY));
                        }
                        else
                        {
                            System.out.println("calcDistance = " + calcDistance(currentPixelColor, contourColorType));
                            System.out.println("setting Direction to LEFT");
                            direction = "left";
                        }
                    }
                    else if (direction.equals("left") && ((currentpointX - 1) >= 0))
                    {
                        currentpointX--;
                        System.out.println("direction = " + direction + " currentpointX = " + currentpointX + " width = " + width);
                        currentPixelColor = new Color(img.getRGB(currentpointX,currentpointY));
                        if (calcDistance(currentPixelColor, contourColorType) < detectionThreshold)
                        {
                            System.out.println("calcDistance = " + calcDistance(currentPixelColor, contourColorType));
                            System.out.println("adding coordinate : " + currentpointX + ":" + currentpointY);
                            contourPoints.add(new Coords(currentpointX, currentpointY));
                        }
                        else
                        {
                            System.out.println("calcDistance = " + calcDistance(currentPixelColor, contourColorType));
                            System.out.println("setting Direction to UP");
                            direction = "up";
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

        System.out.println("contour Tracing is done!");


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