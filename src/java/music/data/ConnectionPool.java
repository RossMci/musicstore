package music.data;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.*;

public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static ConnectionPool pool2 = null;
    private static DataSource dataSource = null;
//    public ConnectionPool connection1;
//    public ConnectionPool connection2;
//    DataSource dataSource1;
//    DataSource dataSource2;
//    
//    public Connection dbConnect1() throws SQLException {
//    ComboPooledDataSource cpds = new ComboPooledDataSource();
//    try {
//        cpds.setDriverClass("com.mysql.jdbc.Driver");
//    } catch (PropertyVetoException e) {
//    }
//    cpds.setJdbcUrl("jdbc:mysql://localhost:3306/myDatabase1?autoReconnect=true"); 
//    cpds.setUser("myMYSQLServerLogin");
//    cpds.setPassword("myMYSQLServerPassword");
//    cpds.setMinPoolSize(5);
//    cpds.setAcquireIncrement(5);
//    cpds.setMaxPoolSize(20);
//    cpds.setMaxIdleTime(60);
//    cpds.setMaxStatements(100);
//    cpds.setPreferredTestQuery("SELECT 1");
//    cpds.setIdleConnectionTestPeriod(60);
//    dataSource1 = cpds;
//    connection1 = dataSource1.getConnection();
//    return connection1;
//}
//
//public Connection dbConnect2() throws SQLException {
//    ComboPooledDataSource cpds = new ComboPooledDataSource();
//    try {
//        cpds.setDriverClass("com.mysql.jdbc.Driver");
//    } catch (PropertyVetoException e) {
//    }
//    cpds.setJdbcUrl("jdbc:mysql://localhost:3306/myDatabase2?autoReconnect=true"); 
//    cpds.setUser("myMYSQLServerLogin");
//    cpds.setPassword("myMYSQLServerPassword");
//    cpds.setMinPoolSize(5);
//    cpds.setAcquireIncrement(5);
//    cpds.setMaxPoolSize(20);
//    cpds.setMaxIdleTime(60);
//    cpds.setMaxStatements(100);
//    cpds.setPreferredTestQuery("SELECT 1");
//    cpds.setIdleConnectionTestPeriod(60);
//    dataSource2 = cpds;
//    connection2 = dataSource2.getConnection();
//    return connection2;
//}

    public synchronized static ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }

        return pool;
    }

    private ConnectionPool() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/testDB");
//            dataSource2 = (DataSource) ic.lookup("java:/comp/env/jdbc/testStore");
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
