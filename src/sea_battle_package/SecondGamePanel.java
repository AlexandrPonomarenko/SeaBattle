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
//        drawShot(g);
        controlDrawLineAndDrawText(g);
        drawStatusCellMyOpponentShips(g);
        drawStatusCellMyShips(g);
        if(!sound.isPlaying()){
            System.out.println("TUT");
            sound.play();
        }
//        drawFatLine(g);
//        drawStartText(g, create);
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
//                checkArray(indexX, indexY);
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
//        for (int i = 0; i < arrayFieldTwo.length; i++){
//            for (int j = 0; j < arrayFieldTwo[i].length; j++){
//                if (arrayFieldTwo[i][j] == 1){
//                    g.fillRect(i * recWidth  + RIGHT + (width / 2), j * recHeight + TOP, recWidth, recHeight);
//                }
//            }
//        }
    }

    private void checkArray(int x, int y){
        if(arrayFieldTwo[x][y] == 0){
            drawFlag =  true;
            System.out.println("BLOCCCCCCCCCCCCCCCCCCCCCCCC ");
        }else if(arrayFieldTwo[x][y] == 1){drawFlag = false;}
    }
//    private void drawShot(Graphics g){
////        System.out.println("++++++++++++++++++++++++++++++++++++++++++___--------------------------------------------");
//        g.setColor(Color.BLUE);
//        if(tempCorX >= 0 && tempCorY >= 0){
//            if (drawFlag) {
////                System.out.println("++++++++++++++++++++++++++++++++++++++++++");
//                g.fillOval(tempCorX * recWidth + RIGHT + (width / 2) + recWidth / 2 - (recWidth / 3) / 2,
//                        tempCorY * recHeight + TOP + recHeight / 2 - (recWidth / 3) / 2, recWidth / 3, recWidth / 3);
//            } else {
//                g.drawLine(tempCorX, tempCorY,tempCorX + recWidth, tempCorY + recHeight );
//                g.drawLine(tempCorX + recWidth, tempCorY + recHeight, tempCorX, tempCorY);
//            }
//        }
//    }

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

    public void setControlMoveAnswerFromServerTwo(String answerFromServer){
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
                    System.out.println("ЗАШЕЛ " + tempArr[1]);
                    sound.stop();
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                    repaint();
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer ПОБЕДААААА!!!!");
                    sendControlWord(new EventObjectSendShot("WIN"));
                }
            }else if(tempArr[2].equals("true")){
                System.out.println("ЗАШЕЛ " + tempArr[0] + tempArr[1] + tempArr[2]);
                controlMove = true;
                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                System.out.println("перед установкой 1 answerFromServer.length() <= 5");
                arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                checkAroundOneShip(arrayFieldTwo, tempMemberCorX,tempMemberCorY);
                repaint();
            }else{
                if(tempArr[1].equals("two")){

                    System.out.println("ЗАШЕЛ " + tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3]);
                    controlMove = true;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                    System.out.println("ЗАШЕЛ И УБИЛ ДВОЙНОЙ КАРАБЛЬ");
                    arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                    checkTwoShipSide(arrayFieldTwo,tempMemberCorX,tempMemberCorY,tempArr[2]);
                    repaint();
                }
//                System.out.println("ЗАШЕЛ " + tempArr[0] + tempArr[1] + tempArr[2]);
//                controlMove = true;
//                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
//                System.out.println("перед установкой 1 answerFromServer.length() <= 5");
//                arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
//                repaint();
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
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
                repaint();
                sendControlWord(new EventObjectSendShot("LOSE"));
                sound.stop();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ВЫ проиграли");
            }else if(tempArr[4].equals("false")){
                System.out.println(tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3] + tempArr[4] + "ОН ПОПАЛ И УБИЛ");
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[2])][Integer.parseInt(tempArr[3])] = 1;
                checkAroundOneShip(copyArrayFieldForBreakMyShips, Integer.parseInt(tempArr[2]),Integer.parseInt(tempArr[3]));
                repaint();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ЭТО ФАЛСЕ С ДАННЫМИ ОН ПОПАЛ");
            }else{
                if(tempArr[1].equals("two")){

                    System.out.println(tempArr[0] + tempArr[1] + tempArr[2] + tempArr[3] + tempArr[4] + tempArr[5] +"ОН ПОПАЛ И УБИЛ");
                    controlMove = false;
                    sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                    System.out.println("УБИЛИ ДВОЙНОЙ КОРАБЛЬ");
                    copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[3])][Integer.parseInt(tempArr[4])] = 1;
                    checkTwoShipSide(copyArrayFieldForBreakMyShips,Integer.parseInt(tempArr[3]),Integer.parseInt(tempArr[4]),tempArr[2]);
                    repaint();
                }
