package dk.bec.catcher.app.step04reactive.business;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Adapter protocol
 */
public interface Repository {
    CompletableFuture<List<Posting>> selectPostings(LocalDate aDay);
}
