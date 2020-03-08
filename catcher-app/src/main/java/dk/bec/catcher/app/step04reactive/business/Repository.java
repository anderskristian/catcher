package dk.bec.catcher.app.step04reactive.business;

import java.time.LocalDate;
import java.util.List;

/**
 * Adapter protocol
 */
public interface Repository {
    List<Posting> selectPostings(LocalDate aDay);
}
