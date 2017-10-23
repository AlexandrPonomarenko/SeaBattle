package sea_battle_package;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by Alexandr on 15.10.2017.
 */
public class EndPanel extends JPanel {
    private int width;
    private int height;
    private boolean flagColor = false;
    private boolean flagMove = true;
    private Font font;
    private Timer timer;
    private int endTimer;
    private int globalX = -100;
    private int globalY;
    private String word;
    private Sound sound;

    private EventListenerList listenerList;


    public EndPanel(int x, int y){
        setSize(x,y);
        listenerList = new EventListenerList();
        setFontParam(100);
        setGlobalXAndGlobalY(width / 2 - font.getSize(), height / 2 + 25);
        endTimer = x / 2 - font.getSize();
        System.out.println(endTimer + "ЭТО В КОНСТРУКТОРЕ");
        setTimer();
        timerStart();
        addMouseClick();
        addMouseListener();
//        setBackground(new Color(30, 100, 77));
    }

    private void setWidthAndHeight(int w, int h){
        width = w;
        height = h;
    }
    public void setWord(String wordFromSGP){
        if(!wordFromSGP.equals(null)){
            if(wordFromSGP.equals("WIN")){
                word = wordFromSGP;
                sound = new Sound(new File("E:\\www\\SeaBattle\\sound_Five.wav"));
            }else if(wordFromSGP.equals("LOSE")){
                word = wordFromSGP;
                sound = new Sound(new File("E:\\www\\SeaBattle\\sound_Six.wav"));
            }else if(wordFromSGP.equals("left")){
                word = "Left game";
            }
        }
        word = wordFromSGP;
    }
    private void setGlobalXAndGlobalY(int x, int y){
        globalX = x;
        globalY = y;
    }
    public void paint(Graphics g){
        setWidthAndHeight(getWidth(), getHeight());
//        setColor(231,232,23);
        g.setColor(new Color(30, 100, 77));
        g.fillRect(0,0,getWidth(),getHeight());
        setFontParam(100);
        drawWords(g,140,140,140,word, globalX, height / 2 + 25);
        setFontParam(30);
        drawWords(g,139,0,0,"Again", width / 2 - font.getSize(),  height - height / 3 + 10);
        roundRect(g,width / 2 - 70,  height - height / 3 - 30,
                width / 100 * (20 - 2) + 10, height / 100 * (15 - 2));
        drawLineUnderAgain(g);
    }

    private void drawLineUnderAgain(Graphics g){
        g.drawLine(width / 2 - 70,  (height - height / 3) + 45,
                width / 2 - 70 + width / 100 * (20 - 2) + 10, height - height / 3 - 42 + height / 100 * (20 - 2));
    }

    private void setFontParam(int size){
        font = new Font("San Francisco", Font.BOLD | Font.ITALIC, size);
    }
    private void roundRect(Graphics g , int x, int y, int width, int height){
        if(flagColor){
            g.setColor(new Color(139,0,0));
        }else {g.setColor(Color.WHITE);}
//        g.setColor(Color.WHITE);
        g.drawRoundRect( x, y, width, height, 20,15);

    }
    private void drawWords(Graphics graphics, int r, int g, int b, String word, int x, int y) {
        if(word.equals("Again") && (flagColor)) {
            graphics.setColor(new Color(r,g,b));
        }else{
            graphics.setColor(Color.WHITE);
        }
        graphics.setFont(font);
        graphics.drawString(word, x, y);
    }

    private boolean checkArea(int x, int y){
        if((x >= width / 2 - 70 && x <= width / 2 - 70 + width / 100 * (20 - 2) + 10) &&
                (y >= height - height / 3 - 30 &&  y <= height - height / 3 - 30 + height / 100 * 15)){
            return true;
        }
        return false;
    }


    private void addMouseClick(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                if(!flagMove) {
                    if (checkArea(e.getX(), e.getY())) {
                        if(!word.equals("WIN") && !word.equals("LOSE")){
                            flagMove = true;
                            timerStart();
                            againGame(new AgainGameCl(e));
                        }else{
                            flagMove = true;
                            timerStart();
                            sound.stop();
                            againGame(new AgainGameCl(e));
                        }
                    }
                }
            }
        });
    }

    private void addMouseListener(){
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
//                super.mouseMoved(e);
//                System.out.println("MAUSE" + e.getX()+ " ---  " + e.getY());
//                if(!flagMove) {
                if (checkArea(e.getX(), e.getY())) {
                    flagColor = true;
                    repaint();
                } else {
                    flagColor = false;
                    repaint();
                }
//                }
            }
        });
    }

    private void setTimer(){
        timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                System.out.println("TIMER ____________in SECOND GAME PANEL");
                if(flagMove){
                    if(globalX <= endTimer){
                        globalX++;
                        System.out.println("Таймер " + globalX + "rrrrrrrrr " + endTimer);
                    }else{
                        timerStop();
                        endTimer = width + 100;
                        flagMove = false;
                        System.out.println(flagMove + "EEEEEEEEEEEEEEEEEEEEEE");
//                        System.out.println(controlMove + "EEEEEEEEEEEEEEEEEEEEEE");
                        repaint();
                        System.out.println("Таймер СТОП" );
//                        startNumberTimer = 0;
//                    if(controlMove) {
//                        sendControlTimerInFirsPanel(new EventObjectSendShot(1));
//                    }
                    }
                }
            }
        });
    }
    private void timerStart(){
        timer.start();
    }
    private void timerStop(){
        timer.stop();
    }

    public void addEventListenerAgainGame(AgainGame listener) {
        listenerList.add(AgainGame.class, listener);
    }

    private void againGame(AgainGameCl evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == AgainGame.class) {
                ((AgainGame) listeners[i + 1]).againGame(evt);
            }
        }
    }
}
