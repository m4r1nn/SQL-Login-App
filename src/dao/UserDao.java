/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import model.User;

/**
 *
 * @author student
 */
public class UserDao {
    private Connection con;
    private PreparedStatement stmt1;
    private PreparedStatement stmt2;
    private PreparedStatement stmt3;
    
    public UserDao(Connection con) {
        try {
            this.con = con;
            stmt1 = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt2 = con.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?)");
            stmt3 = con.prepareStatement("DELETE FROM users WHERE id = ?");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Optional<User> findUser(String username) throws SQLException {
        stmt1.setString(1, username);
        User user = null;
        try (ResultSet rs = stmt1.executeQuery();) {
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("username"));
                user.setPassword(rs.getString("parola"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }
    
    public void addUser(User user) throws SQLException {
        stmt2.setString(1, user.getName());
        stmt2.setString(2, user.getPassword());
        stmt2.executeUpdate();
    }
    
    public void deleteUser(User user) throws SQLException {
        stmt3.setString(1, String.valueOf(user.getId()));
        stmt3.executeUpdate();
    }
    
    
}
