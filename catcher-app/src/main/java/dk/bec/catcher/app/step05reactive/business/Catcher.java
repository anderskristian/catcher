package dk.bec.catcher.app.step05reactive.business;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Business protocol
 * for scenario
 */
public interface Catcher {
    CompletableFuture<List<Posting>> checkFraudAtDay(LocalDate aDay);
}
