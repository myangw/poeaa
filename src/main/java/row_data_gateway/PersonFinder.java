package row_data_gateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

class PersonFinder {

    private static final String findStatementString =
            """
                    SELECT id, lastName, firstName, numberOfDependents FROM people WHERE id = ?
            """;
    public static final String findResponsiblesStatementString =
            """
                    SELECT id, lastName, firstName, numberOfDependents FROM people WHERE numberOfDependents > 0
            """;

    public PersonGateway find(Long id) {
        PersonGateway result = (PersonGateway) Registry.getPerson(id);
        if (result != null) {
            return result;
        }

        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = DB.prepare(findStatementString);
            findStatement.setInt(1, id.intValue());
            rs = findStatement.executeQuery();
            rs.next();
            result = PersonGateway.load(rs);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(findStatement, rs);
        }
    }

    public List<PersonGateway> findResponsibles() {
        List<PersonGateway> result = new ArrayList<>();
        PreparedStatement findResponsiblesStatement = null;
        ResultSet rs = null;
        try {
            findResponsiblesStatement = DB.prepare(findResponsiblesStatementString);
            rs = findResponsiblesStatement.executeQuery();
            while (rs.next()) {
                result.add(PersonGateway.load(rs));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DB.cleanUp(findResponsiblesStatement, rs);
        }
    }

}
