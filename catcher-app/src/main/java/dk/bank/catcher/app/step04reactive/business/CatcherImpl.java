package dk.bank.catcher.app.step04reactive.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Implementing the scenario where we look for dirty works
 * <p>
 * Implementation of the business code does not know repository's internal design.
 */
@SuppressWarnings("TryWithIdenticalCatches")
public class CatcherImpl implements Catcher {

    final Repository repository;

    public CatcherImpl(Repository repository) {
        this.repository = repository;
    }

    /**
     * Business level solution that checks for bad words and returns postings with bad words.
     * <p>
     *     Added a reactive response
     *
     * @return list of postings we have caught - hallelujah
     */
    public CompletableFuture<List<Posting>> checkFraudAtDay(LocalDate aDay) {

        final CompletableFuture<List<Posting>> future = repository.selectPostings(aDay);

        List<Posting> candidates;
        try {
            final List<Posting> daysPostings=future.get();
            candidates = daysPostings.stream()
                    .filter(row -> (row.postingText.contains("pimp") || row.postingText.toLowerCase().contains("orange")))
                    .collect(Collectors.toList());
            return CompletableFuture.completedFuture(candidates);
        } catch (InterruptedException e) {
            candidates=new ArrayList<>();
            e.printStackTrace();
            return CompletableFuture.completedFuture(candidates);
        } catch (ExecutionException ee) {
            candidates=new ArrayList<>();
            ee.printStackTrace();
            return CompletableFuture.completedFuture(candidates);
        }

    }
}
