package sea_battle_package;

import javax.swing.event.EventListenerList;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Created by Alexandr on 02.08.2017.
 */
public class Client {
    private String username = Integer.toString(new Random().nextInt(100) + 1), address = "localhost";
    private Boolean isConnected = false;
    private ArrayList<String> users = new ArrayList();
    private int port = 9000;
    private Socket sock;
    private BufferedReader reader;
    private PrintWriter writer;
    private Scanner sc;
    private String massage;
    private String[][] dataTable;
    private String nameUser;
    private String nameUserFromTableToConnect;
    private EventListenerList eventListenerList;
    private EventListenerList listenerList;

    public Client(){
//        sc = new Scanner(System.in);
//        connectServer();
//        try {
//            oos = new ObjectOutputStream(sock.getOutputStream());
//            ois = new ObjectInputStream(sock.getInputStream());
//        }catch(IOException e){
//            System.out.println("Проблема с потоком обьектов");
//            e.printStackTrace();
//        }
//        try {
//            while ((massage = sc.nextLine()) != null){
//
//                if(massage.equals("quit")){
//                    sendDisconnect();
//                    dissconnect();
//                }
//                sendMassage(massage);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        listenerList = new EventListenerList();
    }

    private void StartThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    private void userAdd(String name) {
        users.add(name);
    }


