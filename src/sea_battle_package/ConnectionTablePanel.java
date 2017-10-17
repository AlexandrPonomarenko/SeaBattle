package sea_battle_package;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
    private String [][] dataUser;
    private ListSelectionModel selModel;
    private String changeName = "";
    private String [][] lastname;


    public ConnectionTablePanel(int x, int y){
        setPreferredSize(new Dimension(x, y));
        setLayout(new BorderLayout());
        eventListenerList = new EventListenerList();
        headers = new String[]{"Name", "CreateGame", "IP"};
//        data = new String[][]{{"Alex" , "23.14.17.14:35", "192.102.20.2"},{"Oleg" , "23.14.17,16:35", "202.102.20.2"},
//                {"Sergey" , "23.14.17,15:35", "144.102.20.2"} };
        defaultSettings();
        addTable();
        changeCell();
        updateTable();
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
//        tableUser.setFillsViewportHeight(true);
        add(tableUser.getTableHeader(), BorderLayout.NORTH);
        add(tableUser,BorderLayout.CENTER);
//        add(scrollPane, BorderLayout.CENTER);
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

    private void setDefaultPicture(){
        this.removeAll();
        this.revalidate();
        repaint();
    }
    public void setDataUser(String[][] dataUserArray){
        if(dataUserArray.length == 1 && dataUserArray[0][0].equals("default")){
//            setDefaultPicture();
            defaultSettings();
            System.out.println(dataUser[0][0]+ " EEEEEEEEEEEEEEeee ");
            tableUser.setModel(new DefaultTableModel(dataUser, headers));
        }else if(dataUserArray.length > 0 && !dataUserArray[0][0].equals("default")){
            dataUser = dataUserArray;
            System.out.println(dataUser.length + " TTTTTTTTTTTTTTT " + dataUser[0].length);
            for (int i = 0; i < dataUser.length; i++) {
                for (int j = 0; j < dataUser[i].length; j++) {
                    System.out.println(dataUser[i][j] + " ЭТО В МЕТОДЕ setDataUser");
                }
            }
            tableUser.setModel(new DefaultTableModel(dataUser, headers));
        }
    }

    private void updateTable(){
        System.out.println(dataUser.length + " 123123123 " + dataUser[0].length);

        for (int i = 0; i < dataUser.length; i++) {
            for (int j = 0; j < dataUser[i].length; j++) {
                System.out.println(dataUser[i][j] + " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }
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
