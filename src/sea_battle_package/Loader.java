package sea_battle_package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Alexandr on 01.07.2017.
 */
public class Loader extends JFrame{

    private int width = 1000;
    private int height = 500;
    private JPanel mainPanel;
    private PanelButton panelButton;
    private PanelSetShip panelSetShip;
    private FirstGamePanel fgp;
    private SecondGamePanel sgp;
    private ConectionPanel cp;
    private ConnectionTablePanel ctp;
    private Client client;
    private EndPanel ep;
    private boolean flag;
    private StartPanel sp;

    public Loader() {
        setSize(width,height);
        mainPanel = new JPanel();
        add(mainPanel,BorderLayout.CENTER);

        setStartPanel();
        setPanelButtonAndPanelShip();
        client = new Client();
        addWindowClosing();
    }

    private void setStartPanel() {
        sp = new StartPanel(width, height);
        mainPanel.add(sp);
    }

    private void addWindowClosing(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(sgp.equals(null)){
                    System.out.println("wwwwwwwwwwwwwwwwwwwwww");
                    System.exit(1);
                }else if(!sgp.equals(null));{
                    client.sendDisconnect();

                }
            }
        });
    }
    private void setPanelButtonAndPanelShip(){
        sp.addMyEventListener(new EventListenerLoadPanel() {
            @Override
            public void click(LoadEventListenerPanel loadEventListenerPanel) {
                clearMainPanel();
                setSizeFrame((width / 100) * 60, height);
                panelButton = new PanelButton((((width / 100) * 60) / 100) * 10, getHeight());
                panelSetShip = new PanelSetShip((width / 100) * 60, getHeight());

                mainPanel.setLayout(new GridBagLayout());

                mainPanel.add(panelButton, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL,
                        new Insets(0,0,0,0), 0,0));

                mainPanel.add(panelSetShip, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                        new Insets(0,0,0,0), 0,0));

                panelButton.addMyEventListener(new MyEventListener() {
                    @Override
                    public void clickButton(MyEventObject eventObject) {
                        panelSetShip.setButtonState(eventObject.getState());
                    }

                    @Override
                    public void getArray(MyEventObject eventObject) {

                    }

                    @Override
                    public void turnOn(MyEventObject eventObject) {

                    }
                });

                panelSetShip.addMyEventListener(new MyEventListener() {
                    @Override
                    public void clickButton(MyEventObject eventObject) {

                    }

                    @Override
                    public void getArray(MyEventObject eventObject) {
                        setConnectionPanelAndConnectionTablePanel();
                        cp.setShip(eventObject.getArray());
                        setListener();
                        setListenerCP();
                        setListenerClient();
                        setListenerCTP();
                    }

                    @Override
                    public void turnOn(MyEventObject eventObject) {

                    }

                });

                panelSetShip.addMyEventListener(new MyEventListener() {
                    @Override
                    public void clickButton(MyEventObject eventObject) {
                        panelButton.turnOffButton(eventObject.getStateButton());
                    }

                    @Override
                    public void getArray(MyEventObject eventObject) {

                    }

                    @Override
                    public void turnOn(MyEventObject eventObject) {
                        panelButton.allTurnOn();
                    }
                });

                panelSetShip.addEventListenerObjectClient(new EventListenerObjectClient() {
                    @Override
                    public void sendArrayCoordinatesShips(EventObjectClient eventObjectClient) {

                    }

                    @Override
                    public void getDataUser(EventObjectClient eventObjectClient) {

                    }

                    @Override
                    public void sendCoordinatesShot(EventObjectClient eventObjectClient) {

                    }

                    @Override
                    public void startClient(EventObjectClient eventObjectClient) {
                        client.connectServer();
                    }

                    @Override
                    public  void sendCommandConnection(EventObjectClient eventObjectClient){}

                    @Override
                    public  void changeNameUser(EventObjectClient eventObjectClient){}

                    @Override
                    public  void sendNameUser(EventObjectClient eventObjectClient){}
                });
            }
        });
    }

    private void setListener(){
        cp.addMyEventListener(new MyEventListener() {
            @Override
            public void clickButton(MyEventObject eventObject) {
                setFirstGamePanelAndSecondGamePanel();
            }

            @Override
            public void getArray(MyEventObject eventObject) {
                sgp.setShip(eventObject.getArray());
            }

            @Override
            public void turnOn(MyEventObject eventObject) {

            }
        });

        cp.addEventListenerObjectClient(new EventListenerObjectClient() {
            @Override
            public void sendArrayCoordinatesShips(EventObjectClient eventObjectClient) {
                client.sendArrayShip(eventObjectClient.getArrayShip());
            }

            @Override
            public void getDataUser(EventObjectClient eventObjectClient) {

            }

            @Override
            public void sendCoordinatesShot(EventObjectClient eventObjectClient) {
//                client.sendMassage(eventObjectClient.getCommandConnection(),eventObjectClient.getCommandConnection());
            }

            @Override
            public void startClient(EventObjectClient eventObjectClient) {

            }


            @Override
            public  void sendNameUser(EventObjectClient eventObjectClient){
                client.setNameUser(eventObjectClient.getNameCreatOrConnectUser());
            }

            @Override
            public void sendCommandConnection(EventObjectClient eventObjectClient) {
                client.setNameUserFromTableToConnect(eventObjectClient.getNameTableUser());
                client.sendMassageCommand(eventObjectClient.getNameCreatOrConnectUser(), eventObjectClient.getCommandConnection());
            }

            @Override
            public  void changeNameUser(EventObjectClient eventObjectClient){}
        });
    }

    private void setListenerCP(){
        cp.addEventListenerObjectSendString(new EventListenerSendString() {
            @Override
            public void sendStringCreateOrConnect(EventObjectSendString eventObjectSendString) {
                sgp.setFlagWord(eventObjectSendString.getSendFlagWord());
            }

            @Override
            public void sendMyNameOrMyOpponent(EventObjectSendString eventObjectSendString) {
                fgp.setMyName(eventObjectSendString.getSendMyName());
            }
        });
    }

    private void setListenerClient(){
        client.addMyEventListener(new EventListenerObjectClient() {
            @Override
            public void sendArrayCoordinatesShips(EventObjectClient eventObjectClient) {

            }

            @Override
            public void getDataUser(EventObjectClient eventObjectClient) {
                ctp.setDataUser(eventObjectClient.getDataUser());
            }

            @Override
            public void sendCoordinatesShot(EventObjectClient eventObjectClient) {

            }

            @Override
            public void startClient(EventObjectClient eventObjectClient) {

            }

            @Override
            public  void sendCommandConnection(EventObjectClient eventObjectClient){}

            @Override
            public  void changeNameUser(EventObjectClient eventObjectClient){}

            @Override
            public  void sendNameUser(EventObjectClient eventObjectClient){}
        });

        client.addEventListenerSetOpponentName(new EventListenerSendString() {
            @Override
            public void sendStringCreateOrConnect(EventObjectSendString eventObjectSendString) {

            }

            @Override
            public void sendMyNameOrMyOpponent(EventObjectSendString eventObjectSendString) {
                fgp.setNameMyOpponent(eventObjectSendString.getSendMyName());
            }
        });

        client.addEventListenerSendAnswerServerControlWord(new EventListenerSendShot() {
            @Override
            public void sendCoordinateShotOrAnswerServer(EventObjectSendShot eventObjectSendShot) {
                sgp.setControlMoveAnswerFromServer(eventObjectSendShot.getAnswerServer());
            }

            @Override
            public void sendWordStart(EventObjectSendShot eventObjectSendShot) {
                sgp.setWordStart(eventObjectSendShot.getWordStartGame());
            }

            @Override
            public void controlTimer(EventObjectSendShot eventObjectSendShot) {

            }

            @Override
            public void sendControlWord(EventObjectSendShot eventObjectSendShot) {

            }
        });

        client.addEventListenerStopGame(new ListenerStopGame() {
            @Override
            public void stopGameL(StopGame stopGame) {
                sgp.setStatusGame(stopGame.getStopGame());
            }
        });
    }

    private void setListenerCTP(){
        ctp.addEventListenerObjectClient(new EventListenerObjectClient() {
            @Override
            public void sendArrayCoordinatesShips(EventObjectClient eventObjectClient) {

            }

            @Override
            public void getDataUser(EventObjectClient eventObjectClient) {

            }

            @Override
            public void sendCoordinatesShot(EventObjectClient eventObjectClient) {

            }

            @Override
            public void startClient(EventObjectClient eventObjectClient) {

            }

            @Override
            public void sendCommandConnection(EventObjectClient eventObjectClient) {

            }

            @Override
            public void changeNameUser(EventObjectClient eventObjectClient) {
                cp.setNameTableUser(eventObjectClient.getNameTableUser());
            }

            @Override
            public  void sendNameUser(EventObjectClient eventObjectClient){
            }
        });
    }

    private void setListenerSecondGamePanel(){
        sgp.addEventListenerSendAnswerServerControlWord(new EventListenerSendShot() {
            @Override
            public void sendCoordinateShotOrAnswerServer(EventObjectSendShot eventObjectSendShot) {
                client.sendCoordinatesShot(eventObjectSendShot.getCordShot());
            }

            @Override
            public void sendWordStart(EventObjectSendShot eventObjectSendShot) {

            }

            @Override
            public void controlTimer(EventObjectSendShot eventObjectSendShot) {
                fgp.setControlTimer(eventObjectSendShot.getControlTimer());
            }

            @Override
            public void sendControlWord(EventObjectSendShot eventObjectSendShot) {
                setEndPanel();
                ep.setWord(eventObjectSendShot.getWord());
                setListenerEndPanel();
            }
        });
    }

    private void setListenerFirstGamePanel(){
        fgp.addEventListenerLoseYourMove(new EventListenerSendShot() {
            @Override
            public void sendCoordinateShotOrAnswerServer(EventObjectSendShot eventObjectSendShot) {
            }

            @Override
            public void sendWordStart(EventObjectSendShot eventObjectSendShot) {

            }

            @Override
            public void controlTimer(EventObjectSendShot eventObjectSendShot) {
                sgp.setLoseMove(eventObjectSendShot.getLoseMove());
            }

            @Override
            public void sendControlWord(EventObjectSendShot eventObjectSendShot) {

            }
        });
    }

    private void setListenerEndPanel(){
        ep.addEventListenerAgainGame(new AgainGame() {
            @Override
            public void againGame(AgainGameCl againGameCl) {
                clearMainPanel();
                setStartPanel();
            }
        });
    }
    private void setFirstGamePanelAndSecondGamePanel(){
        clearMainPanel();
        setSizeFrame(width, height);
        fgp = new FirstGamePanel(width, height / 100 * 20);
        sgp = new SecondGamePanel(width, height / 100 * 80);
        add(mainPanel,BorderLayout.CENTER);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(fgp, BorderLayout.NORTH);
        mainPanel.add(sgp, BorderLayout.CENTER);
        setListenerFirstGamePanel();
        setListenerSecondGamePanel();

    }

    private void setConnectionPanelAndConnectionTablePanel(){
        clearMainPanel();
        setSizeFrame(width / 2, height / 2);
        cp = new ConectionPanel((((width / 100) * 60) / 100) * 10, getHeight());
        ctp = new ConnectionTablePanel(width - (((width / 100) * 60) / 100) * 10, getHeight());
        add(mainPanel,BorderLayout.CENTER);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(cp, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL,
                new Insets(0,0,0,0), 0,0));
        mainPanel.add(ctp, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0,0,0,0), 0,0));
    }

    private void setEndPanel(){
        clearMainPanel();
        setSizeFrame(width, height);
        ep = new EndPanel(width, height);
        add(mainPanel,BorderLayout.CENTER);
        mainPanel.add(ep);
    }
    private void clearMainPanel(){
        mainPanel.removeAll();
        mainPanel.revalidate();
        repaint();
    }

    private void setSizeFrame(int w, int h){setSize(w,h);}
}
