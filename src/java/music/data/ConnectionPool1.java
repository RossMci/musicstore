package music.data;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.*;

public class ConnectionPool1 {

    private static ConnectionPool1 pool = null;
    private static DataSource dataSource = null;
   private static DataSource dataSource2 = null;

    public synchronized static ConnectionPool1 getInstance() {
        if (pool == null) {
            pool = new ConnectionPool1();
        }
        return pool;
    }

    private ConnectionPool1() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/testDB");
             dataSource2 = (DataSource) ic.lookup("java:/comp/env/jdbc/testStore");
        } catch (NamingException e) {
            System.err.println(e);
        }
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException sqle) {
            System.err.println(sqle);
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }
}