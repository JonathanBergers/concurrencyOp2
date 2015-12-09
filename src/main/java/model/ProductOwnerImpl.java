package model;

import interfaces.Company;
import interfaces.ProductOwner;

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
            Thread.sleep(50);
            //done with conversation

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void conversate(int duration) {
        try {
            available = false;
            System.out.println("Product owner talk");
            Thread.sleep(duration);
            available = true;
            //done with conversation

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
