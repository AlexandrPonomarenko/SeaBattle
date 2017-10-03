package sea_battle_package;

import java.util.EventListener;

/**
 * Created by Alexandr on 27.09.2017.
 */
public interface EventListenerSendString extends EventListener {
    public void sendStringCreateOrConnect(EventObjectSendString eventObjectSendString);
    public void sendMyNameOrMyOpponent(EventObjectSendString eventObjectSendString);
}
