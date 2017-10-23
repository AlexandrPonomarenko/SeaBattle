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
    private String ready = "The game will start";
    private String connect = "You have 30 seconds to make a move. You go second!";
    private String lost = "lost";
    private String win = "win";
    private String flagWord;
    private String startGame = "";
    private boolean flagControlTimer = true;
    private boolean controlMove = false;
    private int loseMove;
    private int tempMemberCorX;
    private int tempMemberCorY;
    private EventListenerList listenerList;
    private Sound sound;
    ChangeCellAroundShips ccas;

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
//        drawShot(g);
        controlDrawLineAndDrawText(g);
        drawStatusCellMyOpponentShips(g);
        drawStatusCellMyShips(g);
        if(!sound.isPlaying()){
            System.out.println("TUT");
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
//                System.out.println("ЗАШЕЛ В БЛОК CREATE");
                if (startGame.equals("start")) {
                    System.out.println("ЗАШЕЛ В ПОДБЛОК START БЛОКА CREATE");
                    controlMove = true;
                    drawFatLine(g);
                    drawText(g, afterStart);
                    setTimerWait();
                    repaint();
                } else {
                    System.out.println("ЗАШЕЛ В БЛОК CREATE КОГДА ЕЩЕ НЕ ПРИШЛО СЛОВО START " + arrayFieldTwo[0][0] );
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
        repaint();
        System.out.println("ПРИХОД ОТ СЕРВЕРА " + answerFromServer);
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
            System.out.println("Зашел в " + tempArr[0]);
            if (tempArr[1].equals("true") || tempArr[1].equals("false") || tempArr[1].equals("WIN")) {
                if (tempArr[1].equals("true")) {
                    System.out.println("ЗАШЕЛ " + tempArr[1]);
                    controlMove = true;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    System.out.println("перед установкой 1 answerFromServer.length() <= 5");
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                    repaint();
                } else if (tempArr[1].equals("false")) {
                    System.out.println("ЗАШЕЛ " + tempArr[1]);
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 0;
                    repaint();
                } else if (tempArr[1].equals("WIN")) {
                    if(tempArr[2].equals("two")){
                        System.out.println("ЗАШЕЛ " + tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3]);
                        controlMove = false;
                        sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                        System.out.println("ЗАШЕЛ И УБИЛ ДВОЙНОЙ КАРАБЛЬ WIN");
                        arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
//                        checkTwoShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[3]);
                        ccas.checkTwoShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[3]);
                        sound.stop();
                        sendControlWord(new EventObjectSendShot("WIN"));
                        sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
//                        repaint();
                    }else if(tempArr[2].equals("three")){
                        System.out.println("ЗАШЕЛ " + tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3]);
                        controlMove = false;
                        sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                        System.out.println("ЗАШЕЛ И УБИЛ ТРОЙНОЙ КАРАБЛЬ WIN");
                        arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
//                        checkThreeShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[3]);
                        ccas.checkThreeShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[3]);
                        sound.stop();
                        sendControlWord(new EventObjectSendShot("WIN"));
                        sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
//                        repaint();
                    }else if(tempArr[2].equals("four")){
                        System.out.println("ЗАШЕЛ FOUR " + tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3]);
                        controlMove = false;
                        sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                        System.out.println("ЗАШЕЛ И УБИЛ ЧЕТВЕРНОЙ КАРАБЛЬ WIN");
                        arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
//                        checkFourShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[3]);
                        ccas.checkFourShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[3]);
                        sound.stop();
                        sendControlWord(new EventObjectSendShot("WIN"));
                        sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                    }else{
                        System.out.println("ЗАШЕЛ " + tempArr[1]);
                        sound.stop();
                        arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
//                        checkAroundOneShip(arrayFieldTwo, tempMemberCorX, tempMemberCorY);
                        ccas.checkAroundOneShip(arrayFieldTwo, tempMemberCorX, tempMemberCorY);
//                        repaint();
                        controlMove = false;
                        sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                        System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer ПОБЕДААААА!!!!");
                        sendControlWord(new EventObjectSendShot("WIN"));
                        sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                    }
                }
            }else if(tempArr[2].equals("true")){
                System.out.println("ЗАШЕЛ " + tempArr[0] + tempArr[1] + tempArr[2]);
                controlMove = true;
                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                System.out.println("перед установкой 1 answerFromServer.length() <= 5");
                arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
//                checkAroundOneShip(arrayFieldTwo, tempMemberCorX,tempMemberCorY);
                ccas.checkAroundOneShip(arrayFieldTwo, tempMemberCorX, tempMemberCorY);
                repaint();
            }else {
                if(tempArr[1].equals("two")){
                    System.out.println("ЗАШЕЛ " + tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3]);
                    controlMove = true;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    System.out.println("ЗАШЕЛ И УБИЛ ДВОЙНОЙ КАРАБЛЬ");
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
//                    checkTwoShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                    ccas.checkTwoShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                    repaint();
                }else if(tempArr[1].equals("three")){
                    System.out.println("ЗАШЕЛ " + tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3]);
                    controlMove = true;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    System.out.println("ЗАШЕЛ И УБИЛ ТРОЙНОЙ КАРАБЛЬ");
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
//                    checkThreeShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                    ccas.checkThreeShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                    repaint();
                }else if(tempArr[1].equals("four")){
                    System.out.println("ЗАШЕЛ FOUR " + tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3]);
                    controlMove = true;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    System.out.println("ЗАШЕЛ И УБИЛ ЧЕТВЕРНОЙ КАРАБЛЬ");
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
//                    checkFourShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                    ccas.checkFourShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                }
            }
        }else if(tempArr[0].equals("he")){
            System.out.println("Зашел в " + tempArr[0]);
            if(tempArr[3].equals("true")){
                controlMove = true;
                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 0;
                repaint();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ЭТО ТРУ С ДАННЫМИ ОН НЕ ПОПАЛ");
            }else if(tempArr[3].equals("false")){
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
                repaint();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ЭТО ФАЛСЕ С ДАННЫМИ ОН ПОПАЛ");
            }else if(tempArr[3].equals("LOSE")){
                if(tempArr[4].equals("two")){
                    System.out.println(tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3] + tempArr[4] + tempArr[5] +"ОН ПОПАЛ И УБИЛ");
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    System.out.println("УБИЛИ ДВОЙНОЙ КОРАБЛЬ LOSE");
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
//                    checkTwoShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]),tempArr[5]);
                    ccas.checkTwoShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]),tempArr[5]);
                    sound.stop();
                    sendControlWord(new EventObjectSendShot("LOSE"));
                    sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
