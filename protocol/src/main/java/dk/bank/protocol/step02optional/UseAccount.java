package dk.bank.protocol.step02optional;

import java.util.Optional;

/**
 * Dealing with methods that can return nothing or something
 * See: https://www.baeldung.com/java-optional
 *
 * See great talk on monads: https://medium.com/@afcastano/monads-for-java-developers-part-1-the-optional-monad-aa6e797b8a6e
 *
 * Super comprehensive in where to use optionals and not see: https://dzone.com/articles/using-optional-correctly-is-not-optional
 *
 */
public class UseAccount {

    /**
     * Sample of return no account or an account
     */
    interface Protocol {
        /**
         * Getting the customers main account - not all customers got a main account
         * @param customer customer
         * @return result - Optional is empty when no account.
         */
        Optional<Account> getCustomerMainAccount(String customer);

    }

    final Protocol api;
    /**
     * Assemble the application
     */
    public UseAccount() {
        this.api =new Implementation();
    }


    /**
     * Implement the optional response.
     */
    static class Implementation implements Protocol {

        @Override
        public Optional<Account> getCustomerMainAccount(String customer) {
            if(customer.equals("empty")){
                return Optional.empty();
            } else {
                return Optional.of(new Account(1L,customer));
            }
        }
    }

}
