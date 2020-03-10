package dk.bec.catcher.app.step05reactive.business;

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
    Completes<Outcome<Exception, List<Posting>>> checkFraudAtDay(LocalDate aDay);
}
