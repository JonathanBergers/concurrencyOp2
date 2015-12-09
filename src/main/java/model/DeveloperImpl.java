package model;

import interfaces.Company;
import interfaces.Developer;

/**
 * Created by jonathan on 9-12-15.
 */
public class DeveloperImpl implements Developer {

    private final Company company;

    public DeveloperImpl(Company company) {
        this.company = company;
    }

    @Override
    public void work() {


        try {
            System.out.println("Im working my ass of");
            Thread.sleep(4);
            System.out.println("lets see if i can have a conversation");
            company.joinDeveloperQueue(this);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void conversate(int duration) {
        System.out.println("Special price special price");
        try {
            Thread.sleep(duration);
            //done with conversation

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        work();

    }
}
