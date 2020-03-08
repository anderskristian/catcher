package dk.bec.catcher.app.step04reactive;


import dk.bec.catcher.app.step04reactive.business.Posting;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class AmlCatcherAppTest {

    @Test
    public void checkFraudAtDay() {
        AmlCatcherApp app = new AmlCatcherApp();

        final List<Posting> candidates = app.checkFraudAtDay(LocalDate.parse("2020-04-01"));
        Assert.assertEquals(2, candidates.size());
        for(Posting candidate:candidates){
            System.out.println(candidate);
        }
    }
}