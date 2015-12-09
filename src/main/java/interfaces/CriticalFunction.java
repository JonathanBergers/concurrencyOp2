package interfaces;


import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 * Functional interface for handling critical actions
 * has input and return type
 */
@FunctionalInterface
public interface CriticalFunction<I, R> {

    /**Aquire lock
     * Executes an action
     * release lock
     *
     * returns result
     * @param semaphore
     * @param input
     */
    default R execute(Semaphore semaphore, I input){



        try {
            semaphore.acquire();
            R result = theAction(input);
            semaphore.release();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * The criticalAction
     * @param input
     */
    R theAction(I input);

}
