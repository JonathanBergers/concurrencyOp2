package model;

import interfaces.*;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by jonathan on 9-12-15.
 */
public class CompanyImpl implements Company{


    //list of customers who have a complain and are waiting for a conversation
    private Queue<Customer> customerQueue = new LinkedList<>();
    //list of developers waiting who are ready for a conv and are waiting for a signal of the product owner
    private Queue<Developer> developerQueue = new LinkedList<>();

    private final ProductOwner productOwner;




    private final Semaphore manipulateCustomerQueue, manipulateDeveloperQueue, manipulateProductOwner;

    public CompanyImpl(final int amountOfCustomers, final int amountOfDevelopers) {
        this.productOwner = new ProductOwnerImpl(this);
        manipulateCustomerQueue = new Semaphore(1);
        manipulateDeveloperQueue = new Semaphore(1);
        manipulateProductOwner = new Semaphore(1);


        new Thread(productOwner).start();
        // add some humans
        for (int i = 0; i < amountOfCustomers; i++) {
            new Thread(new CustomerImpl(this)).start();
        }
        for (int i = 0; i < amountOfDevelopers; i++) {
            new Thread(new DeveloperImpl(this)).start();
        }

    }


    public void joinCustomerQueue(Customer customer) {


        // java 8, critical action interface
        // this is a method reference, see javadoc 8
        ((CriticalAction) () -> customerQueue.add(customer)).execute(manipulateCustomerQueue);

        // check if the po can have a conversation
        ((CriticalAction) () -> startConversation(productOwner)).execute(manipulateProductOwner);


    }

    public void joinDeveloperQueue(Developer developer) {



        ((CriticalAction) () -> {
            if(productOwner.isAvailable()){

                // add to queue
                ((CriticalAction) () -> developerQueue.add(developer)).execute(manipulateDeveloperQueue);
                //startConversation(productOwner);

            }else{
                developer.work();
            }
        }).execute(manipulateProductOwner);
        // check if the po can have a conversation
        ((CriticalAction) () -> startConversation(productOwner)).execute(manipulateProductOwner);

    }




    public void startConversation(ProductOwner productOwner) {
        // critical action for productowner


        System.out.println("CHECK FOR CONVERSATION");
        // critical action for getting dev size
        ((CriticalAction) () -> {

            int amountOfDevelopers = developerQueue.size();



            // critical action for getting cust size
            ((CriticalAction) () -> {
                int amountOfCustomers = customerQueue.size();


                // CUSTOMER CONVERSATION
                if(amountOfCustomers >0 && amountOfDevelopers >0){

                    System.out.println("SHOULD START A CUSTOMER CONV");
                    // new conversation
                    Conversation conversation = new Conversation(productOwner);


                    // tell all the customers to conversate and remove them from the queue
                    customerQueue.forEach(conversation::addHuman);
                    customerQueue.clear();

                    assert customerQueue.isEmpty();

                    // get the first developer and have a conversation
                    Developer developer = developerQueue.poll();
                    assert developer != null;
                    conversation.addHuman(developer);

                    // tell all the other developers to go back to work
                    // and remove them from the queue
                    developerQueue.forEach(conversation::addHuman);
                    developerQueue.clear();

                    assert developerQueue.isEmpty();

                    conversation.start();
                    return;

                }

                // DEVELOPER CONVERSATION
                if(amountOfCustomers == 0 && amountOfDevelopers >= 3){


                    Conversation conversation = new Conversation(productOwner);

                    // add developers
                    conversation.addHuman(developerQueue.poll());
                    conversation.addHuman(developerQueue.poll());
                    conversation.addHuman(developerQueue.poll());

                    // tell others to go work again
                    developerQueue.forEach(conversation::addHuman);
                    developerQueue.clear();

                    conversation.start();
                    return;

                }


                System.out.println("NO CONVERSATION NEEDED");
                //No conversation possible
                productOwner.fixRoom();



            }).execute(manipulateCustomerQueue);
            //start conversation


        }).execute(manipulateDeveloperQueue);





    }



    public void leaveCustomerQueue(Customer customer) {
        ((CriticalAction) () -> customerQueue.remove(customer)).execute(manipulateCustomerQueue);
    }

    public void leaveDeveloperQueue(Developer developer) {
        ((CriticalAction) () -> developerQueue.remove(developer)).execute(manipulateDeveloperQueue);
    }

}
