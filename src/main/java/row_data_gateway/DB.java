package row_data_gateway;

import java.sql.*;

class DB {

    private static final String DB_URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "pw";

    public static PreparedStatement prepare(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        return connection.prepareStatement(sql);
    }

    public static void cleanUp(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void cleanUp(PreparedStatement statement, ResultSet resultSet) {
        cleanUp(statement);
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
