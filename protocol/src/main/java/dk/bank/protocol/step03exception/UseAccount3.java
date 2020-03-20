package dk.bank.protocol.step03exception;


import java.util.Collections;
import java.util.List;

/**
 * Dealing with methods that can return data or a problem
 * <p>
 * Good video - one hour: https://www.youtube.com/watch?v=vePeILeSv4E
 * <p>
 * See: https://medium.com/@afcastano/monads-for-java-developers-part-2-the-result-and-log-monads-a9ecc0f231bb
 */
public class UseAccount3 {

    /**
     * Sample of return no account or an account
     */
    interface Protocol {
        /**
         * Getting the customers main account - not all customers got a main account
         *
         * @param customer customer
         * @return result - Results signal an error
         */
        Result<List<Account>> getCustomerMainAccount(String customer);

    }

    final Protocol api;

    /**
     * Assemble the application
     */
    public UseAccount3() {
        this.api = new Implementation();
    }


    /**
     * Implement the optional response.
     */
    static class Implementation implements Protocol {

        @Override
        public Result<List<Account>> getCustomerMainAccount(String customer) {
            if (customer == null) {
                return Result.error(new IllegalArgumentException("Customer cannot be missing"));
            } else if (customer.equals("empty")) {
                return Result.ok(Collections.emptyList());
            } else {
                return Result.ok(Collections.singletonList(new Account(1L, customer)));
            }
        }
    }

}
