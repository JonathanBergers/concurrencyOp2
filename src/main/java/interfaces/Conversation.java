package interfaces;

import model.ProductOwner;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by jonathan on 9-12-15.
 */
public class Conversation {

    final ArrayList<Human> participants = new ArrayList<>();
    final ProductOwner productOwner;







    public void addHuman(Human human){
        participants.add(human);
    }

    public Conversation(ProductOwner productOwner) {
        this.productOwner = productOwner;
    }

    public void start(){
        System.out.println("CONV: starting conversation with " + participants.size() + " participants" );

        Semaphore conversation = new Semaphore(1);
        // every human tries to aquire the semaphore but it is only released when the product owner is done talking.
        // so now every human of the conversation is done having a conversation after the product owner is.

        try {
            conversation.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // add the product owner at the end of the list, so the conv end when the po is done
        addHuman(productOwner);
        participants.parallelStream().forEach(human -> human.conversate(conversation));




        // end conversation
        // every human now passess the semaphore and coninues to live
        conversation.release();
        System.out.println("CONV: conversation is over");

    }

}
