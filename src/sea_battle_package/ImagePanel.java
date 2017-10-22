package sea_battle_package;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandr on 22.10.2017.
 */
public class ImagePanel extends JPanel {
    Image image;
    public ImagePanel(int width, int height){
        setSize(width,height);
//        image = new ImageIcon("\\SeaBattle\\src\\sea_battle_package\\no_Created_Games.png").getImage();
        image = new ImageIcon("\\E:\\www\\SeaBattle\\no_Created_Games.png").getImage();
    }
    public void paint(Graphics g){
        g.drawImage(image,0,0,null);
    }
}
