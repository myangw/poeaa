package domain_model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ContractTest {

    @Test
    @DisplayName("RecognizeRevenue 메서드를 호출하면 수익 인식을 다 더한 수익을 리턴한다.")
    void recognizedRevenue() {

        var contract = new Contract(mock(Product.class), Money.of(0), MfDate.of(2000, 1, 1));
        contract.addRevenueRecognition(new RevenueRecognition(Money.of(100), MfDate.of(2000, 1, 1)));
        contract.addRevenueRecognition(new RevenueRecognition(Money.of(100), MfDate.of(2000, 1, 2)));
        contract.addRevenueRecognition(new RevenueRecognition(Money.of(100), MfDate.of(2000, 1, 3)));

        var result = contract.recognizedRevenue(MfDate.of(2000, 1, 2));

        assertThat(result).isEqualTo(Money.of(200));
    }
}
