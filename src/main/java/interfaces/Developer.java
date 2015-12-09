package interfaces;

/**
 * Created by jonathan on 9-12-15.
 */
public interface Developer extends Human {

    /**When the developer is working
     *
     * Called after Company.developerQueue if the po is not available OR indicates that the developer is too late to conversate
     *
     *
     */
    void work();


    /**When the PO starts a conversation
     *
     */
    void conversate();

}
