package sea_battle_package;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandr on 03.07.2017.
 */
public class FirstGamePanel extends JPanel  {
    private JLabel time;
    //private JLabel yourField;
    // private JLabel myField;
    private JLabel status;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private Polygon poligon;
    int [] xCor;
    int [] yCor;

    public FirstGamePanel(int w, int h){
        setPreferredSize(new Dimension(w,h));

        gbl = new GridBagLayout();
        setLayout(gbl);
        time = new JLabel("Time");
        // yourField = new JLabel("YourField");
        //myField = new JLabel("MyField");
        status = new JLabel("Status: ");

        gbc = new GridBagConstraints(1,0,1,1,1,1,GridBagConstraints.NORTH,GridBagConstraints.NONE,
                new Insets(0,0,0,0),0,0);

        gbl.setConstraints(time, gbc);
        add(time);

        gbc = new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,
                new Insets(0,0,0,0),0,0);
        gbl.setConstraints(status, gbc);
        add(status);

        //setBackground(Color.ORANGE);
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.orange);
        setCoordinates(getWidth(),getHeight(),5);
        System.out.println(getWidth()+" ===== " + getHeight());
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.red);
        poligon = new Polygon(xCor,yCor,5);
//        g.drawPolygon(poligon);
        g.fillPolygon(poligon);
    }

    private void setCoordinates(int w, int h, int v)
    {
        xCor = new int[v];
        yCor = new int[v];

        for(int i = 0; i < v; i++)
        {
            if (i == 0) {
                xCor[i] = (w / 2) - 20;
                yCor[i] = 0;
                System.out.println( xCor[i] + " I = 0 " + yCor[i]);
            } else if (i == 1) {
                xCor[i] = (w / 2) + 20;
                yCor[i] = 0;
                System.out.println(xCor[i] + " i == 1 " + yCor[i]);
            } else if (i == 2) {
                xCor[i] = (w / 2) + 60;
                yCor[i] = h;
                System.out.println(xCor[i] + " i == 2 " + yCor[i]);
            } else if (i == 3) {
                xCor[i] = (w / 2)  - 60;
                yCor[i] = h;
                System.out.println(xCor[i] + " i == 3 " + yCor[i]);
            } else if (i == 4) {
                xCor[i] = (w / 2) - 20;
                yCor[i] = 0;
                System.out.println(xCor[i] + " i == 4 " + yCor[i]);
            }
        }
    }
}
