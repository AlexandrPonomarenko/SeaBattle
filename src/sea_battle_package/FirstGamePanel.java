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
    private JLabel time;
    private JLabel status;
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
//        timerStart();
        timerStop();
        listenerList = new EventListenerList();
//        gbl = new GridBagLayout();
//        setLayout(gbl);
//        time = new JLabel("Time");
//        status = new JLabel("Status: ");

//        gbc = new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.NONE,
//                new Insets(0,0,0,0),0,0);
//
//        gbl.setConstraints(time, gbc);
//        add(time, new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.BOTH,
//                new Insets(0,0,0,0),0,0));

//        gbc = new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,
//                new Insets(0,0,0,0),0,0);
//        gbl.setConstraints(status, gbc);
//        add(status);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.orange);
        setCoordinates(getWidth(),getHeight(),5);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.red);
        poligon = new Polygon(xCor,yCor,5);
//        g.drawPolygon(poligon);
        g.fillPolygon(poligon);
//        repaintComponent(g);
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
//                System.out.println("TIMER ____________ in FIRST GAME PANEL");
                if(startNumberTimer > 0){
                    startNumberTimer--;
                }else{
                    startNumberTimer = 30;
//                    startNumberTimer = 0;
                    System.out.println("ПЕРЕД ОТПРАВКОЙ ЛОСТМУВ");
                    loseYourMove(new EventObjectSendShot(3));
                    timerStop();
                }
            }
        });
    }

    public void setTimerWait(){
//        System.out.println(" В МЕТОДЕ setTimerWait " + time);
//        controlTimer = time;
        timerStop();
        System.out.println(" В МЕТОДЕ setTimerWait " + controlTimer);
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
        System.out.println(myName + " ---- ЭТО В МЕТОДЕ setMyName");
        repaint();
    }

    public void setNameMyOpponent(String nameMyOpponent){
        this.nameMyOpponent = nameMyOpponent;
        System.out.println(nameMyOpponent + " ---- ЭТО В МЕТОДЕ setNameMyOpponent" + this.nameMyOpponent);
        repaint();
    }

    public void setControlTimer(int conTim){
        System.out.println("ПРИХОД setControlTimer " + conTim);
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
//                System.out.println( xCor[i] + " I = 0 " + yCor[i]);
            } else if (i == 1) {
                xCor[i] = (w / 2) + 20;
                yCor[i] = 0;
//                System.out.println(xCor[i] + " i == 1 " + yCor[i]);
            } else if (i == 2) {
                xCor[i] = (w / 2) + 60;
                yCor[i] = h;
//                System.out.println(xCor[i] + " i == 2 " + yCor[i]);
            } else if (i == 3) {
                xCor[i] = (w / 2)  - 60;
                yCor[i] = h;
//                System.out.println(xCor[i] + " i == 3 " + yCor[i]);
            } else if (i == 4) {
                xCor[i] = (w / 2) - 20;
                yCor[i] = 0;
//                System.out.println(xCor[i] + " i == 4 " + yCor[i]);
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
