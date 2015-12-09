package interfaces;


import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 * Functional interface for handling critical actions
 */
@FunctionalInterface
public interface CriticalAction<I> {

    /**Aquire lock
     * Executes an action
     * release lock
     *
     * @param semaphore
     * @param input
     */
    default void execute(Semaphore semaphore, I input){

        try {
            semaphore.acquire();
            theAction(input);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    /**
     * The criticalAction
     * @param input
     */
    void theAction(I input);

}
