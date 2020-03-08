package dk.bec.catcher.app.step04reactive.business;

import java.time.LocalDate;
import java.util.List;

/**
 * Business protocol
 * for scenario
 */
public interface Catcher {
    List<Posting> checkFraudAtDay(LocalDate aDay);
}
