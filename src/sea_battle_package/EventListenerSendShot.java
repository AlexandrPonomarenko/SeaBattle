package sea_battle_package;

import java.util.EventListener;

/**
 * Created by Alexandr on 02.10.2017.
 */
public interface EventListenerSendShot extends EventListener {
    public void  sendCoordinateShotOrAnswerServer(EventObjectSendShot eventObjectSendShot);
    public void  sendWordStart(EventObjectSendShot eventObjectSendShot);
    public void  controlTimer(EventObjectSendShot eventObjectSendShot);
    public void  sendControlWord(EventObjectSendShot eventObjectSendShot);


}
