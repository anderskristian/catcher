package dk.bank.catcher.app.step05reactive.business;

import io.vlingo.actors.Actor;
import io.vlingo.common.Completes;
import io.vlingo.common.Failure;
import io.vlingo.common.Outcome;
import io.vlingo.common.Success;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementing the scenario where we look for dirty works
 * <p>
 * Implementation of the business code does not know repository's internal design.
 */
@SuppressWarnings("TryWithIdenticalCatches")
public class CatcherActor extends Actor implements Catcher {

    final Repository repository;

    public CatcherActor(Repository repository) {
        this.repository = repository;
    }

    /**
     * Business level solution that checks for bad words and returns postings with bad words.
     * <p>
     * Added a reactive response
     *
     * @return list of postings we have caught - hallelujah
     */
    public Completes<Outcome<Exception, List<Posting>>> checkFraudAtDay(LocalDate aDay) {

        try {
            if(aDay.isBefore(LocalDate.parse("2001-09-11"))) throw new IllegalArgumentException("No AML before 9/11");
        } catch (Exception inputException){
            return answerFrom(Completes.withFailure(Failure.of(inputException)));
        }
        final Completes<List<Posting>> future = repository.selectPostings(aDay);
        future.andThen(callback -> {
            final List<Posting> daysPostings = callback;
            List<Posting> candidates;
            candidates = daysPostings.stream()
                    .filter(row -> (row.postingText.contains("pimp") || row.postingText.toLowerCase().contains("orange")))
                    .collect(Collectors.toList());
            return answerFrom(Completes.withSuccess(Success.of(candidates)));
        });
        return null;//completableFuture(); // future
    }
}
