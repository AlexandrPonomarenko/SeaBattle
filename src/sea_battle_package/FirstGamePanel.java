package sea_battle_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.event.EventListenerList;

/**
 * Created by Alexandr on 03.07.2017.
 */
public class FirstGamePanel extends JPanel  {
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private Polygon poligon;
    private Timer timer;
    private int [] xCor;
    private int [] yCor;
    private int timerSpeed = 1000;
    private int startNumberTimer = 30;
    private String myName = "myNoName";
    private String nameMyOpponent = "opponentNoName";
    private int controlTimer;
    private EventListenerList listenerList;

    public FirstGamePanel(int w, int h){
        setPreferredSize(new Dimension(w,h));
        setTimer();
        timerStop();
        listenerList = new EventListenerList();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(255,69,0));
        setCoordinates(getWidth(),getHeight(),5);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(new Color(184,134,11));
        poligon = new Polygon(xCor,yCor,5);
        g.fillPolygon(poligon);
        drawString(g, getWidth() / 2, 0, "Time", "top");
        drawString(g, getWidth() / 2, getHeight(), "Status", "bottom");
        drawString(g, getWidth() / 100 * 10, getHeight(), myName, "bottom");
        drawString(g, getWidth() / 100 * 90, getHeight(), nameMyOpponent, "bottom");
        drawNumber(g, getWidth(), 0, startNumberTimer);
    }

    private void drawString(Graphics g, int x, int y, String text, String state){
        g.setColor(Color.black);
        Font font = new Font("Serif", Font.PLAIN | Font.ITALIC, 16);
        int fontSize = font.getSize();
        g.setFont(font);
        if(state.equals("top")) {
            g.drawString(text, x - fontSize, y + fontSize);
        }else if(state.equals("bottom")){
            g.drawString(text, x - fontSize, y - fontSize);
        }
    }
    private void drawNumber(Graphics g, int x, int y, int number){
        repaint();
        g.setColor(Color.black);
        Font font = new Font("Serif", Font.PLAIN | Font.ITALIC, 30);
        int fontSize = font.getSize();
        g.setFont(font);
        g.drawString(Integer.toString(number), x / 2 - (fontSize / 2), y + fontSize * 2);
    }

    private void setTimer(){
        timer = new Timer(timerSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                if(startNumberTimer > 0){
                    startNumberTimer--;
                }else{
                    startNumberTimer = 30;
                    loseYourMove(new EventObjectSendShot(3));
                    timerStop();
                }
            }
        });
    }

    public void setTimerWait(){
        timerStop();
        if(controlTimer == 1){
            startNumberTimer = 30;
            timerStart();
        }else if(controlTimer == 0){
            startNumberTimer = 30;
            timerStop();
        }
    }

    public void setMyName(String myName){
        this.myName = myName;
        repaint();
    }

    public void setNameMyOpponent(String nameMyOpponent){
        this.nameMyOpponent = nameMyOpponent;
        repaint();
    }

    public void setControlTimer(int conTim){
        controlTimer = conTim;
        setTimerWait();
    }

    private void timerStop(){timer.stop();}

    private void timerStart(){timer.start();}

    private void setCoordinates(int w, int h, int v) {
        xCor = new int[v];
        yCor = new int[v];

        for(int i = 0; i < v; i++) {
            if (i == 0) {
                xCor[i] = (w / 2) - 20;
                yCor[i] = 0;
            } else if (i == 1) {
                xCor[i] = (w / 2) + 20;
                yCor[i] = 0;
            } else if (i == 2) {
                xCor[i] = (w / 2) + 60;
                yCor[i] = h;
            } else if (i == 3) {
                xCor[i] = (w / 2)  - 60;
                yCor[i] = h;
            } else if (i == 4) {
                xCor[i] = (w / 2) - 20;
                yCor[i] = 0;
            }
        }
    }

    public void addEventListenerLoseYourMove(EventListenerSendShot listener)
    {
        listenerList.add(EventListenerSendShot.class, listener);
    }

    private void loseYourMove(EventObjectSendShot evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerSendShot.class) {
                ((EventListenerSendShot) listeners[i + 1]).controlTimer(evt);
            }
        }
    }
}
