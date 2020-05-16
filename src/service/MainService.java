/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.UserDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import model.User;

/**
 *
 * @author student
 */
public class MainService {
    private String user = "root";
    private String pass = "";
    private String url = "jdbc:mysql://localhost/java1pexamen";
    private Connection con;
    
    private MainService() {
        try {
            this.con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static final class SingletonHolder {
        private static final MainService INSTANCE = new MainService();
    }
    
    public static MainService getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public boolean register(User user) {
        UserDao userDao = new UserDao(con);
        boolean res = false;
        
        try {
            Optional<User> optionalUser = userDao.findUser(user.getName());
            if (!optionalUser.isPresent()) {
                userDao.addUser(user);
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return res;
    }
    
    public Optional<User> login(String username, String password) {
        UserDao userDao = new UserDao(con);
        
        try {
            Optional<User> optionalUser = userDao.findUser(username);
            if (optionalUser.isPresent()) {
                if (optionalUser.get().getPassword().equals(password)) {
                    return optionalUser;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    public void delete(User user) {
        UserDao userDao = new UserDao(con);
        
        try {
            userDao.deleteUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
