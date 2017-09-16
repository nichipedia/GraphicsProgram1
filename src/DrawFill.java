/**
 * Created by NicholasMoran on 9/12/17.
 */
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;   // For BufferedImage
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;      // To display the outcome// For color


public class DrawFill
{
    final static JFrame myFrame = new JFrame();
    final static ArrayList<Point> points = new ArrayList<>();
    final static BufferedImage img = new BufferedImage(720, 480, BufferedImage.TYPE_INT_RGB);
    public static void main(String[] args) {



        showSelectPointDialog();
        // g2d.setColor(Color.RED);
        // g2d.drawLine(10, 5, 350, 320);   // Draw line #1 with RED color

        // g2d.setColor(Color.BLUE);        // Draw a BLUE colored circle
        // g2d.drawOval(100, 50, 200, 200); //Oval (X, Y, r, r) => Cirle [Note: Draws the outline of an oval. The result is a circle or ellipse that fits within the rectangle specified by the x, y, width, and height arguments.]

        // int pix= img.getRGB(10, 5);         // Read the color at (x, y) [color of the 1st line]
        // Color pixcolor = new Color(pix);    // Convert the color value into Color Structure
        // System.out.println(pixcolor.getRed());    // Demo: showing how to get the R.G.B.A values
        // System.out.println(pixcolor.getGreen());
        // System.out.println(pixcolor.getBlue());
        // System.out.println(pixcolor.getAlpha());

        // g2d.setColor(pixcolor);        // Use the read color at (10,5) which was the color of the drawn line #1
        // g2d.drawLine(100, 50, 300, 50); // Draw Line #2 with the same color


        // Icon icon = new ImageIcon(img);
        // JLabel label = new JLabel(icon);

        // JFrame myFrame = new JFrame("Java Frame");
        // myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // myFrame.getContentPane().add(label);
        // myFrame.pack();
        // myFrame.setVisible(true);

    }

