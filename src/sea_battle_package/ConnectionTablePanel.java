package sea_battle_package;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;

/**
 * Created by Alexandr on 24.07.2017.
 */
public class ConnectionTablePanel extends JPanel {

    private JTable tableUser;
    private String [] headers;
    private String [][] data;
    private JScrollPane scrollPane;
    private EventListenerList eventListenerList;

    public ConnectionTablePanel(int x, int y){
        setPreferredSize(new Dimension(x, y));
        setLayout(new BorderLayout());
        eventListenerList = new EventListenerList();
        headers = new String[]{"Name", "CreateGame", "IP"};
        data = new String[][]{{"Alex" , "23.14.17.14:35", "192.102.20.2"},{"Oleg" , "23.14.17,16:35", "202.102.20.2"},
                {"Sergey" , "23.14.17,15:35", "144.102.20.2"} };

        addTable();
    }

    private void addTable(){
        tableUser = new JTable(data, headers);
        scrollPane = new JScrollPane(tableUser);
//        tableUser.setFillsViewportHeight(true);
        add(tableUser.getTableHeader(), BorderLayout.NORTH);
        add(tableUser,BorderLayout.CENTER);
//        add(scrollPane, BorderLayout.CENTER);
    }

}
