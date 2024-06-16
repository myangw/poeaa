package row_data_gateway;

import domain_model.Money;

/**
 * 도메인 객체
 */
class Person {

    private PersonGateway data;

    private Long id;
    private String lastName;
    private String firstName;
    private int numberOfDependents;

    public Person() {
        this.data = new PersonGateway(id, lastName, firstName, numberOfDependents);
    }

    public int getNumberOfDependents() {
        return data.getNumberOfDependents();
    }

    public Money getExemption() {
        Money baseExemption = Money.of(1500);
        Money dependentExemption = Money.of(750);
        return baseExemption.add(dependentExemption.multiply(getNumberOfDependents()));
    }
}
