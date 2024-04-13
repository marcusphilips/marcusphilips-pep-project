package DAO;

import static org.mockito.Mockito.lenient;

import java.sql.*;
import java.util.*;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;


public class SocialMediaDAO {
    public Account createAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
