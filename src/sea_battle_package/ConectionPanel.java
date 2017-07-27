package sea_battle_package;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Alexandr on 24.07.2017.
 */
public class ConectionPanel extends JPanel{

    private JLabel labelName;
    private JTextField textName;
    private JButton create;
    private JButton connect;
    public EventListenerList eventListenerList;
    private int array[][];

    private int realWidth;
    private int realHeight;
    private boolean flagColor;

    public ConectionPanel(int x, int y){
        setPreferredSize(new Dimension(x, y));
        setLayout(new GridBagLayout());


        eventListenerList = new EventListenerList();

//        labelName = new JLabel("Name:");
//        add(labelName, new GridBagConstraints(0,0,1,1,0.5,0.5,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,
//                new Insets(0,0,0,0),0,0));

        textName = new JTextField(10);
        textName.setText("Your name");
        add(textName, new GridBagConstraints(0,0,1,1,1,0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),0,0));

        setButtonPanel(create,"Create", 0);
        setButtonPanel(connect,"Connect", 1);

        addFocusListener(textName);

    }

//    public void paint(Graphics g){
//        setRealWidthAndHeight(getWidth(),getHeight());
////        g.fillRect(0,0,getWidth(),getHeight());
////        drawStringStart(g, "Create");
//
//    }

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

//    private void drawStringStart(Graphics g, String nameButton) {
//        if(flagColor) {
//            g.setColor(new Color(139,0,0));
//        }else{g.setColor(Color.WHITE);}
//        Font font = new Font("San Francisco", Font.BOLD | Font.ITALIC, 12);
//        int c = font.getSize();
//        g.setFont(font);
//        g.drawString(nameButton, realWidth, 20);
//    }
//
//    private void setRealWidthAndHeight(int w, int h){
//        realWidth = w;
//        realHeight = h;
//    }

    private void addActionListenerButton(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Create")){
                    fireMyEvent(new MyEventObject());
                    goMyEvent(new MyEventObject(array));
                }else{
                    fireMyEvent(new MyEventObject());
                    goMyEvent(new MyEventObject(array));
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

    private void goMyEvent(MyEventObject evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == MyEventListener.class) {
                ((MyEventListener) listeners[i + 1]).getArray(evt);
            }
        }
    }
}
