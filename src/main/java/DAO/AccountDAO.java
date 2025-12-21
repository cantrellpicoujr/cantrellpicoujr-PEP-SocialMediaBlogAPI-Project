package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {

    public Account insertAccount(Account account) {

      Connection connection = ConnectionUtil.getConnection();
      PreparedStatement ps;
      String sql;
      ResultSet pkeyResultSet;

      try {

          sql = "SELECT * FROM account WHERE username = ?";
          ps = connection.prepareStatement(sql);
          ps.setString(1,account.getUsername());

          ResultSet rs = ps.executeQuery();

          if (rs.next()) {
            return null; 
          }

          sql = "INSERT INTO account (username, password) VALUES (?,?)";
          ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

          ps.setString(1, account.getUsername());
          ps.setString(2, account.getPassword());

          ps.executeUpdate();
          pkeyResultSet = ps.getGeneratedKeys();
          
          if(pkeyResultSet.next()) {
            int generated_account_id = (int) pkeyResultSet.getLong(1);
            return new Account(generated_account_id, account.getUsername(), account.getPassword());
          }

      } catch(SQLException e) {
        System.out.println(e.getMessage());
      }
    
      return null;

  }

  public Account loginAccount(Account account) {

    Connection connection = ConnectionUtil.getConnection();
    PreparedStatement ps;

    try {

      String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
      ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1,account.getUsername());
      ps.setString(2,account.getPassword());

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        Integer account_id = rs.getInt("account_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        return new Account(account_id, username, password); 
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
 
}
