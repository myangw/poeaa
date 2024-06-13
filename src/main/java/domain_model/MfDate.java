package domain_model;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MfDate {
    final LocalDateTime value;

    MfDate(LocalDateTime value) {
        this.value = value;
    }

    public static MfDate of(LocalDateTime value) {
        return new MfDate(value);
    }

    public static MfDate of(int year, int month, int day) {
        return new MfDate(LocalDateTime.of(year, month, day, 0, 0));
    }

    boolean after(MfDate date) {
        return this.value.isAfter(date.value);
    }

    public MfDate addDays(int offSet) {
        return new MfDate(this.value.plusDays(offSet));
    }
}
