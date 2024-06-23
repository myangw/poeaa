package active_record;

import java.util.HashMap;
import java.util.Map;

class Registry {

    private static Map<Long, Person> persons = new HashMap<>();

    public static Person getPerson(Long id) {
        return persons.get(id);
    }

    static void addPerson(Person result) {
        persons.put(result.getId(), result);
    }
}
