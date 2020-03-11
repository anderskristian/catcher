package dk.bank.catcher.app.step03addArchitecture.business;

import java.time.LocalDate;

public class Posting {
    public final LocalDate date;
    public final String postingText;
    public final Double amount;

    public Posting(LocalDate date, String postingText, Double amount) {
        this.date = date;
        this.postingText = postingText;
        this.amount = amount;
    }
}
