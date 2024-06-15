package domain_model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Getter
public class Contract {

    private Long id;
    private List<RevenueRecognition> revenueRecognitions;
    private Product product;
    private Money revenue;
    private MfDate whenSigned;

    public Contract(Product product, Money revenue, MfDate whenSigned) {
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.revenueRecognitions = new ArrayList<>();

        this.product = product;
        this.revenue = revenue;
        this.whenSigned = whenSigned;

        calculateRecognitions();
    }

    private void calculateRecognitions() {
        this.product.calculateRevenueRecognitions(this);
    }

    public Money recognizedRevenue(MfDate asOf) {
        Money result = Money.of(0);
        Iterator it = revenueRecognitions.iterator();
        while (it.hasNext()) {
            RevenueRecognition r = (RevenueRecognition) it.next();
            if (r.isRecognizableBy(asOf)) {
                result = result.add(r.getAmount());
            }
        }
        return result;
    }

    public void addRevenueRecognition(RevenueRecognition revenueRecognition) {
        this.revenueRecognitions.add(revenueRecognition);
    }
}
