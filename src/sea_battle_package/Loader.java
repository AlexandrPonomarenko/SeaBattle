package sea_battle_package;

import javax.swing.*;
import java.awt.*;

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
    private boolean flag;


    private StartPanel sp;
    public Loader() {
        setSize(width,height);
        mainPanel = new JPanel();
        add(mainPanel,BorderLayout.CENTER);

//        panelButton = new PanelButton((((width / 100) * 60) / 100) * 10, getHeight());
//        panelSetShip = new PanelSetShip((width / 100) * 60, getHeight());

        setStartPanel();
        setPanelButtonAndPanelShip();
//        setFirstGamePanelAndSecondGamePanel();
    }

    private void setStartPanel() {
        sp = new StartPanel(width, height);
        mainPanel.add(sp);
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
//                        setFirstGamePanelAndSecond GamePanel();
//                        sgp.setShip(eventObject.getArray());
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

    }

    private void setConnectionPanelAndConnectionTablePanel(){
        clearMainPanel();
        setSizeFrame(width / 2, height / 2);
        cp = new ConectionPanel((((width / 100) * 60) / 100) * 10, getHeight());
        ctp = new ConnectionTablePanel(width - (((width / 100) * 60) / 100) * 10, getHeight());
        add(mainPanel,BorderLayout.CENTER);
//        mainPanel.setLayout(new BorderLayout());
//        mainPanel.add(cp, BorderLayout.NORTH);
//        mainPanel.add(ctp, BorderLayout.CENTER);

        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(cp, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL,
                new Insets(0,0,0,0), 0,0));
        mainPanel.add(ctp, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                new Insets(0,0,0,0), 0,0));
    }

    private void clearMainPanel(){
        mainPanel.removeAll();
        mainPanel.revalidate();
        repaint();
    }

    private void setSizeFrame(int w, int h){setSize(w,h);}

}
