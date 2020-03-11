package dk.bank.catcher.app.step05reactive.business;

import io.vlingo.common.Completes;

import java.time.LocalDate;
import java.util.List;

/**
 * Adapter protocol
 */
public interface Repository {
    Completes<List<Posting>> selectPostings(LocalDate aDay);
}
