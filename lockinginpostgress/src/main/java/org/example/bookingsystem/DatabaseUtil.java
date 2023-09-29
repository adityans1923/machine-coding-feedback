package org.example.bookingsystem;

import java.sql.*;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class DatabaseUtil {
    private static final String INSERT_BOOKING_SEAT_SQL = "{ ? = call bookingsystem.public.bookshowseat(?, ?)}";

    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookingsystem",
                            "aditya.singh", "");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        System.out.println("Opened database successfully");
        return c;
    }

    public static void runInsert(Connection conn, int screen, int seat) throws SQLException {
        int prevLevel = conn.getTransactionIsolation();
        conn.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
        CallableStatement preparedStatement = conn.prepareCall(INSERT_BOOKING_SEAT_SQL);
        preparedStatement.registerOutParameter(1, Types.BOOLEAN);
        preparedStatement.setInt(2, screen);
        preparedStatement.setInt(3, seat);
        preparedStatement.execute();
        System.out.println("insert seat from " + Thread.currentThread().getName() + " - " + preparedStatement.getBoolean(1));
        conn.setTransactionIsolation(prevLevel);
    }

    public static void runInit(Connection conn) throws SQLException {
        CallableStatement preparedStatement2 = conn.prepareCall("{? = call bookingsystem.public.init()}");
        preparedStatement2.registerOutParameter(1, Types.BOOLEAN);
        System.out.println("init2: " + preparedStatement2.execute());
        System.out.println("init2: " + preparedStatement2.getBoolean(1));
        CallableStatement preparedStatement = conn.prepareCall("{? = call bookingsystem.public.setupdata()}");
        preparedStatement.registerOutParameter(1, Types.BOOLEAN);
        System.out.println("init2: " + preparedStatement.execute() + preparedStatement.getBoolean(1));
    }


}
