package model;

import interfaces.Company;
import interfaces.Customer;

/**
 * Created by jonathan on 9-12-15.
 */
public class CustomerImpl implements Customer {

    private final Company company;

    public CustomerImpl(Company company) {
        this.company = company;
    }


    @Override
    public void live() {

        try {
            System.out.println("imma complain about yo product jo");
            Thread.sleep(4);
            //make a complaint
            company.joinCustomerQueue(this);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void conversate(int duration) {
        System.out.println("Yo product sucks dude");
        try {
            Thread.sleep(duration);
            //done with conversation

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        while(true){

            live();

        }
    }
}
