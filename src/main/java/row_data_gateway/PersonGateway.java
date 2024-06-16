package row_data_gateway;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
class PersonGateway {

    private Long id; // 책엔 없는데 id가 필요함..

    private String lastName;
    private String firstName;
    private int numberOfDependents;

    private static final String updateStatementString =
            """
                    UPDATE people SET lastName = ?, firstName = ?, numberOfDependents = ? WHERE id = ?
            """;

    public void update() {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = DB.prepare(updateStatementString);
            updateStatement.setString(1, lastName);
            updateStatement.setString(2, firstName);
            updateStatement.setInt(3, numberOfDependents);
            updateStatement.setInt(4, getId().intValue());
            updateStatement.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(updateStatement);
        }
    }

    private static final String insertStatementString =
            """
                    INSERT INTO people VALUES(?, ?, ?, ?)
            """;

    public Long insert() {
        PreparedStatement insertStatement = null;
        try {
            insertStatement = DB.prepare(insertStatementString);
            setId(IdGenerator.nextId());
            insertStatement.setInt(1, getId().intValue());
            insertStatement.setString(2, lastName);
            insertStatement.setString(3, firstName);
            insertStatement.setInt(4, numberOfDependents);
            insertStatement.execute();
            return getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(insertStatement);
        }
    }

    public static PersonGateway load(ResultSet rs) throws SQLException {
        Long id = rs.getLong(1);
        PersonGateway result = (PersonGateway) Registry.getPerson(id);
        if (result != null) {
            return result;
        }
        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentsArg = rs.getInt(4);
        result = new PersonGateway(id, lastNameArg, firstNameArg, numDependentsArg);
        Registry.addPerson(result);
        return result;
    }

}
