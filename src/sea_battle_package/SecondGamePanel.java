package sea_battle_package;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexandr on 03.07.2017.
 */
public class SecondGamePanel extends JPanel {
    private int width = 0;
    private int height = 0;
    private int recWidth = 0;
    private int recHeight = 0;
    private int [][] arrayField;
    private int [][] arrayFieldTwo;

    private boolean flagRec;
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
    }

    private void setCorPanel(int w, int h)
    {
        width = w;
        height = h;
        recWidth = ((width / 2) - LEFT - RIGHT) / 10;
        recHeight = (height - TOP - BOTTOM) / 10;
    }

    public void paint(Graphics g)
    {
        setCorPanel(getWidth(),getHeight());

        g.setColor(new Color(29,172,214));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.black);
//        drawFieldWar(g);
//        drawFieldWarTwo(g);
        drawSinglWeb(g, recWidth, recHeight, TOP, LEFT);
        drawSinglWeb(g, recWidth, recHeight, TOP, (width / 2) + LEFT);
        g.setColor(Color.red);
        g.fillRect(getWidth() / 2 - 20, 0,40, getHeight());
        drawCell(g);
    }

    private void drawSinglWeb(Graphics g, int stepX, int stepY, int top, int left)
    {
        for (int i = 0; i < 11; i++ )
        {
            int point = (i * stepX) + left;
            g.drawLine(point, top, point,stepY * 10 + top);
        }

        for (int i = 0; i < 11; i++ )
        {
            int point = (i * stepY) + top;
            g.drawLine(left, point, stepX * 10 + left, point);
        }
    }

    //    private void drawFieldWar(Graphics g)
//    {
//        int w = this.getWidth();
//        int h = this.getHeight();
//        int halfWidth = w / 2;
//        int top = 20;
//        int left = 40;
//        int right = 40;
//        int bottom = 20;
//
//
//        int stepX = (halfWidth - right - left) / 11;
//        int stepY = (h - top - bottom) / 11;
//
//        recWidth = stepX;
//        recHeight = stepY;
//
//        for (int i = 0; i < 11; i++ )
//        {
//            int point = (i * stepX) + left;
//            g.drawLine(point, top, point,stepY * 10 + top);
//        }
//
//        for (int i = 0; i < 11; i++ )
//        {
//            int point = (i * stepY) + top;
//            g.drawLine(left, point, stepX * 10 + left, point);
//        }
//    }
//
//    private void drawFieldWarTwo(Graphics g)
//    {
//        int w = this.getWidth();
//        int h = this.getHeight();
//        int top = 20;
//        int left = 572;
//        int right = 40;
//        int bottom = 20;
//
//        int stepX = (w  - left) / 11;
//        int stepY = (h - top - bottom) / 11;
//
//        for (int i = 0; i < 11; i++ )
//        {
//            int point = (i * stepX) + left;
//            g.drawLine(point, top, point,stepY * 10 + top);
//        }
//
//        for (int i = 0; i < 11; i++ )
//        {
//            int point = (i * stepY) + top;
//            g.drawLine(left, point, stepX * 10 + left, point);
//        }
//    }

    public void findCorCell(int corX, int corY)
    {
        if(corX > width / 2)
        {
            setCor(width / 2 + LEFT, width - RIGHT, width - RIGHT, corX, corY, arrayFieldTwo);
            //flagRec = false;
        }else
        {
            setCor(LEFT,width / 2 - RIGHT, (width / 2) - LEFT - RIGHT, corX, corY, arrayField);
            //flagRec = true;
        }
//
//        int x = CorX;
//        int y = CorY;

//        int indexX = 0, indexY = 0;
//        boolean flagX = false;
//        boolean flagY = false;

//        if((x >= LEFT && x <= (width / 2) - RIGHT)  && (y >= TOP && y <= height - BOTTOM))
//        {
//            for (int i = LEFT, i2 = 0; i < ((width / 2) - LEFT - RIGHT); i += recWidth, i2++)
//            {
//                if (i + recWidth > x)
//                {
//                    indexX = i2;
//                    flagX = true;
//                    break;
//                }
//            }
//
//            for (int i = TOP, i2 = 0; i < height - BOTTOM; i += recHeight, i2++)
//            {
//                if (i + recHeight > y)
//                {
//                    indexY = i2;
//                    flagY = true;
//                    break;
//                }
//            }
//            if (flagX && flagY)
//            {
//                arrayField[indexX][indexY] = 1;
//            }
//        }
    }

    private void setCor(int startPoz, int border, int length, int corX, int corY, int array[][])
    {
        int x = corX;
        int y = corY;
        int indexX = 0, indexY = 0;

        boolean flagX = false;
        boolean flagY = false;

        if((x >= startPoz && x <= border)  && (y >= TOP && y <= height - BOTTOM))
        {
            for (int i = startPoz, i2 = 0; i < length; i += recWidth, i2++)
            {
                if (i + recWidth > x)
                {
                    indexX = i2;
                    flagX = true;
                    break;
                }
            }

            for (int i = TOP, i2 = 0; i < height - BOTTOM; i += recHeight, i2++)
            {
                if (i + recHeight > y)
                {
                    indexY = i2;
                    flagY = true;
                    break;
                }
            }
            System.out.println(indexX + " " + indexY);
            if (flagX && flagY)
            {
                array[indexX][indexY] = 1;
            }
        }
    }

    private void drawCell(Graphics g)
    {
        for (int i = 0; i < arrayField.length; i++) {
            for (int j = 0; j < arrayField[i].length; j++) {
                if (arrayField[i][j] == 1) {
                    g.fillRect(i * recWidth + RIGHT, j * recHeight + TOP, recWidth, recHeight);
                }
            }
        }
        for (int i = 0; i < arrayFieldTwo.length; i++) {
            for (int j = 0; j < arrayFieldTwo[i].length; j++) {
                if (arrayFieldTwo[i][j] == 1) {
                    g.fillRect(i * recWidth  + RIGHT + (width / 2), j * recHeight + TOP, recWidth, recHeight);
                }
            }
        }
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public void setShip(int[][] ship)
    {
        arrayField = ship;
        repaint();
    }
}
