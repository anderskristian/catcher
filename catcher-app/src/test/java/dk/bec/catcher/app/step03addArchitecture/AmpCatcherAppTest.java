package dk.bec.catcher.app.step03addArchitecture;

import dk.bec.catcher.app.step03addArchitecture.business.Posting;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class AmpCatcherAppTest {

    @Test
    public void checkFraudAtDay() {
        AmpCatcherApp app=new AmpCatcherApp();

        final List<Posting> candidates = app.checkFraudAtDay(LocalDate.parse("2020-04-01"));
        Assert.assertEquals(2,candidates.size());
    }
}