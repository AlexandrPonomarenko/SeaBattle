package sea_battle_package;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * Created by Alexandr on 03.07.2017.
 */
public class SecondGamePanel extends JPanel {
    private int width = 0;
    private int height = 0;
    private int recWidth = 0;
    private int recHeight = 0;
    private int tempCorX = 0;
    private int tempCorY = 0;
    private int [][] arrayField;
    private int [][] arrayFieldTwo;
    private Timer timerWait;
    private int startNumberTimer = 0;
    private int endTimer = 180;
    private String create = "Waiting for an opponent to connect";
    private String afterStart = "You have 30 seconds to make a move. You go first!";
    private String ready = "The game will start";
    private String connect = "You have 30 seconds to make a move. You go second!";
    private String flagWord;
    private String startGame = "";
    private boolean flagControlTimer = true;
    private boolean controlMove = false;
    private int loseMove;
    private EventListenerList listenerList;

    private boolean flagRec;
    private boolean flag = false;
    private boolean drawFlag = false;

    private int TOP = 20;
    private int LEFT = 40;
    private int RIGHT = 40;
    private int BOTTOM = 20;

    public SecondGamePanel(int w, int h){
        setPreferredSize(new Dimension(w,h ));
        setBackground(Color.RED);
        arrayField = new int[10][10];
        arrayFieldTwo = new int[10][10];
        addMouseMotionListener();
        setTimer();
        timerStart();
        listenerList = new EventListenerList();
    }

    private void setCorPanel(int w, int h) {
        width = w;
        height = h;
        recWidth = ((width / 2) - LEFT - RIGHT) / 10;
        recHeight = (height - TOP - BOTTOM) / 10;
    }

