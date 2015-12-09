package interfaces;

/**
 * Created by jonathan on 9-12-15.
 */
public interface Customer extends Human{

    /**Called when the customer enters a conversation
     *
     */
    void conversate();

    /**When the customer is not complaining or in a conversation
     *
     */
    void live();


    void waitABit();

    


}
