package domain_model;

import domain_model.strategy.CompleteRecognitionStrategy;
import domain_model.strategy.RecognitionStrategy;
import domain_model.strategy.ThreeWayRecognitionStrategy;

public class Product {

    private String name;
    private RecognitionStrategy recognitionStrategy;

    Product(String name, RecognitionStrategy recognitionStrategy) {
        this.name = name;
        this.recognitionStrategy = recognitionStrategy;
    }

    public static Product newWordProcessor(String name) {
        return new Product(name, new CompleteRecognitionStrategy());
    }

    public static Product newSpreadsheet(String name) {
        return new Product(name, new ThreeWayRecognitionStrategy(60, 90));
    }

    public static Product newDatabase(String name) {
        return new Product(name, new ThreeWayRecognitionStrategy(30, 60));
    }

    void calculateRevenueRecognitions(Contract contract) {
        this.recognitionStrategy.calculateRevenueRecognitions(contract);
    }
}
