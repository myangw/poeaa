package domain_model.strategy;

import domain_model.Contract;
import domain_model.Money;
import domain_model.RevenueRecognition;

public class ThreeWayRecognitionStrategy extends RecognitionStrategy {

    private final int firstRecognitionOffset;
    private final int secondRecognitionOffset;

    public ThreeWayRecognitionStrategy(int firstRecognitionOffset, int secondRecognitionOffset) {
        this.firstRecognitionOffset = firstRecognitionOffset;
        this.secondRecognitionOffset = secondRecognitionOffset;
    }

    void calculateRevenueRecognitions(Contract contract) {
        Money[] allocation = contract.getRevenue().allocate(3);
        contract.addRevenueRecognition(new RevenueRecognition(allocation[0], contract.getWhenSigned()));
        contract.addRevenueRecognition(new RevenueRecognition(allocation[1], contract.getWhenSigned().addDays(firstRecognitionOffset)));
        contract.addRevenueRecognition(new RevenueRecognition(allocation[2], contract.getWhenSigned().addDays(secondRecognitionOffset)));
    }
}
