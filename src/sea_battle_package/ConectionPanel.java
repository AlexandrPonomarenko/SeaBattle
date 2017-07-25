package sea_battle_package;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexandr on 24.07.2017.
 */
public class ConectionPanel extends JPanel{

    private JLabel labelName;
    private JTextField textName;
    private JButton create;
    private JButton connect;
    private EventListenerList eventListenerList;
    private int array[][];

    public ConectionPanel(int x, int y){
        setPreferredSize(new Dimension(x, y));
        setLayout(new GridBagLayout());

        eventListenerList = new EventListenerList();

        labelName = new JLabel("Name:");
        add(labelName, new GridBagConstraints(0,0,1,1,0.5,0.5,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),0,0));

        textName = new JTextField(10);
        add(textName, new GridBagConstraints(1,0,1,1,0.5,0.5,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),0,0));

        setButtonPanel(create,"Create", 0);
        setButtonPanel(connect,"Connect", 1);

//        addActionListenerButton(create);
//        addActionListenerButton(connect);
    }



    private void setButtonPanel(JButton button, String name, int weighty) {
        button = new JButton(name);
        add(button, new GridBagConstraints(0,GridBagConstraints.RELATIVE,1,1,0,weighty,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),0,0));
        addActionListenerButton(button);
    }

    private boolean checkString(String stringName){
        if(stringName.length() < 3){
            return false;
        }
        return true;
    }

    private void addActionListenerButton(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setShip(int array[][]){
        this.array = array;
    }

    public void addMyEventListener(MyEventListener listener) {
        listenerList.add(MyEventListener.class, listener);
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
}
