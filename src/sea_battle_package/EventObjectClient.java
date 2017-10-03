package sea_battle_package;

import java.util.EventObject;

/**
 * Created by Alexandr on 02.08.2017.
 */
public class EventObjectClient extends EventObject {

    private int [][] arrayShip;
    private String[][] arrayData;
    private String coordinatesShot;
    private String command;
    private String nameUser;
    private String nameCreatOrConnectUser;


    public  EventObjectClient(Object object, String SelectNameConnectUser) {
        super(object);
        nameUser = SelectNameConnectUser;
    }

    public  EventObjectClient(int [][] array) {
        super(new EventObject(new Object()));
        arrayShip = array;
    }

    public  EventObjectClient(String[][] array) {
        super(new EventObject(new Object()));
        arrayData = array;
    }

    public  EventObjectClient(String com) {
        super(new EventObject(new Object()));
        command = com;
    }

    public  EventObjectClient(Object name) {
        super(new EventObject(new Object()));
        nameCreatOrConnectUser = name.toString();
    }

    public  EventObjectClient() {
        super(new EventObject(new Object()));
    }

    public String [][] getDataUser(){
        return arrayData;
    }

    public String  getCoordinatesShot(){
        return coordinatesShot;
    }

    public int [][] getArrayShip(){
        return arrayShip;
    }

    public String getCommandConnection(){
        return command;
    }

    public String getNameTableUser(){
        return nameUser;
    }

    public String getNameCreatOrConnectUser(){
        return nameCreatOrConnectUser;
    }
}
