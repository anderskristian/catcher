package dk.bank.catcher.app.step05reactive.business;

import io.vlingo.common.Completes;
import io.vlingo.common.Outcome;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Business protocol
 * for scenario
 */
public interface Catcher {
    /**
     *
     * @param aDay day where analysis has "root" -
     *             - too old date generates an outcome exception
     *             - missing date becomes an error in actor
     */
    Completes<Outcome<Exception, List<Posting>>> checkFraudAtDay(LocalDate aDay);
}
