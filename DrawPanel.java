import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DrawPanel extends JPanel implements KeyListener, MouseMotionListener, MouseListener{
    int actualShape;
    ArrayList<Shape> arr;
    Color penColor;
    Boolean isSaved;
    boolean modified;
    MyFrame myFrame;
    boolean pressed;
    public DrawPanel(MyFrame myFrame){
        this.myFrame = myFrame;

        this.setBounds(0, 0, 700, 700);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);

        this.actualShape = 0;
        this.penColor = Color.BLACK;
        this.isSaved = false;
        this.modified = false;
        this.pressed = false;
        arr = new ArrayList<Shape>();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Shape i : arr) {
            if (i.shape != ShapeEnum.PEN) {
                g.setColor(i.color);
                if (i.shape == ShapeEnum.SQUARE) {
                    g.fillRect(i.x - (i.radious/2) , i.y - (i.radious/2), i.radious, i.radious);
                }

                if (i.shape == ShapeEnum.CIRCLE) {
                    g.fillOval(i.x - (i.radious/2), i.y - (i.radious/2), i.radious, i.radious);
                }
            } else {
                g.setColor(i.color);
                g.fillOval(i.x, i.y, 5, 5);
            }
        }
    }

    public void open(String file) throws IOException{

        arr = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;

        while((line = bufferedReader.readLine()) != null){
            String[] splited = line.split("\\s+");

            if(splited.length == 4){
                ShapeEnum shape;
                switch(splited[2]){
                    case "CIRCLE" -> shape = ShapeEnum.CIRCLE;
                    case "SQUARE" -> shape = ShapeEnum.SQUARE;
                    case "PEN" -> shape = ShapeEnum.PEN;
                    default -> shape = null;
                }

                arr.add(new Shape(Integer.valueOf(splited[0]), Integer.valueOf(splited[1]), shape, new Color(Integer.valueOf(splited[3])) ));
            }
        }
        isSaved = true;
        modified = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 112) {

            if (actualShape == 1) {
                Color color = new Color(
                        (int) (Math.random() * 255),
                        (int) (Math.random() * 255),
                        (int) (Math.random() * 255)
                );
                arr.add(new Shape(getMousePosition().x, getMousePosition().y, ShapeEnum.SQUARE, color));

                if(myFrame.isSaveOrOpen){
                    myFrame.label2.setText("Modified");
                    modified = true;
                }
                repaint();
            }
            if (actualShape == 2) {

                Color color = new Color(
                        (int) (Math.random() * 255),
                        (int) (Math.random() * 255),
                        (int) (Math.random() * 255)
                );
                arr.add(new Shape(getMousePosition().x, getMousePosition().y, ShapeEnum.CIRCLE, color));
                if(myFrame.isSaveOrOpen){
                    myFrame.label2.setText("Modified");
                    modified = true;
                }
                repaint();
            }
        }

        if(e.getKeyChar() == 'd' && pressed){
            System.out.println("ta");
            int x = getMousePosition().x;
            int y = getMousePosition().y;
            ArrayList<Shape> arrTemp = new ArrayList<Shape>();

            for(Shape i : arr){
                if(i.shape != ShapeEnum.PEN){
                    if(x < i.x + 30 && x > i.x - 30 && y < i.y + 30 && y > i.y - 30){
                    }else {
                        arrTemp.add(i);
                        if(myFrame.isSaveOrOpen){
                            myFrame.label2.setText("Modified");
                            modified = true;
                        }
                    }
                }else
                    arrTemp.add(i);
            }
            arr = arrTemp;
            repaint();
        }
    }


    public String fileStatus(){
        String status;
        if(isSaved)
            status = "Saved";
        else
            status = "New";
        return status;
    }

    public void save(String file) throws IOException {
        FileWriter writer = new FileWriter(file);

        for(Shape i : arr)
            writer.write(i.x + " " + i.y + " " + i.shape + " " + i.color.getRGB() + "\n");
        myFrame.label2.setText("Saved");
        isSaved= true;
        modified = false;
        writer.close();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (actualShape == 3) {
            arr.add(new Shape(e.getX(), e.getY(), ShapeEnum.PEN, penColor));
            if(myFrame.isSaveOrOpen){
                myFrame.label2.setText("Modified");
                modified = true;
            }
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void setActualShape(int actualShape) {
        this.actualShape = actualShape;
    }

    public void setArr(ArrayList<Shape> arr) {
        this.arr = arr;
    }

    public void setPenColor(Color penColor) {
        this.penColor = penColor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
