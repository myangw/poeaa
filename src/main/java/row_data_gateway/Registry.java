package row_data_gateway;

import java.util.HashMap;
import java.util.Map;

class Registry {

    private static Map<Long, PersonGateway> persons = new HashMap<>();

    public static PersonGateway getPerson(Long id) {
        return persons.get(id);
    }

    static void addPerson(PersonGateway result) {
        persons.put(result.getId(), result);
    }
}
