package sea_battle_package;

import java.util.EventObject;

/**
 * Created by Alexandr on 27.09.2017.
 */
public class EventObjectSendString extends EventObject {

    private String sendFlagWord;
    private String sendMyName;

    public  EventObjectSendString(Object object, String flagWord) {
        super(object);
        sendFlagWord = flagWord;
    }

    public  EventObjectSendString(String sendMyName) {
        super(new EventObject(new Object()));
        this.sendMyName = sendMyName;
    }

    public String getSendFlagWord(){
        return sendFlagWord;
    }
    public String getSendMyName(){
        return sendMyName;
    }
}
