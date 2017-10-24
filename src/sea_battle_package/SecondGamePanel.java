package sea_battle_package;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
    private int [][] copyArrayFieldForBreakMyShips;
    private Timer timerWait;
    private int startNumberTimer = 0;
    private int endTimer = 180;
    private String create = "Waiting for an opponent to connect";
    private String afterStart = "You have 30 seconds to make a move. You go first!";
    private String connect = "You have 30 seconds to make a move. You go second!";
    private String flagWord;
    private String startGame = "";
    private boolean flagControlTimer = true;
    private boolean controlMove = false;
    private int loseMove;
    private int tempMemberCorX;
    private int tempMemberCorY;
    private EventListenerList listenerList;
    private Sound sound;
    private ChangeCellAroundShips ccas;
    private boolean statusGame;

    private boolean flag = false;

    private int TOP = 20;
    private int LEFT = 40;
    private int RIGHT = 40;
    private int BOTTOM = 20;

    public SecondGamePanel(int w, int h){
        setPreferredSize(new Dimension(w,h ));
        setBackground(Color.RED);
        arrayField = new int[10][10];
        arrayFieldTwo = new int[10][10];
        copyArrayFieldForBreakMyShips = new int[10][10];
        initializingArray(arrayFieldTwo);
        initializingArray(copyArrayFieldForBreakMyShips);
        addMouseMotionListener();
        setTimer();
        timerStart();
        listenerList = new EventListenerList();
        sound = new Sound(new File("E:\\www\\SeaBattle\\sound_Four.wav"));
        ccas = new ChangeCellAroundShips();
    }

    private void setCorPanel(int w, int h) {
        width = w;
        height = h;
        recWidth = ((width / 2) - LEFT - RIGHT) / 10;
        recHeight = (height - TOP - BOTTOM) / 10;
    }

    public void paint(Graphics g) {
        setCorPanel(getWidth(),getHeight());

        g.setColor(new Color(0,139,139));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.black);
        drawSinglWeb(g, recWidth, recHeight, TOP, LEFT);
        drawSinglWeb(g, recWidth, recHeight, TOP, (width / 2) + LEFT);
        g.setColor(new Color(184,134,11));
        g.fillRect(getWidth() / 2 - 20, 0,40, getHeight());
        g.setColor(new Color(178,34,34));
        drawCell(g);
        updateGrid(g);
        controlDrawLineAndDrawText(g);
        drawStatusCellMyOpponentShips(g);
        drawStatusCellMyShips(g);
        if(!sound.isPlaying()){
            sound.play();
        }
    }

    private void initializingArray(int[][] array){
        for (int i = 0; i < array.length; i++ ) {
            for (int j = 0; j < array[i].length; j++ ) {
                array[i][j] = 3;
            }
        }
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
        }
    }

    public void setStatusGame(boolean status){
        statusGame = status;
        if(!statusGame){
            sendControlWord(new EventObjectSendShot("Left"));
        }
    }
    private void setCor(int startPoz, int border, int length, int corX, int corY, int array[][]){
        int x = corX;
        int y = corY;
        int indexX = 0, indexY = 0;
        String cordinatesShot = "";

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
                setTempCorXAndCorY(tempCorX,tempCorY);
                cordinatesShot =  String.valueOf(tempCorX) + "," + String.valueOf(tempCorY);
                sendCordinatesShot(new EventObjectSendShot(new Object(),cordinatesShot));
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
    }

    private void drawFatLine(Graphics g){
        g.setColor(new Color(210, 105, 30, 128));
        g.fillRect(0,100,width,150);

    }
    private void drawText(Graphics g, String stringText) {
        g.setColor(new Color(165, 42, 42, 128));
        Font font = new Font("San Francisco", Font.BOLD | Font.ITALIC, 15);
        int c = font.getSize();
        g.setFont(font);
        g.drawString(stringText + " :  " + startNumberTimer, (width / 2 - (width / 2 / 2 / 2)) - c, 175);
    }

    private void controlDrawLineAndDrawText(Graphics g){
        if(flagControlTimer) {
            if (flagWord.equals("Create")) {
                if (startGame.equals("start")) {
                    controlMove = true;
                    drawFatLine(g);
                    drawText(g, afterStart);
                    setTimerWait();
                    repaint();
                } else {
                    drawFatLine(g);
                    drawText(g, create);
                    repaint();
                }
            } else if (flagWord.equals("Connect")) {
                if(startGame.equals("start")){
                    setTimerWait();
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
    }

    public void setWordStart(String start){
        startGame = start;
    }

    public void setControlMoveAnswerFromServer(String answerFromServer){
        repaint();
        String tempArr[] = answerFromServer.split(",");
        if(tempArr[0].equals("move") || tempArr[0].equals("skip move")){
            if(tempArr[0].equals("move")){
                controlMove = true;
                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                return;
            }else if(tempArr[0].equals("skip move")){
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                return;
            }
        }

        if(tempArr[0].equals("you")) {
            if (tempArr[1].equals("true") || tempArr[1].equals("false") || tempArr[1].equals("WIN")) {
                if (tempArr[1].equals("true")) {
                    controlMove = true;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                    repaint();
                } else if (tempArr[1].equals("false")) {
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 0;
                    repaint();
                } else if (tempArr[1].equals("WIN")) {
                    if(tempArr[2].equals("two")){
                        controlMove = false;
                        sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                        arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                        ccas.checkTwoShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[3]);
                        sound.stop();
                        sendControlWord(new EventObjectSendShot("WIN"));
                        sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                    }else if(tempArr[2].equals("three")){
                        controlMove = false;
                        sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                        arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                        ccas.checkThreeShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[3]);
                        sound.stop();
                        sendControlWord(new EventObjectSendShot("WIN"));
                        sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                    }else if(tempArr[2].equals("four")){
                        controlMove = false;
                        sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                        arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                        ccas.checkFourShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[3]);
                        sound.stop();
                        sendControlWord(new EventObjectSendShot("WIN"));
                        sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                    }else{
                        sound.stop();
                        arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                        ccas.checkAroundOneShip(arrayFieldTwo, tempMemberCorX, tempMemberCorY);
                        controlMove = false;
                        sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                        sendControlWord(new EventObjectSendShot("WIN"));
                        sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                    }
                }
            }else if(tempArr[2].equals("true")){
                controlMove = true;
                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                ccas.checkAroundOneShip(arrayFieldTwo, tempMemberCorX, tempMemberCorY);
                repaint();
            }else {
                if(tempArr[1].equals("two")){
                    controlMove = true;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                    ccas.checkTwoShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                    repaint();
                }else if(tempArr[1].equals("three")){
                    controlMove = true;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                    ccas.checkThreeShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                    repaint();
                }else if(tempArr[1].equals("four")){
                    controlMove = true;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                    ccas.checkFourShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                }
            }
        }else if(tempArr[0].equals("he")){
            if(tempArr[3].equals("true")){
                controlMove = true;
                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 0;
                repaint();
            }else if(tempArr[3].equals("false")){
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
                repaint();
            }else if(tempArr[3].equals("LOSE")){
                if(tempArr[4].equals("two")){
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
                    ccas.checkTwoShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]),tempArr[5]);
                    sound.stop();
                    sendControlWord(new EventObjectSendShot("LOSE"));
                    sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                }else if(tempArr[4].equals("three")){
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
                    ccas.checkThreeShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]),tempArr[5]);
                    sound.stop();
                    sendControlWord(new EventObjectSendShot("LOSE"));
                    sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                }else if(tempArr[4].equals("four")){
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
                    ccas.checkFourShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]),tempArr[5]);
                    sound.stop();
                    sendControlWord(new EventObjectSendShot("LOSE"));
                    sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                }else{
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
                    ccas.checkAroundOneShip(copyArrayFieldForBreakMyShips, Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]));
                    sound.stop();
                    sendControlWord(new EventObjectSendShot("LOSE"));
                    sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                }
            }else if(tempArr[4].equals("false")){
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[2])][Integer.parseInt(tempArr[3])] = 1;
                ccas.checkAroundOneShip(copyArrayFieldForBreakMyShips, Integer.parseInt(tempArr[2]),Integer.parseInt(tempArr[3]));
                repaint();
            }else{
                if(tempArr[1].equals("two")){
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[3])][Integer.parseInt(tempArr[4])] = 1;
                    ccas.checkTwoShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[3]),Integer.parseInt(tempArr[4]),tempArr[2]);
                    repaint();
                }else if(tempArr[1].equals("three")){
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[3])][Integer.parseInt(tempArr[4])] = 1;
                    ccas.checkThreeShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[3]),Integer.parseInt(tempArr[4]),tempArr[2]);
                    repaint();
                }else if(tempArr[1].equals("four")){
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[3])][Integer.parseInt(tempArr[4])] = 1;
                    ccas.checkFourShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[3]),Integer.parseInt(tempArr[4]),tempArr[2]);
                    repaint();
                }
            }
        }
    }

    public void setTimerWait(){
        if(!flag){
            startNumberTimer = 0;
            endTimer = 6;
            flag = true;
        }
    }

    private void drawStatusCellMyOpponentShips(Graphics g){
        for(int i = 0; i < arrayFieldTwo.length;i++){
            for(int j = 0; j < arrayFieldTwo[i].length;j++){
                g.setColor(Color.black);
                if(arrayFieldTwo[i][j] == 1){
                    g.drawLine(i * recWidth + (width / 2) + RIGHT, j * recHeight + TOP,
                            i * recWidth + (width / 2) + RIGHT + recWidth , j * recHeight + TOP + recHeight );
                    g.drawLine(i * recWidth + RIGHT + recWidth + (width / 2),
                            j * recHeight + TOP, i * recWidth + (width / 2) + RIGHT , j * recHeight + TOP + recHeight);
                }else if(arrayFieldTwo[i][j] == 0){
                    g.setColor(Color.blue);
                    g.fillOval(i * recWidth + RIGHT + (width / 2) + recWidth / 2 - (recWidth / 3) / 2,
                            j * recHeight + TOP + recHeight / 2 - (recWidth / 3) / 2, recWidth / 3, recWidth / 3);
                }
            }
        }
    }

    private void drawStatusCellMyShips(Graphics g){
        for(int i = 0; i < copyArrayFieldForBreakMyShips.length;i++){
            for(int j = 0; j < copyArrayFieldForBreakMyShips[i].length;j++){
                g.setColor(Color.black);
                if(copyArrayFieldForBreakMyShips[i][j] == 1){
                    g.drawLine(i * recWidth + RIGHT, j * recHeight + TOP,
                            i * recWidth + recWidth + RIGHT, j * recHeight + recHeight + TOP );
                    g.drawLine(i * recWidth + recWidth + RIGHT, j * recHeight + TOP,
                            i * recWidth + RIGHT, j * recHeight + recHeight + TOP);
                }else if(copyArrayFieldForBreakMyShips[i][j] == 0){
                    g.setColor(Color.blue);
                    g.fillOval(i * recWidth + RIGHT + recWidth / 2 - (recWidth / 3) / 2,
                            j * recHeight + TOP + recHeight / 2 - (recWidth / 3) / 2, recWidth / 3, recWidth / 3);
                }
            }
        }
    }

    public void setShip(int[][] ship){
        arrayField = ship;
        repaint();
    }

    public void setLoseMove(int loseMove){
        this.loseMove = loseMove;
        sendCordinatesShot(new EventObjectSendShot(new Object(),"lost"));
        controlMove = false;
    }

    private void addMouseMotionListener(){
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(controlMove) {
                    findCorCell(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    private void setTimer(){
        timerWait = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                if(startNumberTimer < endTimer){
                    startNumberTimer++;
                }else{
                    timerStop();
                    flagControlTimer = false;
                    repaint();
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

    private void setTempCorXAndCorY(int x, int y){
        tempMemberCorX = x;
        tempMemberCorY = y;
    }
    public void addEventListenerSendAnswerServerControlWord(EventListenerSendShot listener) {
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
                ((EventListenerSendShot) listeners[i + 1]).controlTimer(evt);
            }
        }
    }

    private void sendControlWord(EventObjectSendShot evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerSendShot.class) {
                ((EventListenerSendShot) listeners[i + 1]).sendControlWord(evt);
            }
        }
    }
}
