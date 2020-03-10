package dk.bec.catcher.app.step05reactive;

import dk.bec.catcher.app.step05reactive.business.Posting;
import io.vlingo.actors.testkit.TestUntil;
import io.vlingo.common.Completes;
import io.vlingo.common.Outcome;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;


public class AmlCatcherAppTest {

    @Test
    public void checkFraudAtDay() {
        AmlCatcherApp app = new AmlCatcherApp();
        final TestUntil until = TestUntil.happenings(1);
        final Completes<Outcome<Exception, List<Posting>>> future = app.checkFraudAtDay(LocalDate.parse("2020-04-01"));
        future
                .andThenConsume(outcome -> {
                    Assert.assertNotNull(outcome);
                    for (Posting candidate : outcome.getOrNull()) {
                        System.out.println(candidate);
                    }
                    until.happened();
                })
        ;
        until.completes();
        app.world.terminate();
    }

    /**
     * Demonstrate an error
     */
    @Test
    public void checkFraud_lastCentury() {
        AmlCatcherApp app = new AmlCatcherApp();
        final TestUntil until = TestUntil.happenings(1);
        final Completes<Outcome<Exception, List<Posting>>> future = app.checkFraudAtDay(LocalDate.parse("1999-01-01"));
        future
                .andThenConsume(outcome -> {
                    Assert.assertNotNull(outcome);
//                    for (Posting candidate : outcome.getOrNull()) {
//                        System.out.println(candidate);
//                    }
                    until.happened();
                })
                .otherwiseConsume(outcome -> {
                    System.out.println(outcome);
//            Assert.assertEquals(2, outcome.size());
//            for (Posting candidate : outcome) {
//                System.out.println(candidate);
//            }
                    until.happened();
                })
                .recoverFrom(err -> {
                    System.out.println(err);
                    return null;
                });
        until.completes();
        app.world.terminate();
    }
}