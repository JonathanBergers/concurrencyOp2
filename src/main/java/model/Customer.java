package model;

import interfaces.Company;
import interfaces.Human;

import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 */
public class Customer extends Human {

    public Customer(int id, Company company) {
        super("CUST", id, company);
    }

    @Override
    protected void doSomething() {
        System.out.println(toString() +"living");
        try {
            //actionSemaphore.acquire();
            sleep(1000);

            System.out.println(toString() + "complaining");
            company.joinCustomerQueue(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
