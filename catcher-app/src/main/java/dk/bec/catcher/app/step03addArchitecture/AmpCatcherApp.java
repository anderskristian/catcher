package dk.bec.catcher.app.step03addArchitecture;

import dk.bec.catcher.app.step03addArchitecture.business.Catcher;
import dk.bec.catcher.app.step03addArchitecture.business.CatcherImpl;
import dk.bec.catcher.app.step03addArchitecture.business.Posting;
import dk.bec.catcher.app.step03addArchitecture.business.Repository;
import dk.bec.catcher.app.step03addArchitecture.infrastructure.RepositoryImpl;

import java.time.LocalDate;
import java.util.List;

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
public class AmpCatcherApp {


    final Catcher catcher;
    final Repository repository;

    /**
     * Create the application
     */
    public AmpCatcherApp() {
        repository = new RepositoryImpl();
        catcher = new CatcherImpl(repository);
    }

    /**
     * Service checkFraudAtDay
     */
    public List<Posting> checkFraudAtDay(LocalDate aDay){
       return catcher.checkFraudAtDay(aDay);
    }

}
