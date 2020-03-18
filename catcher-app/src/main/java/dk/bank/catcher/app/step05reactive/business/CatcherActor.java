package dk.bank.catcher.app.step05reactive.business;

import io.vlingo.actors.Actor;
import io.vlingo.common.Completes;
import io.vlingo.common.Failure;
import io.vlingo.common.Outcome;
import io.vlingo.common.Success;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementing the scenario where we look for dirty works
 * <p>
 * Implementation of the business code does not know repository's internal design.
 */
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
        // the value to return via answerFrom
        final Completes<Outcome<Exception, List<Posting>>> answer;

        if(aDay==null)throw new RuntimeException("Ups, an internal error in Actor");
        if (aDay.isBefore(LocalDate.parse("2001-09-11"))) {
            // Failure is expected and therefore we are completing successfully
            answer = Completes.withSuccess(
                // The failure is encoded as a failed Outcome value with the expected exception
                Failure.of(new IllegalArgumentException("No AML before 9/11")));
        }
        else {
            // Repository returns a List of postings inside a Completes (async)
            final Completes<List<Posting>> future =
                repository.selectPostings(aDay);

            // Assign answer to the result of the following transformation ...
            answer = future
                // The List of postings is filtered to preserve only the ones we suspect are fraudulent
                .andThen(this::filterFraudulent)
                // The List of suspected postings is transformed to an successful Outcome of List of Posting
                .andThen(Success::of);
        }

        // return the answer value via answerFrom
        return answerFrom(answer);
    }


    /**
     * Filters a list of postings to preserve only
     * the ones we suspect are fraudulent.
     *
     * @param postings the postings in question.
     * @return the postings which are suspected to be fraudulent
     */
    private List<Posting> filterFraudulent(List<Posting> postings) {
        return postings.stream()
            .filter(this::isFraudCandidate)
            .collect(Collectors.toList());
    }

    /**
     * Checks if the posting is candidate for fraud.
     *
     * @param posting the posting in question
     * @return true if the posting is fraudulent, false otherwise
     */
    private boolean isFraudCandidate(Posting posting) {
        final String lowerCasePostingText =
            posting.postingText.toLowerCase();
        return lowerCasePostingText.contains("pimp")
            || lowerCasePostingText.contains("orange");
    }
}
