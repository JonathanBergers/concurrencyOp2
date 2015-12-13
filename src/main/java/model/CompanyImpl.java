package model;

import interfaces.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 */
public class CompanyImpl implements Company{


    //list of customers who have a complain and are waiting for a conversation
    private Queue<Human> customerQueue = new LinkedList<>();
    //list of developers waiting who are ready for a conv and are waiting for a signal of the product owner
    private Queue<Human> developerQueue = new LinkedList<>();

    private final ProductOwner productOwner;




    private final Semaphore manipulateCustomerQueue, manipulateDeveloperQueue, manipulateProductOwner;

    public CompanyImpl(final int amountOfCustomers, final int amountOfDevelopers) {
        this.productOwner = new ProductOwner(1, this);
        manipulateCustomerQueue = new Semaphore(1);
        manipulateDeveloperQueue = new Semaphore(1);
        manipulateProductOwner = new Semaphore(1);


        new Thread(productOwner).start();
        // add some humans
        for (int i = 0; i < amountOfCustomers; i++) {
            new Customer(i, this).start();
        }
        for (int i = 0; i < amountOfDevelopers; i++) {
            new Developer(i, this).start();
        }

    }


    public void joinCustomerQueue(Human customer) {


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
                startConversation(productOwner);

            }else{
                developer.goBackToWork();
            }
        }).execute(manipulateProductOwner);
        // check if the po can have a conversation
        //((CriticalAction) () -> startConversation(productOwner)).execute(manipulateProductOwner);

    }






    public void startConversation(ProductOwner productOwner) {
        // critical action for productowner


        //System.out.println("CHECK FOR CONVERSATION");
        // critical action for getting dev size
        ((CriticalAction) () -> {

            int amountOfDevelopers = developerQueue.size();



            // critical action for getting cust size
            ((CriticalAction) () -> {
                int amountOfCustomers = customerQueue.size();


                // CUSTOMER CONVERSATION
                if(amountOfCustomers >0 && amountOfDevelopers >0){

                    System.out.println("PO: should start customer conv");
                    // new conversation
                    Conversation conversation = new Conversation(productOwner);


                    // tell all the customers to conversate and remove them from the queue
                    customerQueue.forEach(conversation::addHuman);
                    customerQueue.clear();

                    assert customerQueue.isEmpty();

                    // get the first developer and have a conversation
                    Human developer = developerQueue.poll();
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


                    System.out.println("PO: should start developer conversation");
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


                System.out.println("PO: no conversation needed, back to my room.");
                //No conversation possible
                //productOwner.fixRoom();



            }).execute(manipulateCustomerQueue);
            //start conversation


        }).execute(manipulateDeveloperQueue);





    }



}
