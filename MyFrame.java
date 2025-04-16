import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;

public class MyFrame extends JFrame implements ActionListener{

    JRadioButtonMenuItem circle;
    JRadioButtonMenuItem square;
    JRadioButtonMenuItem pen;
    JMenuItem color;
    JMenuItem clear;
    JToolBar jToolBar;
    DrawPanel drawPanel;
    JLabel label1;
    JLabel label2;

    JMenuItem open;
    JMenuItem saveAs;
    JMenuItem save;
    JMenuItem quit;
    JFileChooser fileChooser;
    Boolean isSaveOrOpen;
    File selectedFile;
    boolean chosen;

    public MyFrame() {

        this.chosen = false;
        this.isSaveOrOpen = false;
        this.selectedFile = null;

        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu draw = new JMenu("Draw");

        menu.add(file);
        menu.add(draw);

        JMenu figure = new JMenu("Figure");

        fileChooser = new JFileChooser();

        open = new JMenuItem("Open");
        saveAs = new JMenuItem("Save As");
        save = new JMenuItem("Save");
        quit = new JMenuItem("Quit");
        circle = new JRadioButtonMenuItem("Circle");
        square = new JRadioButtonMenuItem("Square");
        pen = new JRadioButtonMenuItem("Pen");
        color = new JMenuItem("Color");
        clear = new JMenuItem("Clear");

        file.add(open);
        file.add(saveAs);
        file.add(save);
        file.add(quit);
        draw.add(figure);
        figure.add(circle);
        figure.add(square);
        figure.add(pen);
        draw.add(color);
        draw.add(clear);

        circle.addActionListener(this);
        square.addActionListener(this);
        pen.addActionListener(this);
        color.addActionListener(this);
        clear.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        open.addActionListener(this);
        quit.addActionListener(this);

        file.setMnemonic('f');
        draw.setMnemonic('d');
        save.setMnemonic('s');
        saveAs.setMnemonic('a');
        quit.setMnemonic('q');
        circle.setMnemonic('c');
        square.setMnemonic('r');
        color.setMnemonic('c');
        clear.setMnemonic('c');

        circle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        square.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
        pen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
        clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.SHIFT_DOWN_MASK + KeyEvent.CTRL_MASK));
        color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.SHIFT_DOWN_MASK + KeyEvent.ALT_DOWN_MASK));

        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK + KeyEvent.ALT_DOWN_MASK));


        drawPanel = new DrawPanel(MyFrame.this);
        this.add(drawPanel);

        jToolBar = new JToolBar();
        label1 = new JLabel("Circle");
        label2 = new JLabel("New");

        jToolBar.add(label1);
        jToolBar.add(Box.createHorizontalGlue());
        jToolBar.add(label2, BorderLayout.EAST);

        this.add(jToolBar, BorderLayout.SOUTH);

        this.setJMenuBar(menu);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simple Draw");
        this.setSize(700, 700);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == square) {
            drawPanel.setActualShape(1);
            label1.setText("Square");
        }

        if (e.getSource() == circle) {
            drawPanel.setActualShape(2);
            label1.setText("Circle");
        }

        if (e.getSource() == pen) {
            drawPanel.setActualShape(3);
            label1.setText("Pen");

        }

        if (e.getSource() == clear) {
            drawPanel.setArr(new ArrayList<Shape>());
            drawPanel.repaint();

        }
        if (e.getSource() == color) {
            Color selectedColor = JColorChooser.showDialog(this, "Choose a Color", this.getBackground());
            drawPanel.setPenColor(selectedColor);
        }

        if (e.getSource() == saveAs) {
            chooseFile();
        }

        if (e.getSource() == save) {
            if (isSaveOrOpen) {
                try {
                    drawPanel.save(selectedFile.toString());
                    //label2.setText(drawPanel.fileStatus());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                chooseFile();
            }
        }

        if (e.getSource() == open) {
            int a = fileChooser.showOpenDialog(this);
            if (a == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                isSaveOrOpen = true;

                try {
                    drawPanel.open(selectedFile.toString());
                    drawPanel.repaint();
                    label2.setText(drawPanel.fileStatus());
                    this.setTitle("Simple Draw: " + selectedFile.getName());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (e.getSource() == quit) {

            if (drawPanel.isSaved && !drawPanel.modified) {
                System.exit(0);
            } else {
                QuitFrame quitFrame = new QuitFrame(MyFrame.this, drawPanel);
            }
        }
    }
    public void chooseFile(){
        int a = fileChooser.showSaveDialog(this);
        if (a == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            isSaveOrOpen = true;

            try {
                drawPanel.save(selectedFile.toString());
                this.setTitle("Simple Draw: " + selectedFile.getName());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
