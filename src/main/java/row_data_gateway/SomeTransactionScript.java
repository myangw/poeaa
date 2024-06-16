package row_data_gateway;

import java.util.Iterator;

class SomeTransactionScript {

    public String foo() {
        PersonFinder finder = new PersonFinder();
        Iterator people = finder.findResponsibles().iterator();

        StringBuffer result = new StringBuffer();
        while (people.hasNext()) {
            PersonGateway each = (PersonGateway) people.next();
            result.append(each.getLastName());
            result.append("\t");
            result.append(each.getFirstName());
            result.append("\t");
            result.append(each.getNumberOfDependents());
            result.append("\n");
        }
        return result.toString();

    }
}
