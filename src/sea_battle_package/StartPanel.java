package sea_battle_package;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by Alexandr on 01.07.2017.
 */
public class StartPanel extends JPanel {
    private int realWidth;
    private int realHeight;
    private boolean flagColor;
    private Sound sound;

    EventListenerList eventListenerList;

    public StartPanel(int w, int h){
        setPreferredSize(new Dimension(w,h));
        eventListenerList = new EventListenerList();
        addMouseListener();
        addMouseClick();
        sound = new Sound(new File("E:\\www\\SeaBattle\\sound_One.wav"));
        sound.play(false);
    }

    public void paint(Graphics g) {
        setRealWidthAndHeight(getWidth(),getHeight());
        g.setColor(new Color(32,178,170));
        g.fillRect(0,-20,getWidth(),getHeight());
        roundRect(g,realWidth / 2 - (realWidth / 100 * 15), realHeight / 2 - (realHeight / 100 * 10),
                realWidth / 100 * 30, realHeight / 100 * 20);

        roundRect(g,realWidth / 2 - (realWidth / 100 * (15 - 1) + 5), realHeight / 2 - (realHeight / 100 * 9),
                realWidth / 100 * (30 - 2) + 10, realHeight / 100 * (20 - 2));
        drawStringStart(g);
        if(!sound.isPlaying()){
            System.out.println("TUT");
            sound.play();
        }
    }

    private void roundRect(Graphics g , int x, int y, int width, int height){
        if(flagColor){
            g.setColor(new Color(139,0,0));
        }else {g.setColor(Color.WHITE);}
//        g.setColor(Color.WHITE);
        g.drawRoundRect( x, y, width, height, 20,15);

    }
    private void setRealWidthAndHeight(int w, int h){
        realWidth = w;
        realHeight = h;
    }

    private void drawStringStart(Graphics g) {
        if(flagColor) {
            g.setColor(new Color(139,0,0));
        }else{g.setColor(Color.WHITE);}
        Font font = new Font("San Francisco", Font.BOLD | Font.ITALIC, 55);
        int c = font.getSize();
        g.setFont(font);
        g.drawString("Start", realWidth / 2 - c, realHeight / 2 + 25);
    }

    private boolean checkArea(int x, int y){
        if((x >= realWidth / 2 - (realWidth / 100 * 15) && x <= realWidth / 2 - (realWidth / 100 * 15) + realWidth / 100 * 30) &&
                (y >= realHeight / 2 - (realHeight / 100 * 10) &&  y <=realHeight / 2 - (realHeight / 100 * 10) + realHeight / 100 * 20)){
            return true;
        }
        return false;
    }
    private void addMouseListener(){
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
//                super.mouseMoved(e);
//                System.out.println("MAUSE" + e.getX()+ " ---  " + e.getY());
                if(checkArea(e.getX(),e.getY())){
                    flagColor = true;
                    repaint();
                }else {
                    flagColor = false;
                    repaint();
                }
            }
        });
    }

    private void addMouseClick(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                if(checkArea(e.getX(), e.getY())){
                    sound.stop();
                    fireLoad(new LoadEventListenerPanel(e));
                }
            }
        });
    }


    public void addMyEventListener(EventListenerLoadPanel listener)
    {
        listenerList.add(EventListenerLoadPanel.class, listener);
    }

    public void removeMyEventListener(EventListenerLoadPanel listener)
    {
        listenerList.remove(EventListenerLoadPanel.class, listener);
    }

    private void fireLoad(LoadEventListenerPanel evt)
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2)
        {
            if (listeners[i] == EventListenerLoadPanel.class)
            {
                ((EventListenerLoadPanel) listeners[i + 1]).click(evt);
            }
        }
    }
}
