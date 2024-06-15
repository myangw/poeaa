package domain_model.strategy;

import domain_model.Contract;
import domain_model.RevenueRecognition;

public class CompleteRecognitionStrategy extends RecognitionStrategy {

    public void calculateRevenueRecognitions(Contract contract) {
        contract.addRevenueRecognition(new RevenueRecognition(contract.getRevenue(), contract.getWhenSigned()));
    }

}
