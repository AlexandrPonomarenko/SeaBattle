package sea_battle_package;

import java.util.EventObject;

/**
 * Created by Alexandr on 23.10.2017.
 */
public class StopGame extends EventObject {
    private boolean stop = true;

    public StopGame(Object o, boolean s){
        super(o);
        stop = s;
    }

    public boolean getStopGame(){
        return stop;
    }
}
