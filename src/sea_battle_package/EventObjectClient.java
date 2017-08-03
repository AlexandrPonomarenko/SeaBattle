package sea_battle_package;

import java.util.EventObject;

/**
 * Created by Alexandr on 02.08.2017.
 */
public class EventObjectClient extends EventObject {

    private int [][] arrayShip;
    private String[][] arrayData;
    private String coordinatesShot;

    public  EventObjectClient(Object object, String coordinatesShot) {
        super(object);
    }

    public  EventObjectClient(int [][] array) {
        super(new EventObject(new Object()));
        arrayShip = array;
    }

    public  EventObjectClient(String[][] array) {
        super(new EventObject(new Object()));
        arrayData = array;
    }

    public  EventObjectClient() {
        super(new EventObject(new Object()));
    }

//    private
}
