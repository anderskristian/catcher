package dk.bank.protocol.step02optional;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Using {@link Optional} to signal that API returns nothing or an account
 */
public class UseAccountTest {


    private final UseAccount.Protocol api;

    public UseAccountTest() {
        api = new UseAccount().api;
    }


    /**
     * Client is in control of response
     * and reacts to the fact that an account was found
     * <p>
     * Demonstrate that customer "Eva" got an account - use {@link Optional#isPresent()}
     * <p>
     * code is procedural
     */
    @Test
    public void optionalIsPresent() {
        final Optional<Account> response = api.getCustomerMainAccount("Eva");
        if (response.isPresent()) {
            System.out.println(String.format(
                    "Account: %s", response.get().csv()
                    )
            );
        }
    }

    /**
     * Demonstrate that customer "empty" has no accounts
     */
    @Test
    public void customerNamedEmptyGotNoMainAccount() {
        final Optional<Account> response = api.getCustomerMainAccount("empty");
        System.out.println(String.format("Optional: %s", response));
        Assert.assertFalse("Got no account. isPresent() is false", response.isPresent());

        //
        // demonstrate orElse
        //
        Account account = response.orElse(new Account(1L, "Another account"));
        System.out.println(String.format("Account: %s", account));
    }


    /**
     * Demonstrate that customer "Eva" got an account
     */
    @Test
    public void implementConsumerLambda() {
        final Optional<Account> response = api.getCustomerMainAccount("Eva");
        response.ifPresent(
                account ->
                        System.out.println(
                                String.format(
                                        "Account: %s", account.toString()
                                )
                        )
        );
    }

    /**
     * Demonstrate that customer "Eva" with functional style with code
     */
    @Test
    public void implementConsumerWithCode() {

        final Optional<Account> response = api.getCustomerMainAccount("Eva");

        final Consumer<Account> consumer; // create instance to perform lambda
        consumer = new ConsumeAccount();
        response.ifPresent(consumer);
    }

    private static class ConsumeAccount implements Consumer<Account> {

        @Override
        public void accept(Account account) {
            System.out.println(
                    String.format(
                            "csv: %s", account.csv()
                    )
            );
        }
    }

    /**
     * Demonstrate that customer "Eva" with functional style with code
     * Flat mapping
     */
    @Test
    public void theAccountNumber() {

        final Optional<Account> response = api.getCustomerMainAccount("Eva");

        final Optional<Long> optionalLong = response
                .flatMap(account -> Optional.of(account.getId()));

        optionalLong
                .ifPresent(id -> {
                            Assert.assertEquals("Expects id number, ", 1L, (long) id);
                        }
                );

    }

}