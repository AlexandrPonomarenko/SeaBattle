package sea_battle_package;

import com.sun.jmx.remote.internal.ArrayQueue;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Alexandr on 03.07.2017.
 */
public class PanelButton extends JPanel implements ActionListener{
    private JButton buttonOneShip;
    private JButton buttonTwoShip;
    private JButton buttonThreeShip;
    private JButton buttonFourShip;
    private JButton buttonNew;
    private JButton buttonVerOrHor;
    private JButton go;
    private ArrayList<JButton> arrayList;
    private EventListenerList eventListenerList;
    private ArrayQueue<JButton> arrayQueue;

    public PanelButton(int w, int h){
        setPreferredSize(new Dimension(w,h));
        arrayList = new ArrayList<>();
        arrayQueue = new ArrayQueue<>(7);
        eventListenerList = new EventListenerList();
        setLayout(new GridBagLayout());
        setBackground(new Color(192,192,192));

        setButtonPanel(buttonOneShip, "OneShip", 0);
        setButtonPanel(buttonTwoShip, "TwoShip", 0);
        setButtonPanel(buttonThreeShip, "ThreeShip", 0);
        setButtonPanel(buttonFourShip, "FourShip", 0);
        setButtonPanel(buttonVerOrHor, "VerOrHor", 0);
        setButtonPanel(buttonNew, "New", 0);
        setButtonPanel(go, "GO", 1);

        setAllAddActionListenerButton();
        setEnabledButton();
    }

    private void setButtonPanel(JButton button, String name, int weighty) {
        button = new JButton(name);
        arrayQueue.add(button);
        add(button, new GridBagConstraints(0,GridBagConstraints.RELATIVE,1,1,0,weighty,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,
                new Insets(0,0,0,0),0,0));
    }

    private void setAllAddActionListenerButton() {
        for (int i = 0; i < arrayQueue.size(); i++) {
            arrayQueue.get(i).addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireMyEvent(new MyEventObject(e, e.getActionCommand()));
    }

    public void addMyEventListener(MyEventListener listener)
    {
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

    public void turnOffButton(int stateButton) {
        if(stateButton == 1){
            arrayQueue.get(0).setEnabled(false);
            if(checkButton()) {
                arrayQueue.get(4).setEnabled(false);
                arrayQueue.get(6).setEnabled(true);
            }
        }else if(stateButton == 2) {
            arrayQueue.get(1).setEnabled(false);
            if(checkButton()) {
                arrayQueue.get(4).setEnabled(false);
                arrayQueue.get(6).setEnabled(true);
            }
        }else if(stateButton == 3) {
            arrayQueue.get(2).setEnabled(false);
            if(checkButton()) {
                arrayQueue.get(4).setEnabled(false);
                arrayQueue.get(6).setEnabled(true);
            }
        }else if(stateButton == 4) {
            arrayQueue.get(3).setEnabled(false);
            if(checkButton()) {
                arrayQueue.get(4).setEnabled(false);
                arrayQueue.get(6).setEnabled(true);
            }
        }
    }

    private void setEnabledButton()
    {
        arrayQueue.get(6).setEnabled(false);
    }
    private boolean checkButton() {
        boolean flag = false;
        for(int i = 0;i < 4; i++) {
            if(arrayQueue.get(i).isEnabled() == false) {
                flag = true;
            }else flag = false;
        }
        return flag;
    }

    public void allTurnOn(){
        for(int i = 0;i < arrayQueue.size(); i++) {
            arrayQueue.get(i).setEnabled(true);
        }
    }
}
