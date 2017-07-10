package sea_battle_package;

import java.util.EventListener;

/**
 * Created by Alexandr on 03.07.2017.
 */
public interface MyEventListener extends EventListener {
    public void clickButton(MyEventObject eventObject);
    public void getArray(MyEventObject eventObject);
    public void turnOn(MyEventObject eventObject);

}
