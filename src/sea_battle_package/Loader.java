package sea_battle_package;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandr on 01.07.2017.
 */
public class Loader extends JFrame{

    private int width = 1000;
    private int height = 500;
    private StartPanel sp;
    public Loader() {
        setSize(width,height);
//        setMinimumSize(new Dimension(width / 10, height / 10));
//        setLayout(new BorderLayout());
//        sp = new StartPanel(width, height);
        setStartPanel();
    }

    private void setStartPanel()
    {
        sp = new StartPanel(width, height);
        add(sp);
    }
}
