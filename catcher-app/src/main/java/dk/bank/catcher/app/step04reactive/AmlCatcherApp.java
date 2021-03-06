package dk.bank.catcher.app.step04reactive;

import dk.bank.catcher.app.step04reactive.business.Catcher;
import dk.bank.catcher.app.step04reactive.business.CatcherImpl;
import dk.bank.catcher.app.step04reactive.business.Posting;
import dk.bank.catcher.app.step04reactive.business.Repository;
import dk.bank.catcher.app.step04reactive.infrastructure.RepositoryImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Demonstration of how to catch criminal transactions
 * We search for suspicious words in the posting text.
 * <p>
 * In this step we ave added architecture
 *
 * <p>
 * First we define a business package and a infrastructure.
 * And then move classes to these packages
 * <p>
 * <p>
 */
@SuppressWarnings("TryWithIdenticalCatches")
public class AmlCatcherApp {


    final Catcher catcher;
    final Repository repository;

    /**
     * Create the application
     */
    public AmlCatcherApp() {
        repository = new RepositoryImpl();
        catcher = new CatcherImpl(repository);
    }

    /**
     * Service checkFraudAtDay
     */
    public List<Posting> checkFraudAtDay(LocalDate aDay){
        final List<Posting> postings;
        try {
            postings = catcher.checkFraudAtDay(aDay).get();
            return postings;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        } catch (ExecutionException e2) {
            e2.printStackTrace();
            throw new IllegalStateException(e2);
        }
    }

}
