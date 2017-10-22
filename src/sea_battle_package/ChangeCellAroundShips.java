package sea_battle_package;

/**
 * Created by Alexandr on 22.10.2017.
 */
public class ChangeCellAroundShips {

    public ChangeCellAroundShips(){

    }

    public void checkAroundOneShip( int array[][], int corX, int corY){
        for (int i = 0; i < 8; i++){
            if(i == 0){
                if(corY - 1 >= 0){
                    array[corX][corY - 1] = 0;
                }
            }else if(i == 1){
                if(corY - 1 >= 0 && corX + 1 <= array.length - 1){
                    array[corX + 1][corY - 1] = 0;
                }
            }else if(i == 2){
                if(corX + 1 <= array.length - 1){
                    array[corX + 1][corY] = 0;
                }
            }else if(i == 3){
                if(corX + 1 <= array.length - 1 && corY + 1 <= array.length - 1){
                    array[corX + 1][corY + 1] = 0;
                }
            }else if(i == 4){
                if(corY + 1 <= array.length - 1){
                    array[corX][corY + 1] = 0;
                }
            }else if(i == 5){
                if(corX - 1 >= 0 && corY + 1 <= array.length - 1){
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

    public void checkTwoShipSide(int array[][], int x, int y, String side){
        if(side.equals("top")){
            for(int i = 0; i < 10; i++){
                if(i == 0){
                    if(y - 2 >= 0)array[x][y - 2] = 0; // double top
                }else if(i == 1){
                    if(y - 2 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 2] = 0; // double top right
                }else if(i == 2){
                    if(y - 1 >=0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0;// top right
                }else if(i == 3){
                    if(x + 1 <= array.length - 1)array[x + 1][y] = 0;// right
                }else if(i == 4){
                    if(x + 1 <= array.length - 1 && y + 1 <= array.length - 1) array[x + 1][y + 1] = 0;// bottom right
                }else if(i == 5){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0;// bottom
                }else if(i == 6){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
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
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(y - 1 >=0 && x + 2 <= array.length - 1)array[x + 2][y - 1] = 0; // top double right
                }else if(i == 3){
                    if(x + 2 <= array.length - 1)array[x + 2][y] = 0; // double right
                }else if(i == 4){
                    if(x + 2 <= array.length - 1 && y + 1 <= array.length - 1) array[x + 2][y + 1] = 0; // double right bottom
                }else if(i == 5){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 6){
                    if(y + 1 <= array.length - 1)array[x ][y + 1] = 0; // bottom
                }else if(i == 7){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
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
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(x + 1 <= array.length - 1)array[x + 1][y] = 0; //  right
                }else if(i == 3){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 4){
                    if(x + 1 <= array.length - 1 && y + 2 <= array.length - 1) array[x + 1][y + 2] = 0; // double bottom right
                }else if(i == 5){
                    if(y + 2 <= array.length - 1)array[x][y + 2] = 0; // double bottom
                }else if(i == 6){
                    if( x - 1 >= 0 && y + 2 <= array.length - 1)array[x - 1][y + 2] = 0; // double bottom left
                }else if(i == 7){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
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
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(x + 1 <= array.length - 1)array[x + 1][y] = 0; //  right
                }else if(i == 3){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 4){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0; // bottom
                }else if(i == 5){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 6){
                    if( x - 2 >= 0 && y + 1 <= array.length - 1)array[x - 2][y + 1] = 0; // double left bottom
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

    public void checkThreeShipSide(int array[][], int x, int y, String side){
        if(side.equals("top")){
            for(int i = 0; i < 12; i++){
                if(i == 0){
                    if(y - 3 >= 0)array[x][y - 3] = 0; // triple top
                }else if(i == 1){
                    if(y - 3 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 3] = 0; // triple top right
                }else if(i == 2){
                    if(y - 2 >=0 && x + 1 <= array.length - 1)array[x + 1][y - 2] = 0;// double top right
                }else if(i == 3){
                    if(y - 1 >=0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0;// top right
                }else if(i == 4){
                    if(x + 1 <= array.length - 1) array[x + 1][y] = 0;// right
                }else if(i == 5){
                    if(x + 1 <= array.length - 1 && y + 1 <= array.length - 1)array[x + 1][y + 1] = 0;// right bottom
                }else if(i == 6){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0; // bottom
                }else if(i == 7){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 8){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 9){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }else if(i == 10){
                    if(x - 1 >= 0 && y - 2 >= 0)array[x - 1][y - 2] = 0; // left double top
                }else if(i == 11){
                    if(x - 1 >= 0 && y - 3 >= 0)array[x - 1][y - 3] = 0; // left triple top
                }
            }
        }else if(side.equals("right")){
            for(int i = 0; i < 12; i++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(y - 1 >=0 && x + 2 <= array.length - 1)array[x + 2][y - 1] = 0; // top double right
                }else if(i == 3){
                    if(y - 1 >=0 && x + 3 <= array.length - 1)array[x + 3][y - 1] = 0; // top triple right
                }else if(i == 4){
                    if(x + 3 <= array.length - 1)array[x + 3][y] = 0; // triple right
                }else if(i == 5){
                    if(x + 3 <= array.length - 1 && y + 1 <= array.length - 1) array[x + 3][y + 1] = 0; // triple right bottom
                }else if(i == 6){
                    if(x + 2 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 2][y + 1] = 0; // double right bottom
                }else if(i == 7){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                } else if(i == 8){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0; // bottom
                }else if(i == 9){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 10){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 11){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }else if(side.equals("bottom")){
            for(int i = 0; i < 12; i++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(x + 1 <= array.length - 1)array[x + 1][y] = 0; //  right
                }else if(i == 3){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 4){
                    if(x + 1 <= array.length - 1 && y + 2 <= array.length - 1) array[x + 1][y + 2] = 0; // double bottom right
                }else if(i == 5){
                    if(x + 1 <= array.length - 1 && y + 3 <= array.length - 1) array[x + 1][y + 3] = 0; // triple bottom right
                }else if(i == 6){
                    if(y + 3 <= array.length - 1)array[x][y + 3] = 0; // triple bottom
                }else if(i == 7){
                    if( x - 1 >= 0 && y + 3 <= array.length - 1)array[x - 1][y + 3] = 0; // triple bottom left
                }else if(i == 8){
                    if( x - 1 >= 0 && y + 2 <= array.length - 1)array[x - 1][y + 2] = 0; // double bottom left
                }else if(i == 9){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 10){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 11){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }else if(side.equals("left")){
            for(int i = 0; i < 12; i++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(x + 1 <= array.length - 1)array[x + 1][y] = 0; //  right
                }else if(i == 3){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 4){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0; // bottom
                }else if(i == 5){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 6){
                    if( x - 2 >= 0 && y + 1 <= array.length - 1)array[x - 2][y + 1] = 0; // double left bottom
                }else if(i == 7){
                    if( x - 3 >= 0 && y + 1 <= array.length - 1)array[x - 3][y + 1] = 0; // triple left bottom
                }else if(i == 8){
                    if(x - 3 >= 0)array[x - 3][y] = 0; // triple left
                }else if(i == 9){
                    if(x - 3 >= 0 && y - 1 >= 0)array[x - 3][y - 1] = 0; // triple left top
                }else if(i == 10){
                    if(x - 2 >= 0 && y - 1 >= 0)array[x - 2][y - 1] = 0; // double left top
                }else if(i == 11){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }else if(side.equals("tb")){
            for(int i = 0; i < 12; i++){
                if(i == 0){
                    if(y - 2 >= 0)array[x][y - 2] = 0; // double top
                }else if(i == 1){
                    if(y - 2 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 2] = 0; // double top right
                }else if(i == 2){
                    if(y - 1 >=0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0;// top right
                }else if(i == 3){
                    if(x + 1 <= array.length - 1)array[x + 1][y] = 0;// right
                }else if(i == 4){
                    if(x + 1 <= array.length - 1 && y + 1 <= array.length - 1) array[x + 1][y + 1] = 0;// bottom right
                }else if(i == 5){
                    if(x + 1 <= array.length - 1 && y + 2 <= array.length - 1) array[x + 1][y + 2] = 0;// double bottom right
                }else if(i == 6){
                    if(y + 2 <= array.length - 1)array[x][y + 2] = 0;// double bottom
                }else if(i == 7){
                    if(x - 1 >= 0 && y + 2 <= array.length - 1)array[x - 1][y + 2] = 0; // double bottom left
                }else if(i == 8){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 9){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 10){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }else if(i == 11){
                    if(x - 1 >= 0 && y - 2 >= 0)array[x - 1][y - 2] = 0; // // left double top
                }
            }
        }else if(side.equals("lr")){
            for(int i = 0; i < 12; i++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(y - 1 >=0 && x + 2 <= array.length - 1)array[x + 2][y - 1] = 0; // top double right
                }else if(i == 3){
                    if(x + 2 <= array.length - 1)array[x + 2][y] = 0; // double right
                }else if(i == 4){
                    if(x + 2 <= array.length - 1 && y + 1 <= array.length - 1) array[x + 2][y + 1] = 0; // double right bottom
                }else if(i == 5){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 6){
                    if(y + 1 <= array.length - 1)array[x ][y + 1] = 0; // bottom
                }else if(i == 7){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 8){
                    if(x - 2 >= 0 && y + 1 <= array.length - 1)array[x - 2][y + 1] = 0; // bottom  double left
                }else if(i == 9){
                    if(x - 2 >= 0)array[x - 2][y] = 0; //  double left
                }else if(i == 10){
                    if(x - 2 >= 0 && y - 1 >= 0)array[x - 2][y - 1] = 0; // double left top
                }else if(i == 11){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }
    }

    public void checkFourShipSide(int array[][], int x, int y, String side){
        if(side.equals("top")){
            for(int i = 0; i < 14;i ++){
                if(i == 0){
                    if(y - 4 >= 0)array[x][y - 4] = 0; // quadruple top
                }else if(i == 1){
                    if(y - 4 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 4] = 0; // quadruple top right
                }else if(i == 2){
                    if(y - 3 >=0 && x + 1 <= array.length - 1)array[x + 1][y - 3] = 0;// triple top right
                }else if(i == 3){
                    if(y - 2 >=0 && x + 1 <= array.length - 1)array[x + 1][y - 2] = 0;// double top right
                }else if(i == 4){
                    if(y - 1 >=0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0;// top right
                }else if(i == 5){
                    if(x + 1 <= array.length - 1) array[x + 1][y] = 0;// right
                }else if(i == 6){
                    if(x + 1 <= array.length - 1 && y + 1 <= array.length - 1)array[x + 1][y + 1] = 0;// right bottom
                }else if(i == 7){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0; // bottom
                }else if(i == 8){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 9){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 10){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }else if(i == 11){
                    if(x - 1 >= 0 && y - 2 >= 0)array[x - 1][y - 2] = 0; // left double top
                }else if(i == 12){
                    if(x - 1 >= 0 && y - 3 >= 0)array[x - 1][y - 3] = 0; // left triple top
                }else if(i == 13){
                    if(x - 1 >= 0 && y - 4 >= 0)array[x - 1][y - 4] = 0; // left quadruple top
                }
            }
        }else if(side.equals("right")){
            for(int i = 0; i < 14;i ++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(y - 1 >=0 && x + 2 <= array.length - 1)array[x + 2][y - 1] = 0; // top double right
                }else if(i == 3){
                    if(y - 1 >=0 && x + 3 <= array.length - 1)array[x + 3][y - 1] = 0; // top triple right
                }else if(i == 4){
                    if(y - 1 >=0 && x + 4 <= array.length - 1)array[x + 4][y - 1] = 0; // top quadruple right
                }else if(i == 5){
                    if(x + 4 <= array.length - 1)array[x + 4][y] = 0; // quadruple right
                }else if(i == 6){
                    if(x + 4 <= array.length - 1 && y + 1 <= array.length - 1) array[x + 4][y + 1] = 0; // quadruple right bottom
                }else if(i == 7){
                    if(x + 3 <= array.length - 1 && y + 1 <= array.length - 1) array[x + 3][y + 1] = 0; // triple right bottom
                }else if(i == 8){
                    if(x + 2 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 2][y + 1] = 0; // double right bottom
                }else if(i == 9){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                } else if(i == 10){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0; // bottom
                }else if(i == 11){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 12){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 13){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }else if(side.equals("bottom")){
            for(int i = 0; i < 14;i ++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(x + 1 <= array.length - 1)array[x + 1][y] = 0; //  right
                }else if(i == 3){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 4){
                    if(x + 1 <= array.length - 1 && y + 2 <= array.length - 1) array[x + 1][y + 2] = 0; // double bottom right
                }else if(i == 5){
                    if(x + 1 <= array.length - 1 && y + 3 <= array.length - 1) array[x + 1][y + 3] = 0; // triple bottom right
                }else if(i == 6){
                    if(x + 1 <= array.length - 1 && y + 4 <= array.length - 1) array[x + 1][y + 4] = 0; // quadruple bottom right
                }else if(i == 7){
                    if(y + 4 <= array.length - 1)array[x][y + 4] = 0; // quadrupole bottom
                }else if(i == 8){
                    if( x - 1 >= 0 && y + 4 <= array.length - 1)array[x - 1][y + 4] = 0; // quadruple bottom left
                }else if(i == 9){
                    if( x - 1 >= 0 && y + 3 <= array.length - 1)array[x - 1][y + 3] = 0; // triple bottom left
                }else if(i == 10){
                    if( x - 1 >= 0 && y + 2 <= array.length - 1)array[x - 1][y + 2] = 0; // double bottom left
                }else if(i == 11){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 12){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 13){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }else if(side.equals("left")){
            for(int i = 0; i < 14;i ++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(x + 1 <= array.length - 1)array[x + 1][y] = 0; //  right
                }else if(i == 3){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 4){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0; // bottom
                }else if(i == 5){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 6){
                    if( x - 2 >= 0 && y + 1 <= array.length - 1)array[x - 2][y + 1] = 0; // double left bottom
                }else if(i == 7){
                    if( x - 3 >= 0 && y + 1 <= array.length - 1)array[x - 3][y + 1] = 0; // triple left bottom
                }else if(i == 8){
                    if( x - 4 >= 0 && y + 1 <= array.length - 1)array[x - 4][y + 1] = 0; // quadruple left bottom
                }else if(i == 9){
                    if(x - 4 >= 0)array[x - 4][y] = 0; // quadruple left
                }else if(i == 10){
                    if(x - 4 >= 0 && y - 1 >= 0)array[x - 4][y - 1] = 0; // quadruple left top
                }else if(i == 11){
                    if(x - 3 >= 0 && y - 1 >= 0)array[x - 3][y - 1] = 0; // triple left top
                }else if(i == 12){
                    if(x - 2 >= 0 && y - 1 >= 0)array[x - 2][y - 1] = 0; // double left top
                }else if(i == 13){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }else if(side.equals("dtb")){
            for(int i = 0; i < 14;i ++){
                if(i == 0){
                    if(y - 3 >= 0)array[x][y - 3] = 0; // triple top
                }else if(i == 1){
                    if(y - 3 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 3] = 0; // triple top right
                }else if(i == 2){
                    if(y - 2 >=0 && x + 1 <= array.length - 1)array[x + 1][y - 2] = 0;// double top right
                }else if(i == 3){
                    if(y - 1 >=0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0;// top right
                }else if(i == 4){
                    if(x + 1 <= array.length - 1) array[x + 1][y] = 0;// right
                }else if(i == 5){
                    if(x + 1 <= array.length - 1 && y + 1 <= array.length - 1)array[x + 1][y + 1] = 0;// right bottom
                }else if(i == 6){
                    if(x + 1 <= array.length - 1 && y + 2 <= array.length - 1)array[x + 1][y + 2] = 0;// double right bottom
                }else if(i == 7){
                    if(y + 2 <= array.length - 1)array[x][y + 2] = 0; // double bottom
                }else if(i == 8){
                    if(x - 1 >= 0 && y + 2 <= array.length - 1)array[x - 1][y + 2] = 0; // double bottom left
                }else if(i == 9){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 10){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 11){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }else if(i == 12){
                    if(x - 1 >= 0 && y - 2 >= 0)array[x - 1][y - 2] = 0; // left double top
                }else if(i == 13){
                    if(x - 1 >= 0 && y - 3 >= 0)array[x - 1][y - 3] = 0; // left triple top
                }
            }
        }else if(side.equals("dbt")){
            for(int i = 0; i < 14;i ++){
                if(i == 0){
                    if(y - 2 >= 0)array[x][y - 2] = 0; // double top
                }else if(i == 1){
                    if(y - 2 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 2] = 0; // double top right
                }else if(i == 2){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 3){
                    if(x + 1 <= array.length - 1)array[x + 1][y] = 0; //  right
                }else if(i == 4){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 5){
                    if(x + 1 <= array.length - 1 && y + 2 <= array.length - 1) array[x + 1][y + 2] = 0; // double bottom right
                }else if(i == 6){
                    if(x + 1 <= array.length - 1 && y + 3 <= array.length - 1) array[x + 1][y + 3] = 0; // triple bottom right
                }else if(i == 7){
                    if(y + 3 <= array.length - 1)array[x][y + 3] = 0; // triple bottom
                }else if(i == 8){
                    if( x - 1 >= 0 && y + 3 <= array.length - 1)array[x - 1][y + 3] = 0; // triple bottom left
                }else if(i == 9){
                    if( x - 1 >= 0 && y + 2 <= array.length - 1)array[x - 1][y + 2] = 0; // double bottom left
                }else if(i == 10){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 11){
                    if(x - 1 >= 0)array[x - 1][y] = 0; // left
                }else if(i == 12){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }else if(i == 13){
                    if(x - 1 >= 0 && y - 2 >= 0)array[x - 1][y - 2] = 0; // left double top
                }
            }
        }else if(side.equals("drl")){
            for(int i = 0; i < 14;i ++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(y - 1 >=0 && x + 2 <= array.length - 1)array[x + 2][y - 1] = 0; // top double right
                }else if(i == 3){
                    if(y - 1 >=0 && x + 3 <= array.length - 1)array[x + 3][y - 1] = 0; // top triple right
                }else if(i == 4){
                    if(x + 3 <= array.length - 1)array[x + 3][y] = 0; // triple right
                }else if(i == 5){
                    if(x + 3 <= array.length - 1 && y + 1 <= array.length - 1) array[x + 3][y + 1] = 0; // triple right bottom
                }else if(i == 6){
                    if(x + 2 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 2][y + 1] = 0; // double right bottom
                }else if(i == 7){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                } else if(i == 8){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0; // bottom
                }else if(i == 9){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 10){
                    if(x - 2 >= 0 && y + 1 <= array.length - 1)array[x - 2][y + 1] = 0; // bottom double left
                }else if(i == 11){
                    if(x - 2 >= 0)array[x - 2][y] = 0; // double left
                }else if(i == 12){
                    if(x - 2 >= 0 && y - 1 >= 0)array[x - 2][y - 1] = 0; // double left top
                }else if(i == 13){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }else if(side.equals("dlr")){
            for(int i = 0; i < 14;i ++){
                if(i == 0){
                    if(y - 1 >= 0)array[x][y - 1] = 0; // top
                }else if(i == 1){
                    if(y - 1 >= 0 && x + 1 <= array.length - 1)array[x + 1][y - 1] = 0; // top right
                }else if(i == 2){
                    if(y - 1 >= 0 && x + 2 <= array.length - 1)array[x + 2][y - 1] = 0; // top double right
                }else if(i == 3){
                    if(x + 2 <= array.length - 1)array[x + 2][y] = 0; //  double right
                }else if(i == 4){
                    if(x + 2 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 2][y + 1] = 0; // double right bottom
                }else if(i == 5){
                    if(x + 1 <= array.length - 1 &&  y + 1 <= array.length - 1)array[x + 1][y + 1] = 0; // right bottom
                }else if(i == 6){
                    if(y + 1 <= array.length - 1)array[x][y + 1] = 0; // bottom
                }else if(i == 7){
                    if(x - 1 >= 0 && y + 1 <= array.length - 1)array[x - 1][y + 1] = 0; // bottom left
                }else if(i == 8){
                    if( x - 2 >= 0 && y + 1 <= array.length - 1)array[x - 2][y + 1] = 0; // double left bottom
                }else if(i == 9){
                    if( x - 3 >= 0 && y + 1 <= array.length - 1)array[x - 3][y + 1] = 0; // triple left bottom
                }else if(i == 10){
                    if(x - 3 >= 0)array[x - 3][y] = 0; // triple left
                }else if(i == 11){
                    if(x - 3 >= 0 && y - 1 >= 0)array[x - 3][y - 1] = 0; // triple left top
                }else if(i == 12){
                    if(x - 2 >= 0 && y - 1 >= 0)array[x - 2][y - 1] = 0; // double left top
                }else if(i == 13){
                    if(x - 1 >= 0 && y - 1 >= 0)array[x - 1][y - 1] = 0; // left top
                }
            }
        }
    }

}