    public void writeUsers() {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token:tempList) {
            //users.append(token + "\n");
        }
    }

    public boolean getStatusConnection(){
        return isConnected;
    }
    public void sendMassage(String message) {
        try {
            writer.println(username + ":" + message + ":" + "Chat");
            writer.flush();
        }
        catch (Exception ex) {
            System.out.println("sendMassage" + "Message was not sent.");
        }
    }

    public void sendMassageCommand(String name, String commandWords) {
        try {
            System.out.println("ЭТО В МЕТОДЕ sendMassageCommand " + nameUser + ":" + nameUser + ":" + nameUserFromTableToConnect);
//            message = "Hello";
            System.out.println("ЭТО В МЕТОДЕ sendMassageCommand " + username + ":" + nameUser + ":" + commandWords);
            if(commandWords.equals("Create")){
                writer.println(username + ":" + nameUser + ":" + commandWords);
                username = nameUser;
                writer.flush();
            }else if(commandWords.equals("ConnectUser")) {
                writer.println(username + ":" + nameUser + ":" + commandWords);
                username = nameUser;
                splitNames(username);
                writer.flush();
            }
        }
        catch (Exception ex) {
            System.out.println("sendMassage" + "Message was not sent.");
        }
    }

    private void splitNames(String simple){
        String tempArrayNames[] = simple.split(",");
        username = tempArrayNames[0];
        System.out.println("ЭТО В МЕТОДЕ splitNames " + username);
    }
    public void sendArrayShip(int [][] arrayCoordinatesShip){
        try {
            writer.println(username + ":" + intInString(arrayCoordinatesShip) + ":" + "array");
            writer.flush();
        } catch (Exception ex) {
            System.out.println("НЕ УДАЛОСЬ ОТПРАВИТЬ МАССИВ");
        }
    }


    public void sendCoordinatesShot(String coordinatesShot){
        System.out.println("В МЕТОДЕ sendCoordinatesShot " + coordinatesShot);
        if(coordinatesShot.equals("bye")){
            sendDisconnect();
            dissconnect();
            return;
        }
        if(!coordinatesShot.equals("clear")){
            try {
                System.out.println("ПЕРЕД ОТПРАВКОЙ СООРДИНАТОВ  " + username + ":" + coordinatesShot + ":" + "shot");
                writer.println(username + ":" + coordinatesShot + ":" + "shot");
                System.out.println(username + ":" + coordinatesShot + ":" + "shot");
                writer.flush();
            } catch (Exception ex) {
                System.out.println("НЕ УДАЛОСЬ ОТПРАВИТЬ КООРДИНАТЫ");
            }
        }else {
            System.out.println(coordinatesShot + "ПРИШЕЛ CLEAR");
        }
//        try {
//            System.out.println("ПЕРЕД ОТПРАВКОЙ СООРДИНАТОВ  " + username + ":" + coordinatesShot + ":" + "shot");
//            writer.println(username + ":" + coordinatesShot + ":" + "shot");
//            System.out.println(username + ":" + coordinatesShot + ":" + "shot");
//            writer.flush();
//        } catch (Exception ex) {
//            System.out.println("НЕ УДАЛОСЬ ОТПРАВИТЬ КООРДИНАТЫ");
//        }
    }

    public void setNameUser(String nameUser){
        this.nameUser = nameUser;
    }

    public void setNameUserFromTableToConnect(String nameUser){
        nameUserFromTableToConnect = nameUser;
        System.out.println(nameUserFromTableToConnect + "ЭТО В КЛИЕНТЕ");
    }

    private String intInString(int[][] arrayCoordinatesShip){
        String arrayCoordinatesShipToString = "";
        for(int i = 0; i < arrayCoordinatesShip.length;i++){
            for(int j = 0; j < arrayCoordinatesShip[i].length;j++) {
                arrayCoordinatesShipToString +=arrayCoordinatesShip[i][j] + ",";
            }
        }
        return arrayCoordinatesShipToString.substring(0,arrayCoordinatesShipToString.length() - 1);
    }

    public void sendDisconnect() {
        String bye = (username + ": :Disconnect");
        try {
            writer.println(bye);
            writer.flush();
        } catch (Exception e) {
            System.out.println("Could not send Disconnect message.\n");
//            panel2.addMessage("Could not send Disconnect message.\n");
        }
    }

    public void dissconnect() {
        try {
//            panel2.addMessage("Соединение отключено.\n");
            System.out.println("Соединение отключено.\n");
            sock.close();
            reader.close();
            writer.close();
        } catch(Exception ex) {
            System.out.println("Не удалось отклчиться. \n");
//            panel2.addMessage("Не удалось отклчиться. \n");
        }
        isConnected = false;
    }

    private String[][] getArray(String [][] array){
        return dataTable = array;
    }

    public void connectServer() {
        if (isConnected == false) {
            try {
                sock = new Socket(InetAddress.getLocalHost(), port);
                InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamReader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush();
//                writer.close();
                isConnected = true;
            } catch (Exception ex) {
                System.out.println("Cannot Connect! Try Again. \n");
            }
            StartThread();
        }
    }

    public void addMyEventListener(EventListenerObjectClient listener)
    {
        listenerList.add(EventListenerObjectClient.class, listener);
    }

    public void addEventListenerSetOpponentName(EventListenerSendString listener)
    {
        listenerList.add(EventListenerSendString.class, listener);
    }

    public void addEventListenerSendAnswerServerControlWord(EventListenerSendShot listener)
    {
        listenerList.add(EventListenerSendShot.class, listener);
    }

    public void addEventListenerStopGame(ListenerStopGame listener)
    {
        listenerList.add(ListenerStopGame.class, listener);
    }

    private void sendAnswer(EventObjectSendShot evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerSendShot.class) {
                ((EventListenerSendShot) listeners[i + 1]).sendCoordinateShotOrAnswerServer(evt);
            }
        }
    }

    private void sendWordStart(EventObjectSendShot evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerSendShot.class) {
                ((EventListenerSendShot) listeners[i + 1]).sendWordStart(evt);
            }
        }
    }

    private void setOpponentName(EventObjectSendString evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerSendString.class) {
                ((EventListenerSendString) listeners[i + 1]).sendMyNameOrMyOpponent(evt);
            }
        }
    }

    private void fireMyEvent(EventObjectClient evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerObjectClient.class) {
                ((EventListenerObjectClient) listeners[i + 1]).getDataUser(evt);
            }
        }
    }

    private void stopGame (StopGame evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ListenerStopGame.class) {
                ((ListenerStopGame) listeners[i + 1]).stopGameL(evt);
            }
        }
    }

    private void stringInArrayString(String test){
        System.out.println(test + " ТОЛЬКО ЗАШЕЛ В МЕТОД");
        if(test.equals("default")){
            dataTable = new String[1][1];
            dataTable[0][0] = test;
            System.out.println(dataTable[0][0] + " ПРИШЛА СТРОКА С ДЕФОЛТНЫМИ НАСТРОЙКАМИ");
        } else if(!test.equals("default")) {
            System.out.println("--------- " + test);
            String tests[] = test.split(",");
//            for (int i = 0; i < tests.length; i++) {
////            System.out.println(tests[i] + "   Это в методе рабиения строки на массив!!!");
//            }
            int count = 0;
            dataTable = new String[tests.length / 3][3];
            for (int i = 0; i < dataTable.length; i++) {
                for (int j = 0; j < dataTable[i].length; j++) {
                    dataTable[i][j] = tests[count];
                System.out.println(dataTable[i][j] + " ЭТО В МЕТОДЕ - stringInArrayString");
                    count += 1;
                }
//                count = 0;
            }
            count = 0;
            System.out.println(dataTable.length + " LLLLLLLL");
        }
    }

    private class IncomingReader implements Runnable
    {
        @Override
        public void run()
        {
            String[] data;
            String[][]dataUser = new String[10][10];
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat",commandServer = "array",
            answerServer = "answer";
            try
            {
                while ((stream = reader.readLine()) != null)
                {
                    System.out.println(stream);
                    data = stream.split(":");
                    data[1] = data[1].replace('+', ':');
                    System.out.println(data[0] + " = " + data[1] + " = " +  data[2] + "=");

                    if (data[2].equals(chat)) {
                        System.out.println("chat  ++++ " + data[0] + " " + data[1] );
                        if(data[1].substring(0, 4).equals("Name")) {
                            System.out.println(data[1].substring(14, data[1].length()) + "ЭТО НА КЛИЕНТЕ ПЕРЕД ОТПРАВКОЙ НА ПАНЕЛЬ ИМЕНИ ПРОТИВНИКА!!");
                            setOpponentName(new EventObjectSendString(data[1].substring(14, data[1].length())));
                        }

                        if(data[1].equals("start")){
                            System.out.println(data[1] + " ПЕРЕД ОТПРАВКИ СЛОВА СТАРТ");
                            sendWordStart(new EventObjectSendShot(data[1]));
                            System.out.println(data[1] + " ПОСЛЕ ОТПРАВКИ СЛОВА СТАРТ");
                        }
                    }
                    else if (data[2].equals(connect)) {
                        userAdd(data[0]);
                    }
                    else if (data[2].equals(disconnect)) {
                        System.out.println("Вы были отключены и вернуль в очередь!!");

                        stopGame(new StopGame(new Object(),false));
//                        userRemove(data[0]);
//                        sendDisconnect();
//                        dissconnect();
                    }else if (data[2].equals(done)) {
                        writeUsers();
                        users.clear();
                    }else if (data[2].equals(answerServer)){
                        sendAnswer(new EventObjectSendShot(data[1]));
                    }else if(data[2].equals(commandServer)){
                        System.out.println("Принял массив юзеров!!!");
                        stringInArrayString(data[1]);
                        System.out.println(dataTable.length + "RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
                        fireMyEvent(new EventObjectClient(dataTable));
//                        getArray(dataTable);
                    }
                }
            }catch(Exception ex) {
                sendDisconnect();
                dissconnect();
            }
        }
    }
}
