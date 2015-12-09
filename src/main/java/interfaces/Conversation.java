package interfaces;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by jonathan on 9-12-15.
 */
public class Conversation {

    final ArrayList<Human> humans = new ArrayList<>();
    final int duration;


    public void addHuman(Human human){
        humans.add(human);
    }
    public Conversation(int duration) {

        this.duration = duration;
    }

    public void start(){
        humans.forEach(human -> human.conversate(duration));
    }

}
