package domain_model;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
public class Money {
    BigDecimal value;

    Money(BigDecimal value) {
        this.value = value;
    }

    public static Money of(int value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    Money add(Money amount) {
        return new Money(amount.value.add(this.value));
    }

    public Money[] allocate(int toSize) {
        var allocated = new Money[toSize];
        for (int i = 0; i < allocated.length; i++) {
            // todo: 올림내림 정책...
            allocated[i] = Money.of(this.value.divide(BigDecimal.valueOf(toSize), RoundingMode.FLOOR));
        }
        return allocated;
    }
}
