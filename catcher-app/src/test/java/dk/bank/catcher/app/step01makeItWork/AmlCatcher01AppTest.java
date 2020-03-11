package dk.bank.catcher.app.step01makeItWork;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;


public class AmlCatcher01AppTest {

    @Test
    public void testUnderstandingDateFormat() {
        Assert.assertEquals("Check that the date format is understood",LocalDate.of(2020,4,1),LocalDate.parse("2020-04-01"));
    }
    @Test
    public void selectPostings() {
        AmlCatcher01App.Repository repository= new AmlCatcher01App.Repository();
        List<AmlCatcher01App.Posting> candidates=repository.selectPostings(LocalDate.parse("2020-04-01"));
        Assert.assertFalse("Expects to find some rows April 1.st",candidates.isEmpty());
    }

    @Test
    public void checkFraudAtDay() {
        AmlCatcher01App catcher = new AmlCatcher01App();
        List<AmlCatcher01App.Posting> candidates = catcher.checkFraudAtDay(LocalDate.parse("2020-04-01"));

        Assert.assertEquals(2,candidates.size());
        for(AmlCatcher01App.Posting candidate:candidates){
            System.out.println(candidate);
        }
    }
    @Test
    public void selectPostingsAtDayWithNoPostings() {
        AmlCatcher01App.Repository repository= new AmlCatcher01App.Repository();
        List<AmlCatcher01App.Posting> candidates=repository.selectPostings(LocalDate.parse("1999-12-31"));
        Assert.assertTrue(candidates.isEmpty());
    }

}