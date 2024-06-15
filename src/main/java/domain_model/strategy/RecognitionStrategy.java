package domain_model.strategy;

import domain_model.Contract;

public abstract class RecognitionStrategy {

    public abstract void calculateRevenueRecognitions(Contract contract);
}
