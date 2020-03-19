package dk.bank.protocol.step02optional;

import java.util.Optional;


public class UseAccount {

    /**
     * Sample of return no account or an account
     */
    interface Protocol {
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