//                controlMove = false;
//                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
//                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[1])][Integer.parseInt(tempArr[2])] = 1;
//                repaint();
//                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ЭТО ФАЛСЕ С ДАННЫМИ ОН ПОПАЛ");
            }
        }
    }
    public void setControlMoveAnswerFromServer(String answerFromServer){
        repaint();
        System.out.println("ПРИХОД ОТ СЕРВЕРА " + answerFromServer);
        if(answerFromServer.equals("move")){
            controlMove = true;
            sendControlTimerInFirsPanel(new EventObjectSendShot(1));
            return;
        }else if(answerFromServer.equals("skip move")){
            controlMove = false;
            sendControlTimerInFirsPanel(new EventObjectSendShot(0));
            return;
        }

        if(answerFromServer.length() > 5) {
            String tempArr[] = answerFromServer.split(",");
            System.out.println(tempArr[0] + " - " + tempArr[1] + " - " + tempArr[2]);
            if (tempArr[2].equals("true")) {
                controlMove = true;
                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[0])][Integer.parseInt(tempArr[1])] = 0;
                repaint();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ЭТО ТРУ С ДАННЫМИ ОН НЕ ПОПАЛ");
            } else if (tempArr[2].equals("false")) {
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[0])][Integer.parseInt(tempArr[1])] = 1;
                repaint();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ЭТО ФАЛСЕ С ДАННЫМИ ОН ПОПАЛ");
            }else if(tempArr[2].equals("LOSE")){
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                copyArrayFieldForBreakMyShips[Integer.parseInt(tempArr[0])][Integer.parseInt(tempArr[1])] = 1;
                repaint();
                sendControlWord(new EventObjectSendShot("LOSE"));
                sound.stop();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer  ВЫ проиграли");
            }
        }else if(answerFromServer.length() <= 5){
            System.out.println("ЗАШЕЛ answerFromServer.length() <= 5");
            if (answerFromServer.equals("true")) {
                controlMove = true;
                sendControlTimerInFirsPanel(new EventObjectSendShot(1));
                System.out.println("перед установкой 1 answerFromServer.length() <= 5");
                arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                repaint();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer answerFromServer.length() <= 5");
            } else if (answerFromServer.equals("false")) {
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 0;
                repaint();
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer answerFromServer.length() <= 5");
            }else if(answerFromServer.equals("WIN")){
                sound.stop();
                arrayFieldTwo[tempMemberCorX][tempMemberCorY] = 1;
                repaint();
                controlMove = false;
                sendControlTimerInFirsPanel(new EventObjectSendShot(0));
                System.out.println(controlMove + "ЭТО В МЕТОДЕ setControlMoveAnswerFromServer ПОБЕДААААА!!!!");
                sendControlWord(new EventObjectSendShot("WIN"));
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

    private void checkAroundOneShip( int[][] array, int corX, int corY){
        for (int i = 0; i < 8; i++){
            if(i == 0){
                if(corY - 1 >= 0){
                    array[corX][corY - 1] = 0;
                }
            }else if(i == 1){
                if(corY - 1 >= 0 && corX + 1 <= array.length){
                    array[corX + 1][corY - 1] = 0;
                }
            }else if(i == 2){
                if(corX + 1 <= array.length){
                    array[corX + 1][corY] = 0;
                }
            }else if(i == 3){
                if(corX + 1 <= array.length && corY + 1 <= array.length){
                    array[corX + 1][corY + 1] = 0;
                }
            }else if(i == 4){
                if(corY + 1 <= array.length){
                    array[corX][corY + 1] = 0;
                }
            }else if(i == 5){
                if(corX - 1 >= 0 && corY + 1 <= array.length){
                    array[corX - 1][corY + 1] = 0;
                }
            }else if(i == 6){
                if(corX - 1 >= 0){
                    array[corX - 1][corY] = 0;
                }
            }else if(i == 7){
                if(corX - 1 >= 0 && corY  - 1 >=0){
                    array[corX - 1][corY - 1] = 0;
                }
            }
        }
    }

    private void checkTwoShipSide(int array[][], int x, int y, String side){
        if(side.equals("top")){
            for(int i = 0; i < 10; i++){
                if(i == 0){
                    if(y - 2 >= 0)array[x][y - 2] = 0; // double top
                }else if(i == 1){
                    if(y - 2 >= 0 && x + 1 <= array.length)array[x + 1][y - 2] = 0; // double top right
                }else if(i == 2){
                    if(y - 1 >=0 && x + 1 <= array.length)array[x + 1][y - 1] = 0;// top right
                }else if(i == 3){
                    if(x + 1 <= array.length)array[x + 1][y] = 0;// right
                }else if(i == 4){
                    if(x + 1 <= array.length && y + 1 <= array.length) array[x + 1][y + 1] = 0;// bottom right
                }else if(i == 5){
                    if(y + 1 <= array.length)array[x][y + 1] = 0;// bottom
                }else if(i == 6){
                    if(x - 1 >= 0 && y + 1 <= array.length)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 7){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 8){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }else if(i == 9){
                    if(x - 1 >= 0 && y - 2 >= 0)array[x - 1][y - 2] = 0; // // left double top
                }
            }
        }else if(side.equals("right")){
            for(int i = 0; i < 10; i++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(y - 1 >=0 && x + 2 <= array.length)array[x + 2][y - 1] = 0; // top double right
                }else if(i == 3){
                    if(x + 2 <= array.length)array[x + 2][y] = 0; // double right
                }else if(i == 4){
                    if(x + 2 <= array.length && y + 1 <= array.length) array[x + 2][y + 1] = 0; // double right bottom
                }else if(i == 5){
                    if(x + 1 <= array.length &&  y + 1 <= array.length)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 6){
                    if(y + 1 <= array.length)array[x ][y + 1] = 0; // bottom
                }else if(i == 7){
                    if(x - 1 >= 0 && y + 1 <= array.length)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 8){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 9){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }else if(side.equals("bottom")){
            for(int i = 0; i < 10; i++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(x + 1 <= array.length)array[x + 1][y] = 0; //  right
                }else if(i == 3){
                    if(x + 1 <= array.length &&  y + 1 <= array.length)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 4){
                    if(x + 1 <= array.length && y + 2 <= array.length) array[x + 1][y + 2] = 0; // double bottom right
                }else if(i == 5){
                    if(y + 2 <= array.length)array[x][y + 2] = 0; // double bottom
                }else if(i == 6){
                    if( x - 1 >= 0 && y + 2 <= array.length)array[x - 1][y + 2] = 0; // double bottom left
                }else if(i == 7){
                    if(x - 1 >= 0 && y + 1 <= array.length)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 8){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 9){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }else if(side.equals("left")){
            for(int i = 0; i < 10; i++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(x + 1 <= array.length)array[x + 1][y] = 0; //  right
                }else if(i == 3){
                    if(x + 1 <= array.length &&  y + 1 <= array.length)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 4){
                    if(y + 1 <= array.length)array[x][y + 1] = 0; // double bottom
                }else if(i == 5){
                    if(x - 1 >= 0 && y + 1 <= array.length)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 6){
                    if( x - 2 >= 0 && y + 1 <= array.length)array[x - 2][y + 1] = 0; // double left bottom
                }else if(i == 7){
                    if(x - 2 >= 0)array[x - 2][y] = 0; // double left
                }else if(i == 8){
                    if(x - 2 >= 0 && y - 1 >= 0)array[x - 2][y - 1] = 0; // double left top
                }else if(i == 9){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }
    }

    private void drawStatusCellMyOpponentShips(Graphics g){
        for(int i = 0; i < arrayFieldTwo.length;i++){
            for(int j = 0; j < arrayFieldTwo[i].length;j++){
                g.setColor(Color.red);

                if(arrayFieldTwo[i][j] == 1){
//                    System.out.println("--- " + tempCorX + " " + tempCorY + " " + (tempCorX + recWidth) + " " + (tempCorY + recHeight));
                    g.drawLine(i * recWidth + (width / 2) + RIGHT, j * recHeight + TOP,
                            i * recWidth + (width / 2) + RIGHT + recWidth , j * recHeight + TOP + recHeight );
                    g.drawLine(i * recWidth + RIGHT + recWidth + (width / 2),
                            j * recHeight + TOP, i * recWidth + (width / 2) + RIGHT , j * recHeight + TOP + recHeight);
//                    System.out.println("LINE MyOpponent");
//                    repaint();
                }else if(arrayFieldTwo[i][j] == 0){
//                    g.setColor(Color.blue);
                    g.fillOval(i * recWidth + RIGHT + (width / 2) + recWidth / 2 - (recWidth / 3) / 2,
                            j * recHeight + TOP + recHeight / 2 - (recWidth / 3) / 2, recWidth / 3, recWidth / 3);
//                    System.out.println("OVAL MyOpponent");
//                    repaint();
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
                    System.out.println("LINE");
//                    repaint();
                }else if(copyArrayFieldForBreakMyShips[i][j] == 0){
                    g.setColor(Color.blue);
                    g.fillOval(i * recWidth + RIGHT + recWidth / 2 - (recWidth / 3) / 2,
                            j * recHeight + TOP + recHeight / 2 - (recWidth / 3) / 2, recWidth / 3, recWidth / 3);
                    System.out.println("OVAL");
//                    repaint();
                }
            }
        }
    }
//    public void setFlag(boolean flag)
//    {
//        this.flag = flag;
//    }

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
        System.out.println("ЗАШЕЛ В МЕТОД  setLoseMove " + this.loseMove );
        this.loseMove = loseMove;
        System.out.println("ЗАШЕЛ В МЕТОД  setLoseMove " + this.loseMove );
        sendCordinatesShot(new EventObjectSendShot(new Object(),"lost"));
        System.out.println("ОТПРАВИЛ ЛОСТ ТАЙМЕР В ПЕРВОЙ ПАНЕЛИ 0");
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
//                    setTempCorXAndCorY(e.getX(), e.getY());
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
        System.out.println("ЭТО В МЕТОДЕ setTempCorXAndCorY " + x + " +++ " + y);
        tempMemberCorX = x;
        tempMemberCorY = y;
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
