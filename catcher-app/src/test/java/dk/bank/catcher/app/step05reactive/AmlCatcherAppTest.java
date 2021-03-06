package dk.bank.catcher.app.step05reactive;

import dk.bank.catcher.app.step05reactive.business.CatcherSupervisor;
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
     * Demonstrate an input error
     * <p>
     * Note future {@link Completes} yields an outcome {@link Outcome} -
     * and it is that outcome that yields to otherwise {@link Outcome#otherwise(Function)}
     * <p>
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

    /**
     * Demonstrate an error - there is a bug in the actor - and the {@link CatcherSupervisor} reports that.
     *
     * The sample is naive. The default supervisors are better than this supervisor.
     *
     * Handling error situations with {@link io.vlingo.actors.Supervisor} need use cases.
     *
     * Consider a RepositoryActor dies because the database disappears and comes back. Here restart
     * of RepositoryActor could be relevant.
     */
    @Test
    public void checkFraud_internalBug()  {
        final AmlCatcherApp app = new AmlCatcherApp();
        Assert.assertNull("bugs must be missing",app.bugRecording.get());
        final CountDownLatch latch = new CountDownLatch(1);
        final Completes<Outcome<Exception, List<Posting>>> future = app.checkFraudAtDay(null);
        Assert.assertNotNull(future);
        try {
            Assert.assertFalse("Expect latch to timeout", latch.await(100, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            Assert.fail("Was not interrupted");
        }
        Assert.assertNotNull("Expects a bug recorded",app.bugRecording.get());
        System.out.println(app.bugRecording.get());
        app.world.terminate();
    }
}