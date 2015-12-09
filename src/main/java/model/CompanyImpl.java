package model;

import interfaces.Company;
import interfaces.Customer;
import interfaces.Developer;
import interfaces.ProductOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jonathan on 9-12-15.
 */
public class CompanyImpl implements Company{


    private List<Customer> customersWaiting = new ArrayList<Customer>();
    private List<Developer> developersReady = new ArrayList<Developer>();


    public void joinCustomerQueue(Customer customer) {

    }

    public void joinDeveloperQueue(Developer developer) {

    }

    public void leaveCustomerQueue(Customer customer) {

    }

    public void leaveDeveloperQueue(Developer developer) {

    }

    public void startConversation(ProductOwner productOwner) {

    }
}