    public static JPanel createSelectControlPanel() {
        JPanel textPanel = new JPanel(new GridLayout(0,1));
        JLabel textLabel = new JLabel("Click Area to select points");
        JButton drawButton = new JButton("Draw Points");
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (points.size() < 3) {
                    JOptionPane.showMessageDialog(myFrame, "You have not selected a minimum of 3 points");
                } else {
                    showSelectFunctionDialog();
                }
            }
        });
        textPanel.add(textLabel);
        textPanel.add(drawButton);
        return textPanel;
    }

    public static JPanel createPolygonControlPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(0,1));
        JLabel label = new JLabel("Select Algorithm");
        JButton floodFill4Button = new JButton("Flood Fill 4");
        floodFill4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Color background = JColorChooser.showDialog(null, "Choose Fill Color",
                        null);
                JOptionPane.showMessageDialog(myFrame, "Click a area in the polygon for a seed point");
                myFrame.getContentPane().getComponent(0).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Point seedPoint = new Point(e.getX(), e.getY());
                        showFloodFill4Dialog(background, seedPoint);
                    }
                });
            }
        });
        JButton floodFill8Button = new JButton("Flood Fill 8");
        floodFill8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Color background = JColorChooser.showDialog(null, "Choose Fill Color",
                        null);
                JOptionPane.showMessageDialog(myFrame, "Click a area in the polygon for a seed point");
                myFrame.getContentPane().getComponent(0).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Point seedPoint = new Point(e.getX(), e.getY());
                        showFloodFill8Dialog(background, seedPoint);
                    }
                });
            }
        });
        JButton polygonFillButton = new JButton("Polygon Fill Button");
        polygonFillButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final Color background = JColorChooser.showDialog(null, "Choose Fill Color",
                        null);
                showPolygonFillDialog(background);
            }
        });
        buttonPanel.add(label);
        buttonPanel.add(floodFill4Button);
        buttonPanel.add(floodFill8Button);
        buttonPanel.add(polygonFillButton);
        return buttonPanel;
    }


    public static JLabel createSelectPointsBuffer() {
        JLabel label = paintPoints(img, points);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(new Point(e.getX(), e.getY()));
                JLabel imgPanel = createSelectPointsBuffer();
                JPanel controlPanel = createSelectControlPanel();
                display(imgPanel, controlPanel);
            }
        });
        return label;
    }


    public static void display(JLabel imgPanel, JPanel textPanel) {
        myFrame.getContentPane().removeAll();
        JPanel main = new JPanel();
        main.add(imgPanel);
        main.add(textPanel);

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.getContentPane().add(main);
        myFrame.pack();
        myFrame.setVisible(true);
    }

    public static void showFloodFill4Dialog(Color fc, Point seedPoint) {
        int x = (int) seedPoint.getX();
        int y = (int) seedPoint.getY();
        floodFill4(x, y, img.getRGB(x,y), fc.getRGB());
        Icon icon = new ImageIcon(img);
        JLabel label = new JLabel(icon);
        JPanel buttonPanel = new JPanel();
        JButton redoButton = new JButton("Reselect Points");
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                points.clear();
                showSelectPointDialog();
            }
        });
        buttonPanel.add(redoButton);
        display(label, buttonPanel);
    }

    public static void showFloodFill8Dialog(Color fc, Point seedPoint) {
        int x = (int) seedPoint.getX();
        int y = (int) seedPoint.getY();
        floodFill8(x, y, img.getRGB(x, y), fc.getRGB());
        Icon icon = new ImageIcon(img);
        JLabel label = new JLabel(icon);
        JPanel buttonPanel = new JPanel();
        JButton redoButton = new JButton("Reselect Points");
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                points.clear();
                showSelectPointDialog();
            }
        });
        buttonPanel.add(redoButton);
        display(label, buttonPanel);
    }

    public static void showPolygonFillDialog(Color fc) {
        polygonFill(fc);
        Icon icon = new ImageIcon(img);
        JLabel label = new JLabel(icon);
        JPanel buttonPanel = new JPanel();
        JButton redoButton = new JButton("Reselect Points");
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                points.clear();
                showSelectPointDialog();
            }
        });
        buttonPanel.add(redoButton);
        display(label, buttonPanel);
    }

    public static void showSelectPointDialog() {
        JLabel label = createSelectPointsBuffer();
        JPanel text = createSelectControlPanel();
        display(label, text);
    }

    public static void showSelectFunctionDialog() {
        JLabel polygonPane = drawPolygon(img, points);
        JPanel buttonPanel = createPolygonControlPanel();
        display(polygonPane, buttonPanel);
    }

    public static JLabel drawPolygon(BufferedImage img, ArrayList<Point> points) {
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);  // Creating a white background
        g2d.fillRect(0, 0, 720, 480);
        g2d.setColor(Color.black);
        int size = points.size();
        int[] x = new int[size];
        int[] y = new int[size];
        int i = 0;
        for (Point point:points) {
            x[i] = (int) point.getX();
            y[i] = (int) point.getY();
            i++;
        }
        g2d.drawPolygon(x, y, i);
        Icon icon = new ImageIcon(img);
        JLabel label = new JLabel(icon);
        return label;
    }

    public static JLabel paintPoints(BufferedImage img, ArrayList<Point> points) {
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);  // Creating a white background
        g2d.fillRect(0, 0, 720, 480);
        g2d.setColor(Color.RED);
        for (Point point : points) {
            g2d.drawOval((int) point.getX(), (int) point.getY(), 4, 4);
        }

        Icon icon = new ImageIcon(img);
        JLabel label = new JLabel(icon);
        return label;
    }

    public static boolean isInCanvas(int x, int y) {
        return (x < 720 && y < 480 && x >= 0 && y >= 0);
    }


    public static void floodFill4(int x, int y, int ic, int fc) {
        if (isInCanvas(x,y)) {
            int color = img.getRGB(x, y);
            if (color == ic) {
                img.setRGB(x, y, fc);
                floodFill4(x + 1, y, ic, fc);
                floodFill4(x - 1, y, ic, fc);
                floodFill4(x, y + 1, ic, fc);
                floodFill4(x, y - 1, ic, fc);
            }
        }
    }

    //This will overflow because it bleeds out of the polygon
    public static void floodFill8(int x, int y, int ic, int fc) {
        if (isInCanvas(x,y)) {
            int color = img.getRGB(x, y);
            if (color == ic) {
                img.setRGB(x, y, fc);
                floodFill8(x + 1, y, ic, fc);
                floodFill8(x - 1, y, ic, fc);
                floodFill8(x, y + 1, ic, fc);
                floodFill8(x, y - 1, ic, fc);
                floodFill8(x+1,y+1, ic, fc);
                floodFill8(x-1,y-1, ic, fc);
                floodFill8(x-1,y+1, ic, fc);
                floodFill8(x+1,y-1, ic, fc);
            }
        }
    }

    public static void polygonFill(Color fc) {
        int n = points.size();
        System.out.println(n);
        int edges = 0;
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(fc);
        int activeEdges; // number of  active edges
        // active edge table (indexes into edge table)
        int[] aedge;
        double[] xEdge = new double[n];
        int[] yMin = new int[n];
        int[] yMax = new int[n];
        double[] mInv = new double[n];
        int[] x = new int[n];
        int[] y = new int[n];
        int i = 0;
        for (Point point:points) {
            x[i] = (int) point.getX();
            y[i] = (int) point.getY();
            i++;
        }

        int yBoundMin = y[0];
        int yBoundMax = y[n-1];
        System.out.println(i);
        for (int num:y) {
            if (num < yBoundMin) {
                yBoundMin = num;
            }
            if (num > yBoundMax) {
                yBoundMax = num;
            }
        }


        int length, iplus1, x1, x2, y1, y2;
        for (i=0; i<n; i++) {
            iplus1 = i==n-1?0:i+1;
            y1 = y[i];
            y2 = y[iplus1];
            x1 = x[i];
            x2 = x[iplus1];
            if (y1==y2) {
                continue; //ignore horizontal lines
            }
            if (y1>y2) { // swap ends
                int tmp = y1;
                y1=y2;
                y2=tmp;
                tmp=x1;
                x1=x2;
                x2=tmp;
            }
            double slope = (double)(x2-x1)/(y2-y1);
            xEdge[edges] = x1;
            yMin[edges] = y1;
            yMax[edges] = y2;
            mInv[edges] = slope;
            edges++;
        }
        aedge = new int[edges];
        int[] sedge = new int[edges];

        for (i=0; i<edges; i++) {
            int index = sedge[i];
            System.out.println(i+"	"+xEdge[index]+"  "+yMin[index]+"  "+yMax[index] + "  " + mInv[index] );
        }

        activeEdges = 0;
        for (int yit = yBoundMin; yit < yBoundMax; yit++) {
            i = 0;
            while (i<activeEdges) {
                int index = aedge[i];
                if (yit<=yMin[index] || yit>=yMax[index]) {
                    for (int j=i; j<activeEdges-1; j++)
                        aedge[j] = aedge[j+1];
                    activeEdges--;
                } else
                    i++;
            }

            for (i = 0; i < edges; i++) {
                if (yit==yMin[i]) {
                    int index = 0;
                    while (index<activeEdges && xEdge[i]>xEdge[aedge[index]])
                        index++;
                    for (int j=activeEdges-1; j>=index; j--)
                        aedge[j+1] = aedge[j];
                    aedge[index] = i;
                    activeEdges++;
                }
            }


            for (i = 0; i < activeEdges; i += 2) {
                x1 = (int)(xEdge[aedge[i]] + 0.5);
                x2 = (int)(xEdge[aedge[i+1]] - 0.5);
                g2d.drawLine(x1,yit,x2,yit);
            }


            int index;
            double xSort=-Double.MAX_VALUE, xSort2;
            boolean sorted = true;
            for (i=0; i<activeEdges; i++) {
                index = aedge[i];
                xSort2 = xEdge[index] + mInv[index];
                xEdge[index] = xSort2;
                if (xSort2<xSort) sorted = false;
                xSort = xSort2;
            }
            if (!sorted) {
                int min, tmp;
                for (i = 0; i < activeEdges; i++) {
                    min = i;
                    for (int j = i; j < activeEdges; j++)
                        if (xEdge[aedge[j]] < xEdge[aedge[min]]) min = j;
                    tmp = aedge[min];
                    aedge[min] = aedge[i];
                    aedge[i] = tmp;
                }
            }
        }
    }
}