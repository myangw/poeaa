package domain_model.strategy;

import domain_model.Contract;
import domain_model.MfDate;
import domain_model.Money;
import domain_model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ThreeWayRecognitionStrategyTest {
    public static final int ANY = 1;

    @Test
    @DisplayName("수익 인식은 날짜마다 3등분하여 계산한다.")
    void test1() {
        var contract = new Contract(mock(Product.class), Money.of(6), mock(MfDate.class));

        var sut = new ThreeWayRecognitionStrategy(ANY, ANY);
        sut.calculateRevenueRecognitions(contract);

        assertThat(contract.getRevenueRecognitions()).hasSize(3);
        assertThat(contract.getRevenueRecognitions().get(0).getAmount()).isEqualTo(Money.of(2));
        assertThat(contract.getRevenueRecognitions().get(1).getAmount()).isEqualTo(Money.of(2));
        assertThat(contract.getRevenueRecognitions().get(2).getAmount()).isEqualTo(Money.of(2));
    }


    @Test
    @DisplayName("수익 인식의 날짜는 sign한 날짜, 첫번째offset 더한날짜, 두번째offset 더한 날짜")
    void test2() {
        var whenSigned = MfDate.of(LocalDateTime.MIN);
        var contract = new Contract(mock(Product.class), Money.of(ANY), whenSigned);

        var sut = new ThreeWayRecognitionStrategy(1, 2);
        sut.calculateRevenueRecognitions(contract);

        assertThat(contract.getRevenueRecognitions().get(0).getDate()).isEqualTo(whenSigned);
        assertThat(contract.getRevenueRecognitions().get(1).getDate()).isEqualTo(whenSigned.addDays(1));
        assertThat(contract.getRevenueRecognitions().get(2).getDate()).isEqualTo(whenSigned.addDays(2));
    }

}
