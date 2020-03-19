package dk.bank.protocol.step02optional;

import dk.bank.protocol.step01simple.UseName2;
import jdk.internal.dynalink.linker.LinkerServices;

import javax.annotation.Nonnull;
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

    public static void main(String[] args) {
        UseAccount app=new UseAccount();
        final Optional<Account> tom = app.api.getCustomerMainAccount("tom");
        System.out.println(tom);
        final Optional<Account> account1 = app.api.getCustomerMainAccount("Eva");
        System.out.println(account1);


    }


    /**
     *
     */
    static class Implementation implements Protocol {

        @Override
        public Optional<Account> getCustomerMainAccount(String customer) {
            if(customer.equals("tom")){
                return Optional.empty();
            } else {
                return Optional.of(new Account(1L,customer));
            }
        }
    }

}
