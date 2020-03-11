package dk.bank.catcher.app.step03addArchitecture.business;

import java.time.LocalDate;
import java.util.List;

/**
 * Adapter protocol
 */
public interface Repository {
    List<Posting> selectPostings(LocalDate aDay);
}
