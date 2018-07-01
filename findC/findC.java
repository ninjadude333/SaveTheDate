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

    public static ArrayList<Line> calcSideLines(ArrayList<Coords> trackedPoints)
    {
        ArrayList<Line> linesArray = new ArrayList<Line>();
        
        return linesArray;
    }

   public static void main(String[] args) 
   {
    BufferedImage img = null;
    //int contourColor = -13364318;
    double detectionThreshold = 6.0;
    Color contourColorType = new Color(-13364318,false);
    int startX = 0;
    int startY = 0;
    ArrayList<Coords> contourPoints = new ArrayList<Coords>();
    ArrayList<Coords> junctionPoints = new ArrayList<Coords>();

        try 
        {
          img = ImageIO.read(new File("simplePixelContour.jpg"));

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
                            contourPoints.add(new Coords(startX, startY, "EntryPoint"));
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

        while ( !fullCircle )
        {
            System.out.println("direction = " + direction + " currentpointX = " + currentpointX + " currentpointY = " + currentpointY);
            currentPixelColor = new Color(img.getRGB(currentpointX, currentpointY));
            System.out.println("RGBa Red Value : " + currentPixelColor.getRed());
            System.out.println("RGBa Green Value : " + currentPixelColor.getGreen());
            System.out.println("RGBa Blue Value : " + currentPixelColor.getBlue());
            System.out.println("RGBa Alpha Value : " + currentPixelColor.getAlpha());

            if (calcDistance(currentPixelColor, contourColorType) < detectionThreshold)
                {

                    if (currentpointX == 85 && currentpointY == 22)
                    {
                        System.out.println("fff");
                    }

                    System.out.println(calcDistance(currentPixelColor, contourColorType));
                    System.out.println("adding coordinate : " + currentpointX + ":" + currentpointY);
                    contourPoints.add(new Coords(currentpointX, currentpointY, direction));

                    switch(direction)
                    {
                        case "up":  
                        if ((currentpointY + 1) <= height)
                        {
                            currentpointY++;
                            break;
                        }
                        case "right":  
                        if ((currentpointX + 1) <= width)
                        {
                            currentpointX++;
                            break;
                        }
                        case "down":  
                        if ((currentpointY - 1) >= 0)
                        {
                            currentpointY--;
                            break;
                        }
                        case "left":  
                        if ((currentpointX - 1) >= 0)
                        {
                            currentpointX--;
                            break;
                        }
                    }
                }
            else
            {   
               switch(direction)
               {
                case "up":  
                        if ((currentpointX + 1) <= width)
                        {
                            System.out.println("setting Direction to RIGHT");
                            direction = "right";
                            junctionPoints.add(new Coords(currentpointX, currentpointY, direction));
                            currentpointY--;
                            currentpointX++;
                            break;
                        }
                case "right":  
                        if ((currentpointY - 1) >= 0)
                        {
                            System.out.println("setting Direction to DOWN");
                            direction = "down";
                            junctionPoints.add(new Coords(currentpointX, currentpointY, direction));
                            currentpointX--;
                            currentpointY--;
                            break;
                        }
                case "down":  
                        if ((currentpointX - 1) >= 0)
                        {
                            System.out.println("setting Direction to LEFT");
                            direction = "left";
                            junctionPoints.add(new Coords(currentpointX, currentpointY, direction));
                            currentpointY++;
                            currentpointX--;
                            break;
                        }
                case "left":  
                        if ((currentpointY + 1) <= height)
                        {
                            System.out.println("setting Direction to UP");
                            direction = "up";
                            junctionPoints.add(new Coords(currentpointX, currentpointY, direction));
                            currentpointX++;
                            currentpointY++;
                            break;
                        }
               } 
            }

            if (currentpointX == startX && currentpointY == startY)
            {
                System.out.println("Full \"Circle\" Achieved. Closing While Loop.");
                fullCircle = true;
            }

        }

        System.out.println("Traced Points in Junction:");
        for (Coords point : junctionPoints)
        {
            System.out.println(point.x + ":" + point.y + " -- changed direction to: " + point.direction);
        }

        System.out.println("\ncontour Tracing is done!");
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
    String direction = "";

    public boolean equals(Object o)
    {
        Coords c = (Coords) o;
        return c.x == x && c.y == y;
    }

    public Coords(int x, int y, String direction) 
    {
        super();
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int hashCode() 
    {
        return new Integer(x + ":" + y);
    }
}

class Line 
{
    int startX;
    int startY;
    int endX;
    int endY;
    string direction = "";

    public Line(int startX, int startY, int endX, int endY, string direction) 
    {
        super();
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.direction = direction;
    }
}