package sea_battle_package;

import java.util.EventListener;

/**
 * Created by Alexandr on 02.08.2017.
 */
public interface EventListenerObjectClient extends EventListener {
    public void sendArrayCoordinatesShips(EventObjectClient eventObjectClient);
    public void getDataUser(EventObjectClient eventObjectClient);
    public void sendCoordinatesShot(EventObjectClient eventObjectClient);
    public void startClient(EventObjectClient eventObjectClient);
    public void sendCommandConnection(EventObjectClient eventObjectClient);
}