    public void paint(Graphics g) {
        setCorPanel(getWidth(),getHeight());

        g.setColor(new Color(29,172,214));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.black);
        drawSinglWeb(g, recWidth, recHeight, TOP, LEFT);
        drawSinglWeb(g, recWidth, recHeight, TOP, (width / 2) + LEFT);
        g.setColor(Color.red);
        g.fillRect(getWidth() / 2 - 20, 0,40, getHeight());
        drawCell(g);
        updateGrid(g);
        drawShot(g);
        controlDrawLineAndDrawText(g);

//        drawFatLine(g);
//        drawStartText(g, create);
    }

    private void drawSinglWeb(Graphics g, int stepX, int stepY, int top, int left) {
        for (int i = 0; i < 11; i++ ) {
            int point = (i * stepX) + left;
            g.drawLine(point, top, point,stepY * 10 + top);
        }

        for (int i = 0; i < 11; i++ ) {
            int point = (i * stepY) + top;
            g.drawLine(left, point, stepX * 10 + left, point);
        }
    }

    public void findCorCell(int corX, int corY) {
        if(corX > width / 2) {
            setCor(width / 2 + LEFT, width - RIGHT, width - RIGHT, corX, corY, arrayFieldTwo);
            //flagRec = false;
        }
//      else {
//            setCor(LEFT,width / 2 - RIGHT, (width / 2) - LEFT - RIGHT, corX, corY, arrayField);
//            //flagRec = true;
//        }
    }

    private void setCor(int startPoz, int border, int length, int corX, int corY, int array[][]){
        int x = corX;
        int y = corY;
        int indexX = 0, indexY = 0;

        boolean flagX = false;
        boolean flagY = false;

        if((x >= startPoz && x <= border) && (y >= TOP && y <= height - BOTTOM)){
            for (int i = startPoz, i2 = 0; i < length; i += recWidth, i2++) {
                if (i + recWidth > x){
                    indexX = i2;
                    flagX = true;
                    break;
                }
            }

            for (int i = TOP, i2 = 0; i < height - BOTTOM; i += recHeight, i2++){
                if (i + recHeight > y){
                    indexY = i2;
                    flagY = true;
                    break;
                }
            }
            System.out.println(indexX + " " + indexY);
            if (flagX && flagY){
                tempCorX = indexX;
                tempCorY = indexY;
                checkArray(indexX, indexY);
//                array[indexX][indexY] = 1;
            }
        }
    }

    private void updateGrid(Graphics g){
        g.setColor(Color.black);
        drawSinglWeb(g, recWidth, recHeight, TOP, LEFT);
        drawSinglWeb(g, recWidth, recHeight, TOP, (width / 2) + LEFT);
    }
    private void drawCell(Graphics g){
        for (int i = 0; i < arrayField.length; i++){
            for (int j = 0; j < arrayField[i].length; j++){
                if (arrayField[i][j] == 1){
                    g.fillRect(i * recWidth + RIGHT, j * recHeight + TOP, recWidth, recHeight);
                }
            }
        }
        for (int i = 0; i < arrayFieldTwo.length; i++){
            for (int j = 0; j < arrayFieldTwo[i].length; j++){
                if (arrayFieldTwo[i][j] == 1){
                    g.fillRect(i * recWidth  + RIGHT + (width / 2), j * recHeight + TOP, recWidth, recHeight);
                }
            }
        }
    }

    private void checkArray(int x, int y){
        if(arrayFieldTwo[x][y] == 0){
            drawFlag =  true;
            System.out.println("BLOCCCCCCCCCCCCCCCCCCCCCCCC ");
        }else if(arrayFieldTwo[x][y] == 1){drawFlag = false;}
    }
    private void drawShot(Graphics g){
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++___--------------------------------------------");
        g.setColor(Color.BLUE);
        if(tempCorX >= 0 && tempCorY >= 0){
            if (drawFlag) {
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++");
                g.fillOval(tempCorX * recWidth + RIGHT + (width / 2) + recWidth / 2 - (recWidth / 3) / 2,
                        tempCorY * recHeight + TOP + recHeight / 2 - (recWidth / 3) / 2, recWidth / 3, recWidth / 3);
            } else {
                g.drawLine(tempCorX, tempCorY,tempCorX + recWidth, tempCorY + recHeight );
                g.drawLine(tempCorX + recWidth, tempCorY + recHeight, tempCorX, tempCorY);
            }
        }
    }

    private void drawFatLine(Graphics g){
        g.setColor(new Color(128, 0, 128, 128));
        g.fillRect(0,100,width,150);

    }
    private void drawText(Graphics g, String stringText) {
        g.setColor(new Color(240, 128, 128, 128));
        Font font = new Font("San Francisco", Font.BOLD | Font.ITALIC, 30);
        int c = font.getSize();
        g.setFont(font);
        g.drawString(stringText + " :  " + startNumberTimer, (width / 2 - (width / 2 / 2 / 2)) - c, 175);
    }

    private void controlDrawLineAndDrawText(Graphics g){
        if(flagControlTimer) {
            if (flagWord.equals("Create")) {
                System.out.println("ЗАШЕЛ В БЛОК CREATE");
                if (startGame.equals("start")) {
                    System.out.println("ЗАШЕЛ В ПОДБЛОК START БЛОКА CREATE");
                    controlMove = true;
                    drawFatLine(g);
                    drawText(g, afterStart);
                    setTimerWait();
                    repaint();
                } else {
                    System.out.println("ЗАШЕЛ В БЛОК CREATE КОГДА ЕЩЕ НЕ ПРИШЛО СЛОВО START" );
                    drawFatLine(g);
                    drawText(g, create);
                    repaint();
                }
            } else if (flagWord.equals("Connect")) {
//                timerStop();
                System.out.println("ЗАШЕЛ С БЛОК CONNECT");
                if(startGame.equals("start")){
                    System.out.println("ЗАШЕЛ В ПОДБЛОК START БЛОКА CONNECT");
                    setTimerWait();
//                    timerStart();
                    controlMove = false;
                    drawFatLine(g);
                    drawText(g, connect);
                    repaint();
                }
            }
        }
    }

    public void setFlagWord(String flagWord){
        this.flagWord = flagWord;
        System.out.println(flagWord);
    }

    public void setWordStart(String start){
        startGame = start;
        System.out.println(startGame + "ЭТО В МЕТОДЕ УСТАНОВКИ СЛОВА СТАРТ setWordStart");
    }

    public void setControlMoveAnswerFromServer(String answerFromServer){
        if(answerFromServer.equals("true")){
            controlMove = true;
            if(controlMove) {
                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
            }
            System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer ");
        }else if(answerFromServer.equals("false")){
            controlMove = false;
            if(!controlMove) {
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
            }
            System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer ");
        }
    }
    public void setTimerWait(){
        if(!flag){
            startNumberTimer = 0;
            endTimer = 6;
            flag = true;
        }
    }
    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public void setShip(int[][] ship){
        arrayField = ship;
        repaint();
    }

    public void setLoseMove(int loseMove){
        this.loseMove = loseMove;
        if(loseMove == 3){
            sendCordinatesShot(new EventObjectSendShot(new Object(),"lose"));
        }
    }

    private void addMouseMotionListener(){
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                //super.mouseClicked(e);
                System.out.println("CLIKKKKKKKKKK");
                if(controlMove) {
                    findCorCell(e.getX(), e.getY());
                    repaint();
                    System.out.println("CLIKKKKKKKKKKEEEEEDDDDDDD" + controlMove);
                }
            }
        });
    }

    private void setTimer(){
        timerWait = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                System.out.println("TIMER ____________in SECOND GAME PANEL");
                if(startNumberTimer < endTimer){
                    startNumberTimer++;
                    System.out.println("Таймер " + startNumberTimer);
                }else{
                    timerStop();
                    flagControlTimer = false;
                    System.out.println(flagControlTimer + "EEEEEEEEEEEEEEEEEEEEEE");
                    repaint();
                    System.out.println("Таймер СТОП" + flagControlTimer);
                    startNumberTimer = 0;
                    if(controlMove) {
                        sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    }
                }
            }
        });
    }
    private void timerStart(){
        timerWait.start();
    }
    private void timerStop(){
        timerWait.stop();
    }

    public void addEventListenerSendAnswerServerControlWord(EventListenerSendShot listener)
    {
        listenerList.add(EventListenerSendShot.class, listener);
    }

    private void sendCordinatesShot(EventObjectSendShot evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerSendShot.class) {
                ((EventListenerSendShot) listeners[i + 1]).sendCoordinateShotOrAnswerServer(evt);
            }
        }
    }

    private void sendControlTimerInFirsPanel(EventObjectSendShot evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerSendShot.class) {
                ((EventListenerSendShot) listeners[i + 1]).sendCoordinateShotOrAnswerServer(evt);
            }
        }
    }
}
