/**
 * Created by NicholasMoran on 9/12/17.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;   // For BufferedImage
import java.util.ArrayList;
import javax.swing.*;      // To display the outcome// For color


public class DrawJGraph {
    final static JFrame myFrame = new JFrame();
    final static ArrayList<Point> points = new ArrayList<>();
    final static BufferedImage img = new BufferedImage(720, 480, BufferedImage.TYPE_INT_RGB);
    public static void main(String[] args) {





        JLabel label = createSelectPointsBuffer();
        JPanel text = createSelectControlPanel();


        display(label, text);










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
        JPanel textPanel = new JPanel();
        JLabel textLabel = new JLabel("Click Area Please");
        JButton undoButton = new JButton("Draw Points");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (points.size() < 3) {
                    JOptionPane.showMessageDialog(myFrame, "You have not selected 3 points");
                } else {
                    JLabel polygonPane = drawPolygon(img, points);
                    JPanel buttonPanel = createPolygonControlPanel();
                    display(polygonPane, buttonPanel);
                }
            }
        });
        textPanel.add(textLabel);
        textPanel.add(undoButton);
        return textPanel;
    }

    public static JPanel createPolygonControlPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1,4));
        JButton floodFill4Button = new JButton("Flood Fill 4");
        JButton floodFill8Button = new JButton("Flood Fill 8");
        JButton polygonFillButton = new JButton("Polygon Fill Button");
        floodFill4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color background = JColorChooser.showDialog(null, "Change Button Background",
                        null);
                JOptionPane.showMessageDialog(myFrame, "Click a area in the polygon for a seed point");
                myFrame.getContentPane().getComponent(0).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Point seedPoint = new Point(e.getX(), e.getY());
                    }
                });
            }
        });
        buttonPanel.add(floodFill4Button);
        buttonPanel.add(floodFill8Button);
        buttonPanel.add(polygonFillButton);
        return buttonPanel;
    }

    public static JLabel createSelectPointsBuffer() {
        JLabel label = paintPoints(img, points);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

}