//                    repaint();
                }else if(tempArr[4].equals("three")){
                    System.out.println(tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3] + tempArr[4] + tempArr[5] +"ОН ПОПАЛ И УБИЛ");
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    System.out.println("УБИЛИ ТРОЙНОЙ КОРАБЛЬ LOSE");
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
//                    checkThreeShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]),tempArr[5]);
                    ccas.checkThreeShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]),tempArr[5]);
                    sound.stop();
                    sendControlWord(new EventObjectSendShot("LOSE"));
                    sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
//                    repaint();
                }else if(tempArr[4].equals("four")){
                    System.out.println(tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3] + tempArr[4] + tempArr[5] +"ОН ПОПАЛ И УБИЛ");
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    System.out.println("УБИЛИ ЧЕТВЕРНОЙ КОРАБЛЬ LOSE ");
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
//                    checkFourShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]),tempArr[5]);
                    ccas.checkFourShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]),tempArr[5]);
                    sound.stop();
                    sendControlWord(new EventObjectSendShot("LOSE"));
                    sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
//                    repaint();
                }else{
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
//                    checkAroundOneShip(copyArrayFieldForBreakMyShips, Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]));
                    ccas.checkAroundOneShip(copyArrayFieldForBreakMyShips, Integer.parseInt(tempArr[1]),Integer.parseInt(tempArr[2]));
//                    repaint();
                    sound.stop();
                    sendControlWord(new EventObjectSendShot("LOSE"));
                    System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ВЫ проиграли");
                    sendCordinatesShot(new EventObjectSendShot(new Object(),"bye"));
                }
            }else if(tempArr[4].equals("false")){
                System.out.println(tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3] + tempArr[4] + "ОН ПОПАЛ И УБИЛ");
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[2])][Integer.parseInt(tempArr[3])] = 1;
//                checkAroundOneShip(copyArrayFieldForBreakMyShips, Integer.parseInt(tempArr[2]),Integer.parseInt(tempArr[3]));
                ccas.checkAroundOneShip(copyArrayFieldForBreakMyShips, Integer.parseInt(tempArr[2]),Integer.parseInt(tempArr[3]));
                repaint();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ЭТО ФАЛСЕ С ДАННЫМИ ОН ПОПАЛ");
            }else{
                if(tempArr[1].equals("two")){
                    System.out.println(tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3] + tempArr[4] + tempArr[5] +"ОН ПОПАЛ И УБИЛ");
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    System.out.println("УБИЛИ ДВОЙНОЙ КОРАБЛЬ");
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[3])][Integer.parseInt(tempArr[4])] = 1;
//                    checkTwoShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[3]),Integer.parseInt(tempArr[4]),tempArr[2]);
                    ccas.checkTwoShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[3]),Integer.parseInt(tempArr[4]),tempArr[2]);
                    repaint();
                }else if(tempArr[1].equals("three")){
                    System.out.println(tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3] + tempArr[4] + tempArr[5] +"ОН ПОПАЛ И УБИЛ");
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    System.out.println("УБИЛИ ТРОЙНОЙ КОРАБЛЬ");
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[3])][Integer.parseInt(tempArr[4])] = 1;
//                    checkThreeShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[3]),Integer.parseInt(tempArr[4]),tempArr[2]);
                    ccas.checkThreeShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[3]),Integer.parseInt(tempArr[4]),tempArr[2]);
                    repaint();
                }else if(tempArr[1].equals("four")){
                    System.out.println(tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3] + tempArr[4] + tempArr[5] +"ОН ПОПАЛ И УБИЛ");
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    System.out.println("УБИЛИ ЧЕТВЕРНОЙ КОРАБЛЬ");
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[3])][Integer.parseInt(tempArr[4])] = 1;
//                    checkFourShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[3]),Integer.parseInt(tempArr[4]),tempArr[2]);
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
        System.out.println("ЭТО В СЕКОНДПАНЕЛГЕЙМ========");
        for(int i = 0; i < arrayField.length;i++){
            for(int j = 0; j < arrayField[i].length;j++) {
                System.out.print(arrayField[i][j] + " + ");
            }
            System.out.println();
        }
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
                    System.out.println(controlMove + "EEEEEEEEEEEEEEEEEEEEEE");
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
