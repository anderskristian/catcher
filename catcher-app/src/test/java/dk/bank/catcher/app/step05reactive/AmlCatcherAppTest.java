package dk.bank.catcher.app.step05reactive;

import dk.bank.catcher.app.step05reactive.business.Posting;
import io.vlingo.common.Completes;
import io.vlingo.common.Outcome;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


public class AmlCatcherAppTest {

    @Test
    public void checkFraudAtDay() throws InterruptedException {
        final AmlCatcherApp app = new AmlCatcherApp();
        final CountDownLatch latch = new CountDownLatch(1);
        final Completes<Outcome<Exception, List<Posting>>> future = app.checkFraudAtDay(LocalDate.parse("2020-04-01"));
        future
                .andThenConsume(outcome -> {
                    Assert.assertNotNull("There should always be an outcome, found none", outcome);
                    outcome
                            .andThen(
                                    (result -> {
                                        for (Posting candidate : result) {
                                            System.out.println(candidate);
                                        }
                                        return result;
                                    })
                            )
                            .otherwise(e -> {
                                Assert.fail("is not expected to come here when success. Discovered error e=" + e);
                                // this function must return a list of posting according to the contract of Outcome#otherwise
                                return Collections.emptyList();
                            });
                    latch.countDown();
                })
        ;
        Assert.assertTrue("Expect latch released from countdown", latch.await(100, TimeUnit.MILLISECONDS));
        app.world.terminate();
    }

    /**
     * Demonstrate an error
     *
     * Note future {@link Completes} yields an outcome {@link Outcome} -
     * and it is that outcome that yields to otherwise {@link Outcome#otherwise(Function)}
     *
     * So there is two levels of futures that yields at their levels.
     */
    @Test
    public void checkFraud_lastCentury() throws InterruptedException {
        final AmlCatcherApp app = new AmlCatcherApp();
        final CountDownLatch latch = new CountDownLatch(1);
        final Completes<Outcome<Exception, List<Posting>>> future = app.checkFraudAtDay(LocalDate.parse("1999-01-01"));
        future
                .andThenConsume(outcome -> {
                    outcome
                            .andThen(
                                    (result -> {
                                        Assert.fail("is not expected to come here when we got error. Discovered result=" + result);
                                        return result;
                                    })
                            ).otherwise(e -> {
                        Assert.assertTrue("exception was not the expected type", e instanceof IllegalArgumentException);
                        Assert.assertEquals("exception message was not the expected message", "No AML before 9/11", e.getMessage());
                        return Collections.emptyList(); // this function must return a list of posting according to the contract of Outcome#otherwise
                    });
                    latch.countDown();
                });
        Assert.assertTrue("Expect latch released from countdown", latch.await(100, TimeUnit.MILLISECONDS));
        app.world.terminate();
    }
}