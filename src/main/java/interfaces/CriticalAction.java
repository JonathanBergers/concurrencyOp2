package interfaces;

import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 * Simple critical Action withuout input
 */
@FunctionalInterface
public interface CriticalAction {


    /**Aquire lock
     * Executes an action
     * release lock
     *
     * @param semaphore
     */
    default void execute(Semaphore semaphore){

        try {
            semaphore.acquire();
            theAction();
            semaphore.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    /**
     * The criticalAction
     */
    void theAction();
}
