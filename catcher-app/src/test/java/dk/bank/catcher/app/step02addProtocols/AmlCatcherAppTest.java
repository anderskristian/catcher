package dk.bank.catcher.app.step02addProtocols;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;


public class AmlCatcherAppTest {

    @Test
    public void checkFraudAtDay() {
        AmlCatcherApp app=new AmlCatcherApp();

        final List<AmlCatcherApp.Posting> candidates = app.checkFraudAtDay(LocalDate.parse("2020-04-01"));
        Assert.assertEquals(2,candidates.size());
    }


}