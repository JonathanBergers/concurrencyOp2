package model;

import interfaces.Company;
import interfaces.CriticalFunction;
import interfaces.ProductOwner;

import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 */
public class ProductOwnerImpl implements ProductOwner {

    private final Company company;
    private boolean available = true;

    public ProductOwnerImpl(Company company) {
        this.company = company;
    }

    @Override
    public void fixRoom() {
        try {
            System.out.println("Fixxing my room");
            Thread.sleep(200);

            // check if the po can have another conv
            //done with conversation

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void conversate(Semaphore semaphore) {


        try {
            available = false;
            Thread.sleep(5000);
            System.out.println("PO: done with conv");
            semaphore.release();

            available = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isAvailable() {
        return available;
    }




    @Override
    public void run() {

        while (true){
            fixRoom();
        }

    }
}
