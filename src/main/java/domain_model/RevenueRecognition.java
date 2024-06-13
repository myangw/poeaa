package domain_model;

import lombok.Getter;

@Getter
public class RevenueRecognition {
    private Money amount;
    private MfDate date;

    public RevenueRecognition(Money amount, MfDate date) {
        this.amount = amount;
        this.date = date;
    }

    boolean isRecognizableBy(MfDate asOf) {
        return asOf.after(date) || asOf.equals(date);
    }
}
