package domain_model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntContractTest {

    @Test
    @DisplayName("wordProcessor: 계약 생성시 모든 수익을 당일 인식한다.")
    void word() {
        var word = Product.newWordProcessor("ANY");
        var contract = new Contract(word, Money.of(300), MfDate.of(2024, 1, 1));

        var actual = contract.recognizedRevenue(MfDate.of(2024, 1, 1));

        assertThat(actual).isEqualTo(Money.of(300));
    }

    @Test
    @DisplayName("spreadsheet: 계약 생성시 1/3 수익을 당일 인식, 1/3 수익을 60일 후 인식, 1/3 수익을 90일 후 인식한다.")
    void spreadsheet() {
        var spreadsheet = Product.newSpreadsheet("ANY");
        var contract = new Contract(spreadsheet, Money.of(300), MfDate.of(2024, 1, 1));

        var actual = contract.recognizedRevenue(MfDate.of(2024, 1, 1));
        assertThat(actual).isEqualTo(Money.of(100));

        actual = contract.recognizedRevenue(MfDate.of(2024, 3, 1));
        assertThat(actual).isEqualTo(Money.of(200));

        actual = contract.recognizedRevenue(MfDate.of(2024, 4, 1));
        assertThat(actual).isEqualTo(Money.of(300));
    }

    @Test
    @DisplayName("database: 계약 생성시 1/3 수익을 당일 인식, 1/3 수익을 30일 후 인식, 1/3 수익을 60일 후 인식한다.")
    void database() {
        var database = Product.newDatabase("ANY");
        var contract = new Contract(database, Money.of(300), MfDate.of(2024, 1, 1));

        var actual = contract.recognizedRevenue(MfDate.of(2024, 1, 1));
        assertThat(actual).isEqualTo(Money.of(100));

        actual = contract.recognizedRevenue(MfDate.of(2024, 1, 31));
        assertThat(actual).isEqualTo(Money.of(200));

        actual = contract.recognizedRevenue(MfDate.of(2024, 3, 1));
        assertThat(actual).isEqualTo(Money.of(300));

    }
}
