package sea_battle_package;

import java.util.EventObject;

/**
 * Created by Alexandr on 03.07.2017.
 */
public class MyEventObject extends EventObject {
    private String state;
    private int [][] array;
    private int stateButton;
    private boolean mainFlag = true;

    public  MyEventObject(Object object, String stateButton) {
        super(object);
        state = stateButton;
    }

    public  MyEventObject(int [][] array) {
        super(new EventObject(new Object()));
        this.array = array;
    }

    public  MyEventObject(int stateButton) {
        super(new EventObject(new Object()));
        this.stateButton = stateButton;
    }

    public  MyEventObject(boolean mainFlag) {
        super(new EventObject(new Object()));
        this.mainFlag = mainFlag;
    }

    public  MyEventObject() {
        super(new EventObject(new Object()));
    }

    public String getState() {
        return state;
    }

    public int [][] getArray() {
        return array;
    }

    public int getStateButton() {
        return stateButton;
    }

    public boolean getMainFlag(){return mainFlag;}
}
