package domain_model.strategy;

import domain_model.Contract;
import domain_model.MfDate;
import domain_model.Money;
import domain_model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CompleteRecognitionStrategyTest {

    @Test
    @DisplayName("계약날짜에 수익인식이 완료된다.")
    void test1() {
        Contract contract = new Contract(mock(Product.class), Money.of(100), MfDate.of(2000, 1, 1));
        CompleteRecognitionStrategy sut = new CompleteRecognitionStrategy();

        sut.calculateRevenueRecognitions(contract);

        assertThat(contract.getRevenueRecognitions().size()).isEqualTo(1);
        assertThat(contract.getRevenueRecognitions().get(0).getAmount()).isEqualTo(Money.of(100));
        assertThat(contract.getRevenueRecognitions().get(0).getDate()).isEqualTo(MfDate.of(2000, 1, 1));
    }

}
