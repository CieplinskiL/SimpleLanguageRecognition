import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUI {


    private java.util.List<Perceptron> perceptrons;
    public GUI(java.util.List<Perceptron> l, HashMap<String,Double> alf, String[] letters){
        this.perceptrons = l;
        JFrame frame = new JFrame();
        frame.setSize(700,500);
        frame.setLayout(null);

        JTextArea textArea = new JTextArea();
        textArea.setSize(650,350);
        textArea.setLocation(17,90);
        textArea.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setSize(textArea.getSize());
        scrollPane.setLocation(textArea.getLocation());

        JLabel label = new JLabel("Language:");
        label.setFont(new Font("tt",Font.BOLD,20));
        label.setSize(100,50);
        label.setLocation(40,35);

        JLabel resLabel = new JLabel("");
        resLabel.setFont(new Font("tt",Font.BOLD,20));
        resLabel.setSize(200,50);
        resLabel.setLocation(160,36);

        JButton button = new JButton("Find Language");
        button.setFont(new Font("tt",Font.BOLD,17));
        button.setSize(frame.getWidth(),40);
        button.setLocation(0,0);
        button.setFocusable(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = textArea.getText();
                if(str.isEmpty())
                    resLabel.setText("No text provided ");
                else{
                    Vector v = Vector.createUserVector(str,alf,letters);
                    List<String> tmp = new ArrayList<>();
                    for (Perceptron p : perceptrons) {
                        if (p.countNet(v) == 1)
                            tmp.add(p.name);
                    }
                    if (!tmp.isEmpty()) {
                        resLabel.setText(tmp.get((int) (Math.random() * tmp.size())));
                    } else {

                        double d = 0;
                        Perceptron fin = null;
                        for (Perceptron p: perceptrons) {
                            if(p.countFullNet(v)>d ||  d ==0){
                                d = p.countFullNet(v);
                                fin = p;
                            }
                            resLabel.setText(fin.name);
                        }
                    }
                }
            }
        });


        frame.add(scrollPane); frame.add(label); frame.add(resLabel); frame.add(button);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
