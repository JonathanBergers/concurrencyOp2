package interfaces;

/**
 * Created by jonathan on 9-12-15.
 */
public interface Company {


    void joinCustomerQueue(Customer customer);

    void joinDeveloperQueue(Developer developer);

    void leaveCustomerQueue(Customer customer);

    void leaveDeveloperQueue(Developer developer);

    void startConversation(ProductOwner productOwner);





}
