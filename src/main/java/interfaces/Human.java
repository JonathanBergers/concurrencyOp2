package interfaces;

import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 */
public abstract class Human extends Thread{

    private final String name;
    private final int id;
    protected final Semaphore actionSemaphore = new Semaphore(1);
    protected final Company company;

    public Human(String name, int id, Company company) {
        this.name = name;
        this.id = id;
        this.company = company;
    }

    protected abstract void doSomething();



    public void conversate(Semaphore theConversation){
        try {

            System.out.println(toString() + "Conversating");
            theConversation.acquire();

            //pass, end conv
            theConversation.release();
            actionSemaphore.release();

            System.out.println(toString() + "Conversating done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            doSomething();
            while (true){


                actionSemaphore.acquire();
                sleep(100);

                doSomething();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return name+id + ": ";
    }
}
