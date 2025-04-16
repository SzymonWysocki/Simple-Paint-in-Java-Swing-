import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class QuitFrame extends JFrame implements ActionListener {
    JButton yes;
    JButton no;
    JLabel label;
    JTextArea textArea;
    boolean chosen;
    boolean save;
    MyFrame myFrame;
    DrawPanel drawPanel;
    public QuitFrame(MyFrame myFrame, DrawPanel drawPanel){

        this.myFrame = myFrame;
        this.drawPanel = drawPanel;

        no = new JButton("No");
        yes = new JButton("Yes");

        no.addActionListener(this);
        yes.addActionListener(this);

        textArea = new JTextArea("Do you want save?");

        this.add(textArea, BorderLayout.NORTH);
        this.add(yes, BorderLayout.WEST);
        this.add(no, BorderLayout.EAST);


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(200, 100);
        this.setVisible(true);

        save = false;
        chosen = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == yes){
            if(drawPanel.modified){
                try {
                    drawPanel.save(myFrame.selectedFile.toString());
                    save = true;
                    System.exit(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }else{
                myFrame.chooseFile();
                this.setVisible(false);
                System.exit(0);
            }

        }if(e.getSource() == no){
            System.exit(0);
        }
    }
}
