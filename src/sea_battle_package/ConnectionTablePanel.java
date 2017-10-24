package sea_battle_package;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;

/**
 * Created by Alexandr on 24.07.2017.
 */
public class ConnectionTablePanel extends JPanel {

    private JTable tableUser;
    private String [] headers;
    private JScrollPane scrollPane;
    private EventListenerList eventListenerList;
    private String [][] dataUser;
    private ListSelectionModel selModel;
    private String changeName = "";
    private ImagePanel imagePanel;
    private int widht, height;

    public ConnectionTablePanel(int x, int y){
        widht = x;
        height = y;
        setPreferredSize(new Dimension(widht, height));
        setLayout(new BorderLayout());
        eventListenerList = new EventListenerList();
        headers = new String[]{"Name", "CreateGame", "IP"};
        defaultSettings();
        addTable();
        changeCell();
    }

    private void defaultSettings(){
        dataUser = new String[1][3];
        dataUser[0][0] = "NoName";
        dataUser[0][1] = "- // -";
        dataUser[0][2] = "- // - - // - - // -";
    }
    private void addTable(){
        tableUser = new JTable(dataUser, headers);
        scrollPane = new JScrollPane(tableUser);
        add(tableUser.getTableHeader(), BorderLayout.NORTH);
        add(tableUser,BorderLayout.CENTER);
    }

    private void changeCell(){
        selModel = tableUser.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRows = tableUser.getSelectedRow();
                TableModel model = tableUser.getModel();
                Object value = model.getValueAt(selectedRows, 0);
                changeName = value.toString();
                changeNameTable(new EventObjectClient(e, changeName));
            }
        });
    }

    private void setDefaultPanel(){
        this.removeAll();
        this.revalidate();
        imagePanel = new ImagePanel(widht,height);
        add(imagePanel);
        repaint();
    }

    public void setDataUser(String[][] dataUserArray){
        if(dataUserArray.length == 1 && dataUserArray[0][0].equals("default")){
            setDefaultPanel();
        }else if(dataUserArray.length > 0 && !dataUserArray[0][0].equals("default")){
            dataUser = dataUserArray;
            tableUser.setModel(new DefaultTableModel(dataUser, headers));
        }
    }

    public void addEventListenerObjectClient(EventListenerObjectClient listener) {
        eventListenerList.add(EventListenerObjectClient.class, listener);
    }

    private void changeNameTable(EventObjectClient evt) {
        Object[] listeners = eventListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerObjectClient.class) {
                ((EventListenerObjectClient) listeners[i + 1]).changeNameUser(evt);
            }
        }
    }
}
