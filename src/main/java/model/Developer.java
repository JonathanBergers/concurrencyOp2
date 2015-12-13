package model;

import interfaces.Company;
import interfaces.Human;

/**
 * Created by jonathan on 9-12-15.
 */
public class Developer extends Human {


    public Developer(int id, Company company) {
        super("DEV", id, company);
    }

    @Override
    protected void doSomething() {

        System.out.println(toString()+ "Working");

        try {
            sleep(1000);
            //actionSemaphore.acquire();
            System.out.println(toString() + "check for conv");
            company.joinDeveloperQueue(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void goBackToWork(){
        System.out.println(toString() + "not taking part of conv");
        actionSemaphore.release();
    }
}
