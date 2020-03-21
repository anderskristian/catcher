package dk.bank.protocol.step03exception;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class UseAccount3Test {

    private final UseAccount3.Protocol api;

    public UseAccount3Test() {
        api = new UseAccount3().api;
    }


    /**
     * Client is in control of response
     * and reacts to the fact that an account was found
     * <p>
     * Demonstrate that customer "Eva" got an account - use {@link Result#ifSuccess(Consumer)}
     * <p>
     * code is procedural - and also a monad
     */
    @Test
    public void resultIsSuccess() {
        final Result<List<Account>> response = api.getCustomerMainAccount("Eva");
        final AtomicBoolean success = new AtomicBoolean();
        response
                .ifSuccess(list -> {
                    System.out.println(String.format(
                            "Accounts: %s", list
                            )
                    );
                    success.set(true);
                })
                .ifFailure(error -> Assert.fail("this test should not fail " + error))
        ;
        Assert.assertTrue("Expected to have success", success.get());
    }

    /**
     * Client is in control of response
     * and reacts to the fact that an error was reported
     * <p>
     * Demonstrate that customer 'null' will fail to read account
     * <p>
     * code is procedural - and also a monad
     */
    @Test
    public void resultIsFailure() {

        final Result<List<Account>> response = api.getCustomerMainAccount(null);
        final AtomicBoolean failed = new AtomicBoolean();
        response
                .ifSuccess(list -> Assert.fail("this test should not have success"))
                .ifFailure(error -> {
                    failed.set(true);
                    try {
                        throw error;
                    } catch (Exception e) {
                        System.out.println(String.format("Getting accounts for customer null, answers: %s", e));
                    }
                });
        Assert.assertTrue("Expected to fail", failed.get());
    }


    /**
     * Client is in control of response
     * and reacts to the fact that an account was found
     *
     * Reads a list of accounts and selects the first account - if any
     */
    @Test
    public void resultFlatMap() {

        final Result<List<Account>> response = api.getCustomerMainAccount("Eva");
        final Result<Account> singleAccount = response
                .flatMap(
                        list ->
                                list.isEmpty()
                                        ? Result.error(new Exception("got no accounts"))
                                        : Result.ok(list.get(0))
                );
        singleAccount.ifSuccess(account ->
                System.out.println(String.format("Got single account: %s", account))
        );
        Assert.assertTrue("Expected to have success", singleAccount.isSuccess());
    }

    /**
     * Client is in control of response
     * and reacts to the fact that account got problems being found
     *
     * Reads a list of accounts and selects the first account - if any
     */
    @Test
    public void resultFlatMap_butErrorFromAPI() {

        final Result<List<Account>> response = api.getCustomerMainAccount(null);
        System.out.println(String.format("response.....: %s",response));
        final Result<Account> singleAccount = response
                .flatMap(
                        list -> documentThatFlatMapNotCalledwhenResultContainsError()
                );
        System.out.println(String.format("singleAccount: %s",singleAccount));
        singleAccount.ifSuccess(account -> {
                System.out.println(String.format("Got single account: %s", account));
                Assert.fail("Single account got no success because 'api' failed from the beginning" +
                        ", therefore this clojure is not executed");
                }
        );
        Assert.assertNotEquals("check that Monad 'response' and Monad 'singleAccount' are not the same", response, singleAccount);
        Assert.assertTrue("Expected to have failed", singleAccount.isError());
    }

    private Result<Account> documentThatFlatMapNotCalledwhenResultContainsError() {
        Assert.fail("program should never come here when response contains error");
        return Result.error(new Exception("flatmap ERROR never calls function when error"));
    }


}
