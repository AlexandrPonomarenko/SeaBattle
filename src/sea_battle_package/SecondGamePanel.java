package sea_battle_package;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private String create = "Waiting for an opponent to connect";
    private String afterStart = "You have 30 seconds to make a move. You go first!";
    private String connect = "You have 30 seconds to make a move. You go second!";

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
        }else {
            setCor(LEFT,width / 2 - RIGHT, (width / 2) - LEFT - RIGHT, corX, corY, arrayField);
            //flagRec = true;
        }
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
        System.out.println("++++++++++++++++++++++++++++++++++++++++++___--------------------------------------------");
        g.setColor(Color.BLUE);
        if(tempCorX >= 0 && tempCorY >= 0){
            if (drawFlag) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++");
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
    private void drawStartText(Graphics g, String string) {
        g.setColor(new Color(240, 128, 128, 128));
        Font font = new Font("San Francisco", Font.BOLD | Font.ITALIC, 30);
        int c = font.getSize();
        g.setFont(font);
        g.drawString(string + " :  " + startNumberTimer, (width / 2 - (width / 2 / 2)) - c, 175);
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public void setShip(int[][] ship){
        arrayField = ship;
        repaint();
    }

    private void addMouseMotionListener(){
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                //super.mouseClicked(e);
                System.out.println("CLIKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                findCorCell(e.getX(), e.getY());
                repaint();
            }
        });
    }

    private void setTimer(){
        timerWait = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                System.out.println("TIMER ____________+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                if(startNumberTimer < 180){
                    startNumberTimer++;
                }else{startNumberTimer = 0;}
            }
        });
    }
    private void timerStart(){
        timerWait.start();
    }
    private void timerStop(){
        timerWait.stop();
    }

}
