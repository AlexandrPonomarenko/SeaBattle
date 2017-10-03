package sea_battle_package;

import java.util.EventObject;

/**
 * Created by Alexandr on 02.10.2017.
 */
public class EventObjectSendShot extends EventObject {
    private String cordShot;
    private String answerServer;
    private String start;
    private int controlTimer;
    private int loseMove;

    public EventObjectSendShot(Object object, String corShot){
        super(object);
        cordShot = corShot;
    }

    public EventObjectSendShot(String answer){
        super(new EventObject(new Object()));
        answerServer = answer;
        start = answer;
    }

    public EventObjectSendShot(int controlTimer){
        super(new EventObject(new Object()));
        this.controlTimer = controlTimer;
        loseMove = controlTimer;
    }

    public String getAnswerServer(){
        return answerServer;
    }

    public String getCordShot(){
        return  cordShot;
    }

    public String getWordStartGame(){
        return start;
    }

    public int getControlTimer(){
        return controlTimer;
    }

    public int getLoseMove(){
        return loseMove;
    }


}