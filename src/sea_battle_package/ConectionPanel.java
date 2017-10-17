package sea_battle_package;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Alexandr on 24.07.2017.
 */
public class ConectionPanel extends JPanel{

//    private JLabel labelName;
    private JTextField textName;
    private JButton create;
    private JButton connect;
    public EventListenerList eventListenerList;
    private int array[][];
    private String nameTableUser;
    private Object objectNameUser;
    private Sound sound;
    private ArrayList<JButton> arrayListJButton;


    public ConectionPanel(int x, int y){
        setPreferredSize(new Dimension(x, y));
        setLayout(new GridBagLayout());
        arrayListJButton = new ArrayList<>();
        array = new int[10][10];
        nameTableUser = "";
        objectNameUser = new Object();
        eventListenerList = new EventListenerList();

        textName = new JTextField(10);
        textName.setText("Your name");
        add(textName, new GridBagConstraints(0,0,1,1,1,0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),0,0));

        setButtonPanel(create,"Create", 0);
        setButtonPanel(connect,"Connect", 1);

        addFocusListener(textName);
        sound = new Sound(new File("E:\\www\\SeaBattle\\sound_Three.wav"));
        sound.play();
        System.out.println("ПОСЛЕ ЗАПУСКА ПЕСНИ");
    }

    private void setButtonPanel(JButton button, String name, int weighty) {
        button = new JButton(name);
        arrayListJButton.add(button);
        turnOffButton(button);
        add(button, new GridBagConstraints(0,GridBagConstraints.RELATIVE,1,1,0,weighty,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),0,0));
        addActionListenerButton(button);
    }

    private boolean checkString(String stringName){
        if(stringName.length() > 3){
            return true;
        }
        return false;
    }

    private void turnOffButton(JButton but){
        but.setEnabled(false);
    }

    private void turnOnButton(JButton but){
        but.setEnabled(true);
    }

    private void addActionListenerButton(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getActionCommand().equals("Create")){
                    System.out.println("В начале кнопки СОЗДАТЬ");
//                    nameTableUser = textName.getText();
                    objectNameUser = textName.getText();
                    fireMyEvent(new MyEventObject());
                    goMyEvent(new MyEventObject(array));
                    sendNameUser(new EventObjectClient(objectNameUser));
                    sendString(new EventObjectSendString(e, e.getActionCommand().toString()));
                    sendMyName(new EventObjectSendString(textName.getText()));
                    sendConnection(new EventObjectClient(e.getActionCommand().toString()));
                    sendArrayShip(new EventObjectClient(array));
                    sound.stop();
                    System.out.println("В конце кнопки СОЗДАТЬ");
                }else{
                    System.out.println("В начале кнопки ПОДКЛЮЧИТСЯ");
                    System.out.println(nameTableUser + " ++++++++++++++ " + e.getActionCommand());
                    objectNameUser = textName.getText() + "," + nameTableUser;
                    fireMyEvent(new MyEventObject());
                    goMyEvent(new MyEventObject(array));
                    sendNameUser(new EventObjectClient(objectNameUser));
                    sendString(new EventObjectSendString(e, e.getActionCommand().toString()));
                    sendMyName(new EventObjectSendString(textName.getText()));
                    sendConnection(new EventObjectClient("ConnectUser"));
                    sendArrayShip(new EventObjectClient(array));
                    sound.stop();
                    System.out.println("В конце кнопки ПОДКЛЮЧИТСЯ");
                }
            }
        });
    }

    private void addFocusListener(JTextField tf){
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                tf.setText("");
                addDocListener(tf);
            }
        });
    }

    private void addDocListener(JTextField tf)
    {
        tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                if(checkString(tf.getText())){
                    turnOnButton(arrayListJButton.get(0));
//                    turnOnButton(arrayListJButton.get(1));
                }else{
                    turnOffButton(arrayListJButton.get(0));
//                    turnOffButton(arrayListJButton.get(1));
                }

                if(!nameTableUser.equals("") && checkString(tf.getText())){
                    turnOnButton(arrayListJButton.get(1));
                }else{
                    turnOffButton(arrayListJButton.get(1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                if(checkString(tf.getText())){
                    turnOnButton(arrayListJButton.get(0));
                    turnOnButton(arrayListJButton.get(1));
                }else{
                    turnOffButton(arrayListJButton.get(0));
                    turnOffButton(arrayListJButton.get(1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {

            }
        });
    }

    public void setNameTableUser(String selectName){
        if(nameTableUser.length() == 0) {
            nameTableUser = selectName;
        }else{
            nameTableUser = "";
            nameTableUser = selectName;
            turnOnButton(arrayListJButton.get(1));
        }
        System.out.println(nameTableUser + " NNNNAAAAAAMMMMEEEE");
    }
    public void setShip(int array[][]){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " + ");
            }
            System.out.println();
        }
        this.array = array;
    }

    public void addMyEventListener(MyEventListener listener) {
        listenerList.add(MyEventListener.class, listener);
    }

    public void addEventListenerObjectClient(EventListenerObjectClient listener) {
        listenerList.add(EventListenerObjectClient.class, listener);
    }

    public void addEventListenerObjectSendString(EventListenerSendString listener) {
        listenerList.add(EventListenerSendString.class, listener);
    }

    public void removeMyEventListener(MyEventListener listener) {
        listenerList.remove(MyEventListener.class, listener);
    }

    private void fireMyEvent(MyEventObject evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == MyEventListener.class) {
                ((MyEventListener) listeners[i + 1]).clickButton(evt);
            }
        }
    }

    private void goMyEvent(MyEventObject evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == MyEventListener.class) {
                ((MyEventListener) listeners[i + 1]).getArray(evt);
            }
        }
    }

    private void sendConnection(EventObjectClient evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerObjectClient.class) {
                ((EventListenerObjectClient) listeners[i + 1]).sendCommandConnection(evt);
            }
        }
    }

    private void sendArrayShip(EventObjectClient evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerObjectClient.class) {
                ((EventListenerObjectClient) listeners[i + 1]).sendArrayCoordinatesShips(evt);
            }
        }
    }

    private void sendNameUser(EventObjectClient evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerObjectClient.class) {
                ((EventListenerObjectClient) listeners[i + 1]).sendNameUser(evt);
            }
        }
    }

    private void sendString(EventObjectSendString evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerSendString.class) {
                ((EventListenerSendString) listeners[i + 1]).sendStringCreateOrConnect(evt);
            }
        }
    }

    private void sendMyName(EventObjectSendString evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerSendString.class) {
                ((EventListenerSendString) listeners[i + 1]).sendMyNameOrMyOpponent(evt);
            }
        }
    }
}
