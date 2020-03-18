package dk.bank.catcher.app.step05reactive.business;

import io.vlingo.actors.DefaultSupervisor;
import io.vlingo.actors.Supervised;
import io.vlingo.actors.Supervisor;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Naive supervisor for catcher - just record the bug
 *
 * The sample
 *
 * The recording is done in a static variable - a more elegant solution should be possible
 */
public class CatcherSupervisor extends DefaultSupervisor implements Supervisor {

    public final static AtomicReference<String> bugRecording= new AtomicReference<>();;

    @Override
    public void inform(final Throwable throwable, final Supervised supervised) {
        StringBuilder sb=new StringBuilder();
        final String message="CatcherSupervisor: Failure of: " + supervised.address() + " because: " + throwable.getMessage() ;
        sb.append(message);
        bugRecording.set(sb.toString());
    }

}
