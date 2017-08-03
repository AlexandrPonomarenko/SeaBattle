package sea_battle_package;

import javax.swing.event.EventListenerList;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Scanner sc;
    private String massage;
    private String[][] dataTable;
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

    private void createStreamObject(){
        try {
            oos = new ObjectOutputStream(sock.getOutputStream());
            ois = new ObjectInputStream(sock.getInputStream());
        }catch(IOException e){
            System.out.println("Проблема с потоком обьектов");
            e.printStackTrace();
        }
    }

    public void writeUsers() {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token:tempList) {
            //users.append(token + "\n");
        }
    }

    private void sendMassage(String message) {
        try {
            writer.println(username + ":" + message + ":" + "Chat");
            writer.flush();
        }
        catch (Exception ex) {
            System.out.println("sendMassage" + "Message was not sent.");
        }
    }

    private void sendArrayShip(int [][] arr){
//                oos = new ObjectOutputStream(sock.getOutputStream());
        try {
            oos.writeObject(arr);
            oos.flush();
        } catch (Exception ex) {
            System.out.println("НЕ УДАЛОСЬ ОТПРАВИТЬ МАССИВ");
        }
    }



    public void userRemove(String data) {
//        panel2.addMessage(data + " is now offline.\n");
        System.out.println(data + " is now offline.\n");
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
                isConnected = true;
            } catch (Exception ex) {
                System.out.println("Cannot Connect! Try Again. \n");
//                panel2.addMessage("Cannot Connect! Try Again. \n");
            }

            StartThread();
            createStreamObject();
        }
    }

    public void addMyEventListener(EventListenerObjectClient listener)
    {
        listenerList.add(EventListenerObjectClient.class, listener);
    }

    private void fireMyEvent(EventObjectClient evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == EventListenerObjectClient.class) {
                ((EventListenerObjectClient) listeners[i + 1]).getDataUser(evt);
            }
        }
    }

    private class IncomingReader implements Runnable
    {
        @Override
        public void run()
        {
            String[] data;
            String[][]dataUser = new String[10][10];
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            try
            {
                while ((stream = reader.readLine()) != null || ((dataUser = (String[][]) ois.readObject()) != null))
                {
                    data = stream.split(":");

                    if (data[2].equals(chat))
                    {
//                        panel2.addMessage("" + sdf.format(date), data[0], data[1]);
                        System.out.println("chat  ++++ " + data[0] + " " + data[1] );
                    }
                    else if (data[2].equals(connect))
                    {
                        userAdd(data[0]);
                    }
                    else if (data[2].equals(disconnect))
                    {
                        System.out.println("Вы были отключены и вернуль в очередь!!");
//                        userRemove(data[0]);
//                        sendDisconnect();
//                        dissconnect();
                    }
                    else if (data[2].equals(done))
                    {
                        writeUsers();
                        users.clear();
                    }else if((ois.readObject()) != null){
                        getArray(dataUser);
                    }
                }
            }catch(Exception ex) {
                sendDisconnect();
                dissconnect();
            }
        }
    }
}
