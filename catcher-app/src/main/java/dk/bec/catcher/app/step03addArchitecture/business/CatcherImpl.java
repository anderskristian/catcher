package dk.bec.catcher.app.step03addArchitecture.business;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementing the scenario where we look for dirty works
 * <p>
 * Implementation of the business code does not know repository's internal design.
 */
public class CatcherImpl implements Catcher {

    final Repository repository;

    public CatcherImpl(Repository repository) {
        this.repository = repository;
    }

    /**
     * Business level solution that checks for bad words and returns postings with bad words.
     * <p>
     * Design with streaming
     *
     * @return list of postings we have caught - hallelujah
     */
    public List<Posting> checkFraudAtDay(LocalDate aDay) {
        final List<Posting> daysPostings = repository.selectPostings(aDay);
        final List<Posting> candidates;

        candidates = daysPostings.stream()
                .filter(row -> (row.postingText.contains("pimp") || row.postingText.toLowerCase().contains("orange")))
                .collect(Collectors.toList());

        return candidates;
    }
}
