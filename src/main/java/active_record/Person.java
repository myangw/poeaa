package active_record;

import domain_model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
class Person {

    private final static String findStatementString = """
            SELECT id, lastName, firstName, numberOfDependents
            FROM people
            WHERE id = ?
            """;

    private final static String updateStatementString = """
            UPDATE people
            SET lastName = ?, firstName = ?, numberOfDependents = ?
            WHERE id = ?
            """;

    private final static String insertStatementString = """
            INSERT INTO people
            VALUES(?, ?, ?, ?)
            """;

    public static Person find(Long id) {
        Person result = Registry.getPerson(id);
        if (result != null) return result;
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = DB.prepare(findStatementString);
            findStatement.setLong(1, id.longValue());
            rs = findStatement.executeQuery();
            rs.next();
            result = load(rs);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(findStatement, rs);
        }
    }

    public static Person find(long id) {
        return find(id);
    }

    public static Person load(ResultSet rs) throws SQLException {
        Long id = rs.getLong(1);
        Person result = (Person) Registry.getPerson(id);
        if (result != null) return result;
        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentsArg = rs.getInt(4);
        result = new Person(id, lastNameArg, firstNameArg, numDependentsArg);
        Registry.addPerson(result);
        return result;
    }

    @Getter
    @Setter
    private Long id;
    private String lastName;
    private String firstName;
    private int numberOfDependents;


    public void update() {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DB.prepare(updateStatementString);
            updateStatement.setString(1, lastName);
            updateStatement.setString(2, firstName);
            updateStatement.setInt(3, numberOfDependents);
            updateStatement.setLong(4, getId().longValue());
            updateStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(updateStatement);
        }
    }

    public long insert() {
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DB.prepare(insertStatementString);
            setId(IdGenerator.nextId());
            insertStatement.setLong(1, getId().longValue());
            insertStatement.setString(2, lastName);
            insertStatement.setString(3, firstName);
            insertStatement.setInt(4, numberOfDependents);
            insertStatement.execute();
            Registry.addPerson(this);
            return getId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(insertStatement);
        }
    }

    public Money getExemption() {
        Money baseExemption = Money.of(1500);
        Money dependentExemption = Money.of(750);
        return baseExemption.add(dependentExemption.multiply(numberOfDependents));
    }

}
