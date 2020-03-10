package dk.bec.catcher.app.step05reactive;


import dk.bec.catcher.app.step05reactive.business.Catcher;
import dk.bec.catcher.app.step05reactive.business.CatcherActor;
import dk.bec.catcher.app.step05reactive.business.Posting;
import dk.bec.catcher.app.step05reactive.business.Repository;
import dk.bec.catcher.app.step05reactive.infrastructure.RepositoryActor;
import io.vlingo.actors.World;
import io.vlingo.common.Completes;
import io.vlingo.common.Outcome;

import java.time.LocalDate;
import java.util.List;

/**
 * Demonstration of how to catch criminal transactions
 * We search for suspicious words in the posting text.
 * <p>
 * In this step we have changed to {@link io.vlingo.actors.Actor}
 *
 * <p>
 * First we define a business package and a infrastructure.
 * And then move classes to these packages
 * <p>
 * <p>
 */
@SuppressWarnings("TryWithIdenticalCatches")
public class AmlCatcherApp {

    private static AmlCatcherApp app;

    public static void main(String[] args) {
        app = new AmlCatcherApp();
    }

    final World world;
    final Catcher catcher;
    final Repository repository;

    /**
     * Create the application
     *
     * The application got two actors
     * <ul>
     *     <li>RepositoryActor - reads rows from database</li>
     *     <li>CatcherActor - runs scenario on data to catch suspicious transactions</li>
     * </ul>
     *
     */
    public AmlCatcherApp() {
        world = World.startWithDefaults("catchers");

        repository = world.actorFor(Repository.class, RepositoryActor.class);
        catcher = world.actorFor(Catcher.class, CatcherActor.class, repository);
    }

    /**
     * Service checkFraudAtDay
     */
    public Completes<Outcome<Exception, List<Posting>>> checkFraudAtDay(LocalDate aDay) {

        final Completes<Outcome<Exception, List<Posting>>> candidates;
        candidates = catcher.checkFraudAtDay(aDay);
        return candidates;

    }

}
