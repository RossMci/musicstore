/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import music.business.Admin;

/**
 *
 * @author ross1
 */
public class AdminDB {

    public static void Register(Admin admin) {
        ConnectionPool1 pool = ConnectionPool1.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO userpass (Username, Password)"
                + "VALUES (?,?)";
            
                String query2
                = "INSERT INTO userrole (Username,Rolename)"
                + "VALUES (?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.executeUpdate();
            
            ps2 = connection.prepareStatement(query2);
            ps2.setString(1, admin.getUsername());
            ps2.setString(2, admin.getRolename());
            ps2.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closePreparedStatement(ps2);
            pool.freeConnection(connection);
        }
    }
}
