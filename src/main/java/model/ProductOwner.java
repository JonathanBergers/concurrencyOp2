package model;

import interfaces.Company;
import interfaces.Human;

import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 */
public class ProductOwner extends Human{

    private boolean available = true;

    public ProductOwner(int id, Company company) {
        super("PO", id, company);
    }


    @Override
    protected void doSomething() {

        System.out.println(toString()+ "fixing room");




    }

    @Override
    public void conversate(Semaphore semaphore) {
        try {
            available = false;
            Thread.sleep(5000);

            semaphore.release();
            System.out.println("PO: done with conv");
            available = true;

            // release fixxing room so po can fix his room again
            actionSemaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean isAvailable() {
        return available;
    }





}
