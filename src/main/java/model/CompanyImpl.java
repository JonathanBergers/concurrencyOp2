package model;

import interfaces.*;

import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 */
public class CompanyImpl implements Company{


    //list of customers who have a complain and are waiting for a conversation
    private PriorityQueue<Customer> customerQueue = new PriorityQueue<>();
    //list of developers waiting who are ready for a conv and are waiting for a signal of the product owner
    private PriorityQueue<Developer> developerQueue = new PriorityQueue<>();

    private final ProductOwner productOwner;

    private final Semaphore manipulateCustomerQueue, manipulateDeveloperQueue, manipulateProductOwner;

    public CompanyImpl() {
        this.productOwner = new ProductOwnerImpl(this);
        manipulateCustomerQueue = new Semaphore(-1);
        manipulateDeveloperQueue = new Semaphore(-1);
        manipulateProductOwner = new Semaphore(-1);


        new Thread(productOwner).start();
        // add some humans
        for (int i = 0; i < 10; i++) {

            new Thread(new CustomerImpl(this)).start();
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

                // check if po can start a conversation
                startConversation(productOwner);
            }else{
                developer.work();
            }
        }).execute(manipulateProductOwner);



    }




    public void startConversation(ProductOwner productOwner) {
        // critical action for productowner


        // critical action for getting dev size
        ((CriticalAction) () -> {

            int amountOfDevelopers = developerQueue.size();


            // critical action for getting cust size
            ((CriticalAction) () -> {
                int amountOfCustomers = customerQueue.size();


                // CUSTOMER CONVERSATION
                if(amountOfCustomers >0 && amountOfDevelopers >0){

                    // new conversation
                    Conversation conversation = new Conversation(1000);
                    conversation.addHuman(productOwner);


                    // tell all the customers to conversate and remove them from the queue
                    customerQueue.forEach(customer -> {
                        conversation.addHuman(customer);
                        customerQueue.remove(customer);
                    });

                    assert customerQueue.isEmpty();

                    // get the first developer and have a conversation
                    Developer developer = developerQueue.poll();
                    assert developer != null;
                    conversation.addHuman(developer);

                    // tell all the other developers to go back to work
                    // and remove them from the queue
                    developerQueue.forEach(developer1 -> {
                        developer1.work();
                        developerQueue.remove(developer1);
                    });
                    assert developerQueue.isEmpty();

                    conversation.start();
                    return;

                }

                // DEVELOPER CONVERSATION
                if(amountOfCustomers == 0 && amountOfDevelopers >= 3){


                    Conversation conversation = new Conversation(3000);

                    conversation.addHuman(productOwner);
                    // add developers
                    conversation.addHuman(developerQueue.poll());
                    conversation.addHuman(developerQueue.poll());
                    conversation.addHuman(developerQueue.poll());

                    // tell others to go work again
                    developerQueue.forEach(developer1 -> {
                        developer1.work();
                        developerQueue.remove(developer1);
                    });

                    conversation.start();
                    return;

                }

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
