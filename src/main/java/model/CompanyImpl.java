package model;

import interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 */
public class CompanyImpl implements Company{


    //list of customers who have a complain and are waiting for a conversation
    private ArrayList<Customer> customerQueue = new ArrayList<Customer>();
    //list of developers waiting who are ready for a conv and are waiting for a signal of the product owner
    private List<Developer> developerQueue = new ArrayList<Developer>();

    private final Semaphore manipulateCustomerQueue, manipulateDeveloperQueue;

    public CompanyImpl() {
        manipulateCustomerQueue = new Semaphore(-1);
        manipulateDeveloperQueue = new Semaphore(-1);

    }


    public void joinCustomerQueue(Customer customer) {
        // java 8, critical action interface
        // this is a method reference, see javadoc 8
        ((CriticalAction<Customer>) customerQueue::add).execute(manipulateCustomerQueue, customer);
    }

    public void joinDeveloperQueue(Developer developer) {
        ((CriticalAction<Developer>) developerQueue::add).execute(manipulateDeveloperQueue, developer);
    }

    public void leaveCustomerQueue(Customer customer) {
        ((CriticalAction<Customer>) customerQueue::remove).execute(manipulateCustomerQueue, customer);
    }

    public void leaveDeveloperQueue(Developer developer) {
        ((CriticalAction<Developer>) developerQueue::remove).execute(manipulateDeveloperQueue, developer);
    }

    public void startConversation(ProductOwner productOwner) {

    }
}
