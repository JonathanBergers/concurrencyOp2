package company;

import model.CompanyImpl;
import org.junit.Test;

/**
 * Created by jonathan on 9-12-15.
 */
public class CompanyTest {


    public static void main(String[] args) {
        new CompanyImpl(5, 5);
    }


    /**Make a company with only 1 customer and 1 developer.
     * A customer conversation should start
     *
     */
    @Test
    private void testOnlyCustomerConv(){


        new CompanyImpl(5, 1);

    }


    @Test
    private void testOnlyDeveloper(){
        new CompanyImpl(0, 5);

    }


}
