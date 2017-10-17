package sea_battle_package;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandr on 01.07.2017.
 */
public class SeaBattle {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                JFrame frame = new Loader();
                frame.setTitle("Sea Battle");
                frame.setVisible(true);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

    }
}
