package interfaces;

import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 */
public interface Human extends Runnable{

    default void conversate(Semaphore theConversation){
        try {

            System.out.println("Conversating");
            // does semaphore with - amount of customers work too, or do they all need to release first

            theConversation.acquire();
            //do nothing, hacky
            theConversation.release();

            System.out.println("Conversating done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
