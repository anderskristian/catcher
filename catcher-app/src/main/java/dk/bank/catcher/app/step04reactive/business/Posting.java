package dk.bank.catcher.app.step04reactive.business;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("date=").append(date);
        sb.append(", postingText='").append(postingText).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
