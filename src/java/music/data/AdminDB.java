package music.data;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import music.business.Admin;
import util.PasswordUtil;

/**
 *
 * @author ross1
 */
public class AdminDB {


    public static void Register(Admin admin) throws Exception {
        ConnectionPool1 pool = ConnectionPool1.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO userpass (Username, Password) "
                + "VALUES (?,?)";

        String query2
                = "INSERT INTO userrole (Username,Rolename) "
                + "VALUES (?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, admin.getUsername());
            String hashedPassword = PasswordUtil.hashAndSaltPassword(admin.getPassword());
            ps.setString(2, hashedPassword );
            ps.executeUpdate();

            ps2 = connection.prepareStatement(query2);
            ps2.setString(1, admin.getUsername());
            ps2.setString(2, admin.getRolename());
            ps2.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e);
            throw new Exception("Databse error");
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
            throw new Exception("Software error");
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closePreparedStatement(ps2);
            pool.freeConnection(connection);
        }
    }

    public static boolean UsernameExists(String Username) {
        ConnectionPool1 pool = ConnectionPool1.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT Username FROM userpass WHERE Username = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, Username);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e);
            return true;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
