package interfaces;

/**
 * Created by jonathan on 9-12-15.
 */
public interface Company {


    /**called when a user has a complain
     * and is invited by the product owner to come over for a conversation
     *
     * @param customer
     */
    void joinCustomerQueue(Customer customer);

    /**Called when a developer becomes available for a conversation with the product owner
     * and 3 other developers OR alone with another customer
     *
     * @param developer
     */
    void joinDeveloperQueue(Developer developer);

    /**Called when a user is done having a conversation with the product owner and a developer
     *
     * @param customer
     */
    void leaveCustomerQueue(Customer customer);

    /**Called when the product owner is not available
     * OR when the PO IS available but the developer is not invited for a conversation
     * OR when the developer is done having a conversation
     *
     * @param developer
     */
    void leaveDeveloperQueue(Developer developer);


    /**Called when the product owner is available for a conversation
     * And when customers waiting size > 0 && 1 developer is available
     *
     * OR
     * when 0 customer is waiting and 3 developers are ready for a conv.
     *
     * @param productOwner
     */
    void startConversation(ProductOwner productOwner);





}
