package serverPack;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Alexandr on 24.10.2017.
 */
public class Server implements Runnable {
    public ArrayList<PrintWriter> clientConnections;
    public static ArrayList<String> users;
    public static ArrayList<ServerLogic.User> arrayUser;

    public Server()
    {
        arrayUser = new ArrayList<>();
        run();
    }


    @Override
    public void run() {

        clientConnections = new ArrayList();
        users = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                Thread listener = new Thread(new ServerLogic(clientSocket, writer, clientConnections));
                clientConnections.add(writer);
                listener.start();
                System.out.println("Con a connectoion \n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error making a connection \n");
        }
    }
}
