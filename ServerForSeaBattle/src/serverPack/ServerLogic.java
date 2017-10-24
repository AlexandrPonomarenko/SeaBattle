package serverPack;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static serverPack.Server.arrayUser;

/**
 * Created by Alexandr on 24.10.2017.
 */
public class ServerLogic implements Runnable{
    private BufferedReader reader;
    private PrintWriter user;
    private Socket socket;
    private ArrayList couple;
    private ArrayList clientConnections;
    private User u;
    private String[] arrayCoordinatesOfTheShot;
    private ArrayQueue<User> couplePlayers;

    public ServerLogic(Socket clientSocket, PrintWriter winter, ArrayList arrayList){
        u = new User(clientSocket, winter);
        couplePlayers = new ArrayQueue(2);
        arrayCoordinatesOfTheShot = new String[3];
    }

    private void addUser(){
        arrayUser.add(u);
    }
    private ArrayList getArrayUser(){
        return arrayUser;
    }

    private void SearchForAnOpponent(String nameOppinent){
        for(int i = 0;i < arrayUser.size();i++){
            if((arrayUser.get(i).getName().equals(nameOppinent))){
                if(!arrayUser.get(i).getStateFlag()){
                    User myOpponent = arrayUser.get(i);
                    u.setOpponent(myOpponent);
                    myOpponent.setOpponent(u);
                    System.out.println(u.getName() + " - " + myOpponent.getName());
                    u.sendMessage("Вы подключтлись к " + myOpponent.getName() + " теперь вы в одной комнате!!!");
                    u.sendMessage("Name opponent-" + u.getName());
                    myOpponent.sendMessage("К вам подключился " + u.getName() + "теперь вы в одной комнате!!!");
                    myOpponent.sendMessage("Name opponent-" + myOpponent.getName());
                    u.sendMessage("start");
                    myOpponent.sendMessage("start");
                    u.setCreateOrConnect("connect");
                    u.setStatusGame(true);
                    myOpponent.setStatusGame(true);
                    myOpponent.setCreateOrConnect("create");
                    couplePlayers.add(arrayUser.get(i));
                    couplePlayers.add(u);
                    arrayUser.remove(arrayUser.get(i));
                    u.setFlag(false);
                    break;
                }
            }
        }
    }
    private void getControlMovePlayers(String coordinatesOfTheShot){
        arrayCoordinatesOfTheShot = coordinatesOfTheShot.split(",");
        if(arrayCoordinatesOfTheShot[1].equals("lost") && arrayCoordinatesOfTheShot[0].equals(couplePlayers.get(0).getName())){
            couplePlayers.get(0).sendAnswerMessage("skip move");
            couplePlayers.get(1).sendAnswerMessage("move");
            return;
        }else if(arrayCoordinatesOfTheShot[1].equals("lost") && arrayCoordinatesOfTheShot[0].equals(couplePlayers.get(1).getName())){
            couplePlayers.get(0).sendAnswerMessage("move");
            couplePlayers.get(1).sendAnswerMessage("skip move");
            return;
        }
        if(arrayCoordinatesOfTheShot[0].equals(couplePlayers.get(0).getName())){
            if(couplePlayers.get(1).shotCheck(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2]))){
                if(couplePlayers.get(1).sumBrokeShips()){
                    if(!couplePlayers.get(1).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])).equals("-")){
                        couplePlayers.get(0).sendAnswerMessage("you" + "," + "WIN" + "," + couplePlayers.get(1).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])));
                        couplePlayers.get(1).sendAnswerMessage("he" + "," + arrayCoordinatesOfTheShot[1] + "," + arrayCoordinatesOfTheShot[2] + "," + "LOSE" + "," + couplePlayers.get(1).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])));
                        return;
                    }
                    couplePlayers.get(0).sendAnswerMessage("you" + "," + "WIN");
                    couplePlayers.get(1).sendAnswerMessage("he" + "," + arrayCoordinatesOfTheShot[1] + "," + arrayCoordinatesOfTheShot[2] + "," +"LOSE");
                    return;
                }
                if(!couplePlayers.get(1).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])).equals("-")){
                    couplePlayers.get(0).sendAnswerMessage("you" + "," + couplePlayers.get(1).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])) + "," + "true");
                    couplePlayers.get(1).sendAnswerMessage("he" + "," + couplePlayers.get(1).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])) + "," + arrayCoordinatesOfTheShot[1] +
                            "," + arrayCoordinatesOfTheShot[2] + "," +"false");
                    return;
                }
                couplePlayers.get(0).sendAnswerMessage("you" + "," + "true");
                couplePlayers.get(1).sendAnswerMessage("he" + "," + arrayCoordinatesOfTheShot[1] + "," + arrayCoordinatesOfTheShot[2] + "," +"false");
            }else if(!couplePlayers.get(1).shotCheck(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2]))){
                couplePlayers.get(0).sendAnswerMessage("you" + "," + "false");
                couplePlayers.get(1).sendAnswerMessage("he" + "," +arrayCoordinatesOfTheShot[1] + "," + arrayCoordinatesOfTheShot[2] + "," +"true");
            }
        }else if(arrayCoordinatesOfTheShot[0].equals(couplePlayers.get(1).getName())){
            if(couplePlayers.get(0).shotCheck(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2]))){
                if(couplePlayers.get(0).sumBrokeShips()){
                    if(!couplePlayers.get(0).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])).equals("-")){
                        couplePlayers.get(1).sendAnswerMessage("you" + "," + "WIN" + "," + couplePlayers.get(1).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])));
                        couplePlayers.get(0).sendAnswerMessage("he" + "," + arrayCoordinatesOfTheShot[1] + "," + arrayCoordinatesOfTheShot[2] + "," + "LOSE" + "," + couplePlayers.get(1).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])));
                        return;
                    }
                    couplePlayers.get(1).sendAnswerMessage("you" + "," + "WIN");
                    couplePlayers.get(0).sendAnswerMessage("he" + "," + arrayCoordinatesOfTheShot[1] + "," + arrayCoordinatesOfTheShot[2] + "," +"LOSE");
                    return;
                }
                if(!couplePlayers.get(0).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])).equals("-")){
                    couplePlayers.get(1).sendAnswerMessage("you" + "," + couplePlayers.get(0).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])) + "," + "true");
                    couplePlayers.get(0).sendAnswerMessage("he" + "," + couplePlayers.get(0).checkDeadShips(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2])) + "," + arrayCoordinatesOfTheShot[1] +
                            "," + arrayCoordinatesOfTheShot[2] + "," +"false");
                    return;
                }
                couplePlayers.get(0).sendAnswerMessage("he" + "," + arrayCoordinatesOfTheShot[1] + "," + arrayCoordinatesOfTheShot[2] + "," +"false");
                couplePlayers.get(1).sendAnswerMessage("you" + "," + "true");
            }else if(!couplePlayers.get(0).shotCheck(Integer.parseInt(arrayCoordinatesOfTheShot[1]),Integer.parseInt(arrayCoordinatesOfTheShot[2]))){
                couplePlayers.get(0).sendAnswerMessage("he" + "," +arrayCoordinatesOfTheShot[1] + "," + arrayCoordinatesOfTheShot[2] + "," +"true");
                couplePlayers.get(1).sendAnswerMessage("you" + "," + "false");
            }
        }
    }

    @Override
    public void run(){
        String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat", shot = "shot", ConnectUser = "ConnectUser"
                , create = "Create", array = "array" ;
        String[] data;
        try {
            while ((message = u.readerUser.readLine()) != null ) {
                data = message.split(":");
                if (data[2].equals(connect)) {
                    u.setName(data[0]);
                    u.createDataArrayUser();
                    u.sendArrayDate();
                }
                else if (data[2].equals(disconnect)) {
                    if(u.getStatusGame()){
                        u.sendDisconnect("STOP");
                    }

                    if(data[0].equals(couplePlayers.get(0).getName())){
                        couplePlayers.get(0).close();
                        couplePlayers.get(0).closeSocket();
                        couplePlayers.remove(0);
                    }else if(data[0].equals(couplePlayers.get(1).getName())){
                        couplePlayers.get(0).close();
                        couplePlayers.get(0).closeSocket();
                        couplePlayers.remove(0);
                    }
                    arrayUser.remove(u);
                }
                else if (data[2].equals(chat)) {
                    u.sendMessage(data[1]);
                }else if(data[2].equals(array)){
                    u.setArrayShip(data[1]);
                }else if (data[2].equals(shot)) {
                    if(couplePlayers.size() < 2){
                        couplePlayers.add(couplePlayers.get(0).getOpponent());
                    }
                    getControlMovePlayers(data[0] + "," +data[1]);
                }else if (data[2].equals(create)) {
                    u.setName(data[1]);
                    u.setTime();
                    u.setFlag(false);
                    addUser();
                    couplePlayers.add(u);
                }else if (data[2].equals(ConnectUser)) {
                    SearchForAnOpponent(u.splitString(data[1]));
                }else {System.out.println("Ничего не зашло");}
            }
        }
        catch (Exception ex) {
            System.out.print("Lost a connection. \n");
            u.close();
            u.closeSocket();
            ex.printStackTrace();
        }
    }

    public class User {
        private BufferedReader readerUser;
        private PrintWriter user;
        private String name = "";
        private Socket socketUser;
        private boolean flag;
        private String userNameOpponent;
        private User opponent;
        private String [][] arrayShip;
        private String IP;
        private String time;
        private String [][] dataUser;
        private String createOrConnect;
        private int sumCellShip = 0;
        private boolean statusGame = false;

        public User(Socket clientSocket, PrintWriter winter){
            flag = true;
            user = winter;
            arrayShip = new String [10][10];
            try{
                socketUser = clientSocket;
                IP = socketUser.getInetAddress().toString();
                InputStreamReader isReader = new InputStreamReader(socketUser.getInputStream());
                readerUser = new BufferedReader(isReader);
            }catch (IOException e){
                close();
                e.printStackTrace();

            }
        }

        private void setTime(){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            time = sdf.format(date);
            time = time.replace(':', '+');
        }
        private String getTime(){
            return time;
        }
        private String getIP(){
            IP = IP.replace('/',' ');
            return IP;
        }
        private void setArrayShip(String  array){
            arrayShip = stringInArrayString(array);
        }
        private void setName(String name){
            this.name = name;
        }

        private void setStatusGame(boolean status){
            statusGame = status;
        }

        private boolean getStatusGame(){
            return statusGame;
        }

        private void setCreateOrConnect(String createOrConnect){
            this.createOrConnect = createOrConnect;
        }

        private String getCreateOrConnect(){
            return createOrConnect;
        }

        private void setUserNameOpponent(String userNameOpponent){
            this.userNameOpponent = userNameOpponent;
        }

        private String getUserNameOpponent(){
            return userNameOpponent;
        }

        private String getName(){
            return name;
        }

        private boolean sumBrokeShips(){
            for(int i = 0; i < arrayShip.length;i++){
                for(int j = 0; j < arrayShip[i].length;j++){
                    if(arrayShip[j][i] == "b"){
                        arrayShip[j][i] = "b1";
                        sumCellShip++;
                    }
                }
            }
            if(sumCellShip == 20){
                return true;
            }
            return false;
        }
        private boolean getStateFlag(){
            return flag;
        }

        private void setFlag(boolean flag){
            this.flag = flag;
        }

        public User getOpponent() {
            return opponent;
        }

        private void setOpponent(User userOpponent){
            opponent = userOpponent;
        }

        private boolean shotCheck(int corX, int corY){
            if(arrayShip[corY][corX].equals("1")){
                arrayShip[corY][corX] = "b";
                return true;
            }
            return false;
        }
        private String [][] stringInArrayString(String stringArray){
            String stringTest[] = stringArray.split(",");
            int count = 0;
            for (int i = 0; i < arrayShip.length; i++) {
                for (int j = 0; j < arrayShip[i].length; j++) {
                    arrayShip[j][i] = stringTest[count];
                    count += 1;
                }
            }
            return arrayShip;
        }

        private void sendMessage(String message){
            try {
                opponent.user.println(name + ":" + message + ":" + "Chat");
                opponent.user.flush();
            }catch (Exception e){
                System.out.println("Error send message");
            }
        }
        private void sendAnswerMessage(String message){
            try {
                user.println(name + ":" + message + ":" + "answer");
                user.flush();
            }catch (Exception e){
                System.out.println("Error send message");
            }
        }

        private String checkDeadShips(int x, int y){
            if(arrayShip[y][x].equals("b1") && checkOne(y,x)){
                return "one";
            }else if(arrayShip[y][x].equals("b1") && !checkTwo(x,y).equals("-")){
                return checkTwo(x,y);
            }else if(arrayShip[y][x].equals("b1") && !checkThree(x,y).equals("-")){
                return checkThree(x,y);
            }else if(arrayShip[y][x].equals("b1") && !checkFour(x,y).equals("-")){
                return checkFour(x,y);
            }
            return "-";
        }

        private boolean checkOne(int x, int y){
            if((y - 1 < 0 || (y - 1 >= 0 && !arrayShip[x][y - 1].equals("b1")) && (y - 1 >= 0 && !arrayShip[x][y - 1].equals("1"))) && //top
                    (x + 1 > arrayShip.length - 1 || (x + 1 <= arrayShip.length - 1 && !arrayShip[x + 1][y].equals("b1")) && (x + 1 <= arrayShip.length - 1 && !arrayShip[x + 1][y].equals("1"))) && // right
                    (y + 1 > arrayShip.length - 1 || (y + 1 <= arrayShip.length - 1 && !arrayShip[x][y + 1].equals("b1")) && (y + 1 <= arrayShip.length - 1 && !arrayShip[x][y + 1].equals("1")))&& // bottom
                    (x - 1 < 0 || (x - 1 >= 0 && !arrayShip[x - 1][y].equals("b1")) && (x - 1 >= 0 && !arrayShip[x - 1][y].equals("1")))){// left)
                return true;
            }
            return false;
        }

        private String checkTwo(int x, int y){
            if((y - 1 >= 0 && arrayShip[y - 1][x].equals("b1")) && (y - 2 < 0 || (y - 2 >= 0 && (!arrayShip[y - 2][x].equals("b1") && !arrayShip[y - 2][x].equals("1")))) &&
                    (y + 1 <= arrayShip.length - 1 && (!arrayShip[y + 1][x].equals("b1") && !arrayShip[y + 1][x].equals("1")))){ // top
                return "two,top";
            }else if((x + 1 <= arrayShip.length - 1 && arrayShip[y][x + 1].equals("b1")) &&
                    ( x + 2 > arrayShip.length - 1 || (x + 2 <= arrayShip.length - 1 && (!arrayShip[y][x + 2].equals("b1") && !arrayShip[y][x + 2].equals("1")))) &&
                    (x - 1 >= 0 && (!arrayShip[y][x - 1].equals("b1") && !arrayShip[y][x - 1].equals("1")))){ //right
                return "two,right";
            }else if((y + 1 <= arrayShip.length - 1 && arrayShip[y + 1][x].equals("b1")) &&
                    (y + 2 > arrayShip.length - 1 || (y + 2 <= arrayShip.length - 1 && (!arrayShip[y + 2][x].equals("b1") && !arrayShip[y + 2][x].equals("1")))) &&
                    (y - 1 >= 0 && (!arrayShip[y - 1][x].equals("b1") && !arrayShip[y - 1][x].equals("1")))){ // bottom
                return "two,bottom";
            }else if((x - 1 >= 0 && arrayShip[y][x - 1].equals("b1")) &&
                    ( x - 2 < 0 || (x - 2 >= 0 && (!arrayShip[y][x - 2].equals("b1") && !arrayShip[y][x - 2].equals("1")))) &&
                    (x + 1 <= arrayShip.length - 1 && (!arrayShip[y][x + 1].equals("b1") && !arrayShip[y][x + 1].equals("1")))){ // left
                return "two,left";
            }
            return "-";
        }

        private String  checkThree(int x, int y){
            if((y - 1 >= 0 && arrayShip[y - 1][x].equals("b1")) &&(y - 2 >= 0 && arrayShip[y - 2][x].equals("b1"))
                    && (y - 3 < 0 || (y - 3 >= 0 && !arrayShip[y - 3][x].equals("b1"))) &&
                    (((y + 1 <= arrayShip.length - 1 && (!arrayShip[y + 1][x].equals("b1") && !arrayShip[y + 1][x].equals("1"))) || y + 1 > arrayShip.length - 1))){ // top
                return "three,top";
            }else if((x + 1 <= arrayShip.length - 1 && arrayShip[y][x + 1].equals("b1")) && (x + 2 <= arrayShip.length - 1 && arrayShip[y][x + 2].equals("b1")) &&
                    ( x + 3 > arrayShip.length - 1 || (x + 3 <= arrayShip.length - 1 && (!arrayShip[y][x + 3].equals("b1") && !arrayShip[y][x + 3].equals("1")))) &&
                    (((x - 1 >= 0 && (!arrayShip[y][x - 1].equals("b1") && !arrayShip[y][x - 1].equals("1"))) || x - 1 < 0))){ //right
                return "three,right";
            }else if((y + 1 <= arrayShip.length - 1 && arrayShip[y + 1][x].equals("b1")) && (y + 2 <= arrayShip.length - 1 && arrayShip[y + 2][x].equals("b1"))&&
                    (y + 3 > arrayShip.length - 1 || (y + 3 <= arrayShip.length - 1 && (!arrayShip[y + 3][x].equals("b1") && !arrayShip[y + 3][x].equals("1"))))
                    && (((y - 1 >= 0 && (!arrayShip[y - 1][x].equals("b1") && !arrayShip[y - 1][x].equals("1"))) || y - 1 < 0))){ // bottom
                return "three,bottom";
            }else if((x - 1 >= 0 && arrayShip[y][x - 1].equals("b1")) && (x - 2 >= 0 && arrayShip[y][x - 2].equals("b1")) &&
                    ( x - 3 < 0 || (x - 3 >= 0 && (!arrayShip[y][x - 3].equals("b1") && !arrayShip[y][x - 3].equals("1")))) &&
                    (((x + 1 <= arrayShip.length - 1 && (!arrayShip[y][x + 1].equals("b1") && !arrayShip[y][x + 1].equals("1"))) || x + 1 > arrayShip.length - 1))){ // left
                return "three,left";
            }else if((y - 1 >= 0 && arrayShip[y - 1][x].equals("b1")) && (y + 1 <= arrayShip.length - 1 && arrayShip[y + 1][x].equals("b1")) &&
                    (y - 2 < 0 || (y - 2 >= 0 && (!arrayShip[y - 2][x].equals("b1") && !arrayShip[y - 2][x].equals("1")))) &&
                    (y + 2 > arrayShip.length - 1 || (y + 2 <= arrayShip.length - 1 && (!arrayShip[y + 2][x].equals("b1") && !arrayShip[y + 2][x].equals("1"))))){
                return "three,tb";
            }else if((x + 1 <= arrayShip.length - 1 && arrayShip[y][x + 1].equals("b1")) && (x - 1 >= 0 && arrayShip[y][x - 1].equals("b1")) &&
                    ( x + 2 > arrayShip.length - 1 || (x + 2 <= arrayShip.length - 1 && (!arrayShip[y][x + 2].equals("b1") && !arrayShip[y][x + 2].equals("1"))))&&
                    ( x - 2 < 0 || (x - 2 >= 0 && (!arrayShip[y][x - 2].equals("b1") && !arrayShip[y][x - 2].equals("1"))))){
                return "three,lr";
            }
            return "-";
        }

        private String  checkFour(int x, int y){
            if((y - 1 >= 0 && arrayShip[y - 1][x].equals("b1")) && (y - 2 >= 0 && arrayShip[y - 2][x].equals("b1")) && (y - 3 >= 0 && arrayShip[y - 3][x].equals("b1")) &&
                    (y - 4 < 0 || (y - 4 >= 0 && !arrayShip[y - 4][x].equals("b1"))) &&
                    (((y + 1 <= arrayShip.length - 1 && (!arrayShip[y + 1][x].equals("b1") || !arrayShip[y + 1][x].equals("1"))) || y + 1 > arrayShip.length - 1))){
                return "four,top";
            }else if((x + 1 <= arrayShip.length - 1 && arrayShip[y][x + 1].equals("b1")) &&(x + 2 <= arrayShip.length - 1 && arrayShip[y][x + 2].equals("b1")) &&
                    (x + 3 <= arrayShip.length - 1 && arrayShip[y][x + 3].equals("b1")) && ( x + 4 > arrayShip.length - 1 || (x + 4 <= arrayShip.length - 1 && !arrayShip[y][x + 4].equals("b1"))) &&
                    (((x - 1 >= 0 && (!arrayShip[y][x - 1].equals("b1") ||!arrayShip[y][x - 1].equals("1"))) || x - 1 < 0))){
                return "four,right";
            }else if((y + 1 <= arrayShip.length - 1 && arrayShip[y + 1][x].equals("b1")) && (y + 2 <= arrayShip.length - 1 && arrayShip[y + 2][x].equals("b1"))&&
                    (y + 3 <= arrayShip.length - 1 && arrayShip[y + 3][x].equals("b1")) && (y + 4 > arrayShip.length - 1 || (y + 4 <= arrayShip.length - 1 && !arrayShip[y + 4][x].equals("b1"))) &&
                    (((y - 1 >= 0 && (!arrayShip[y - 1][x].equals("b1") || !arrayShip[y - 1][x].equals("1"))) || y - 1 < 0))){
                return "four,bottom";
            }else if((x - 1 >= 0 && arrayShip[y][x - 1].equals("b1")) && (x - 2 >= 0 && arrayShip[y][x - 2].equals("b1")) &&
                    (x - 3 >= 0 && arrayShip[y][x - 3].equals("b1")) && ( x - 4 < 0 || (x - 4 >= 0 && !arrayShip[y][x - 4].equals("b1"))) &&
                    (((x + 1 <= arrayShip.length - 1 && (!arrayShip[y][x + 1].equals("b1") || !arrayShip[y][x + 1].equals("1"))) || x + 1 > arrayShip.length - 1))){
                return "four,left";
            }else if((y - 1 >= 0 && arrayShip[y - 1][x].equals("b1")) && (y - 2 >= 0 && arrayShip[y - 2][x].equals("b1")) && (y + 1 >= 0 && arrayShip[y + 1][x].equals("b1")) &&
                    (y - 3 < 0 || (y - 3 >= 0 && !arrayShip[y - 3][x].equals("b1"))) &&
                    (y + 2 > arrayShip.length - 1 || (y + 2 <= arrayShip.length - 1 && !arrayShip[y + 2][x].equals("b1")))){
                return "four,dtb";
            }else if((y + 1 <= arrayShip.length - 1 && arrayShip[y + 1][x].equals("b1")) &&
                    (y + 2 <= arrayShip.length - 1 && arrayShip[y + 2][x].equals("b1"))&&
                    (y - 1 >= 0 && arrayShip[y - 1][x].equals("b1")) &&
                    (y + 3 > arrayShip.length - 1 || (y + 3 <= arrayShip.length - 1 && !arrayShip[y + 3][x].equals("b1"))) &&
                    (y - 2 < 0 || (y - 2 >= 0 && !arrayShip[y + 2][x].equals("b1")))){
                return "four,dbt";
            }else if((x + 1 <= arrayShip.length - 1 && arrayShip[y][x + 1].equals("b1")) &&
                    (x + 2 <= arrayShip.length - 1 && arrayShip[y][x + 2].equals("b1")) &&
                    (x - 1 >= 0 && arrayShip[y][x + 1].equals("b1")) &&
                    (x + 3 > arrayShip.length - 1 || (x + 3 <= arrayShip.length - 1 && arrayShip[y][x + 3].equals("b1"))) &&
                    (x - 2 < 0 || (x - 2 >= 0 && !arrayShip[y][x - 2].equals("b1")))){
                return "four,drl";
            }else if((x - 1 >= 0 && arrayShip[y][x - 1].equals("b1")) && (x - 2 >= 0 && arrayShip[y][x - 2].equals("b1")) &&
                    (x + 1 <= arrayShip.length - 1 && arrayShip[y][x + 1].equals("b1")) &&
                    (x - 3 < 0 || (x - 3 >= 0 && !arrayShip[y][x - 3].equals("b1"))) &&
                    (x + 2 > arrayShip.length - 1 || (x + 2 <= arrayShip.length - 1 && !arrayShip[y][x + 2].equals("b1")))){
                return "four,dlr";
            }
            return "-";
        }

        private void createDataArrayUser(){
            if(arrayUser.size() == 0){
                dataUser = new String[1][1];
                dataUser[0][0] = "default";
                return;
            }else if(arrayUser.size() > 0){
                dataUser = new String[arrayUser.size()][3];
                for(int i = 0; i < arrayUser.size();i++){
                    if(!(arrayUser.get(i).getStateFlag())){
                        dataUser[i][0] = arrayUser.get(i).getName();
                        dataUser[i][1] = arrayUser.get(i).getTime();
                        dataUser[i][2] = arrayUser.get(i).getIP();
                    }
                }
            }
        }

        private void sendArrayDate(){
            try {
                user.println(arrayInString(dataUser));
                user.flush();
            }catch (Exception e){e.printStackTrace();}
        }
        private String arrayInString(String[][] test){
            String tempString = "";
            if(test[0][0].equals("default")){
                tempString = test[0][0];
            }else if(!test[0][0].equals("default")) {
                String tst = "";
                for (int i = 0; i < test.length; i++) {
                    for (int j = 0; j < test[i].length; j++) {
                        tst += test[i][j] + ",";
                    }
                }
                tempString = tst.substring(0, tst.length() - 1);
                return name + ":" + tempString + ":" + "array";
            }
            return name + ":" + tempString + ":" + "array";
        }
        private String splitString(String namesUsers){
            String [] names = namesUsers.split(",");
            setName(names[0]);
            return names[1];
        }
        private void sendDisconnect(String message){
            if(!opponent.equals(null)){
                opponent.user.println(name + ":" + message + ":" + "Disconnect");
                opponent.user.flush();
            }
        }

        private void closeSocket(){
            try {
                socketUser.close();
            }catch (IOException e){
                System.out.println("Закрый сокет не удалось");
                e.printStackTrace();
            }
        }

        private void close(){
            try {
                readerUser.close();
                user.close();
            }catch (IOException e){
                System.out.println("Закрый поток не удалось");
                e.printStackTrace();
            }
        }
    }
}
