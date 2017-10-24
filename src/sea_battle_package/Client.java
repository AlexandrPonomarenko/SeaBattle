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
    private int port = 9000;
    private Socket sock;
    private BufferedReader reader;
    private PrintWriter writer;
    private String massage;
    private String[][] dataTable;
    private String nameUser;
    private String nameUserFromTableToConnect;
    private EventListenerList eventListenerList;
    private EventListenerList listenerList;

    public Client(){
        listenerList = new EventListenerList();
    }

    private void StartThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
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
        if(coordinatesShot.equals("bye")){
            sendDisconnect();
            dissconnect();
            return;
        }
        if(!coordinatesShot.equals("clear")){
            try {
                writer.println(username + ":" + coordinatesShot + ":" + "shot");
                writer.flush();
            } catch (Exception ex) {
                System.out.println("НЕ УДАЛОСЬ ОТПРАВИТЬ КООРДИНАТЫ");
            }
        }else {
            System.out.println(coordinatesShot + "ПРИШЕЛ CLEAR");
        }
    }

    public void setNameUser(String nameUser){
        this.nameUser = nameUser;
    }

    public void setNameUserFromTableToConnect(String nameUser){
        nameUserFromTableToConnect = nameUser;
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
        }
    }

    public void dissconnect() {
        try {
            System.out.println("Соединение отключено.\n");
            sock.close();
            reader.close();
            writer.close();
        } catch(Exception ex) {
            System.out.println("Не удалось отклчиться. \n");
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
        if(test.equals("default")){
            dataTable = new String[1][1];
            dataTable[0][0] = test;
        } else if(!test.equals("default")) {
            String tests[] = test.split(",");
            int count = 0;
            dataTable = new String[tests.length / 3][3];
            for (int i = 0; i < dataTable.length; i++) {
                for (int j = 0; j < dataTable[i].length; j++) {
                    dataTable[i][j] = tests[count];
                    count += 1;
                }
            }
            count = 0;
        }
    }

    private class IncomingReader implements Runnable
    {
        @Override
        public void run()
        {
            String[] data;
            String stream, connect = "Connect", disconnect = "Disconnect", chat = "Chat",commandServer = "array",
            answerServer = "answer";
            try
            {
                while ((stream = reader.readLine()) != null)
                {
                    data = stream.split(":");
                    data[1] = data[1].replace('+', ':');

                    if (data[2].equals(chat)) {
                        if(data[1].substring(0, 4).equals("Name")) {
                            setOpponentName(new EventObjectSendString(data[1].substring(14, data[1].length())));
                        }

                        if(data[1].equals("start")){
                            sendWordStart(new EventObjectSendShot(data[1]));
                        }
                    }else if (data[2].equals(disconnect)) {
                        stopGame(new StopGame(new Object(),false));
                    }else if (data[2].equals(answerServer)){
                        sendAnswer(new EventObjectSendShot(data[1]));
                    }else if(data[2].equals(commandServer)){
                        stringInArrayString(data[1]);
                        fireMyEvent(new EventObjectClient(dataTable));
                    }
                }
            }catch(Exception ex) {
                sendDisconnect();
                dissconnect();
            }
        }
    }
}
