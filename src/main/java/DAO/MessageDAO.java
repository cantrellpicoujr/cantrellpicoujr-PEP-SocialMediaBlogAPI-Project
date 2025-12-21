package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.util.*;
import java.sql.*;

public class MessageDAO {

  public Message addMessage(Message message) {

    String sql = "SELECT * FROM account WHERE account_id = ?";
    PreparedStatement ps;
    Connection connection = ConnectionUtil.getConnection();
    ResultSet pkeyResultSet;

    try {

      ps = connection.prepareStatement(sql);
      ps.setInt(1, message.getPosted_by());

      ResultSet rs = ps.executeQuery();

      if (!rs.next()) {
        return null;
      }

      sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
      ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setInt(1,message.getPosted_by());
      ps.setString(2,message.getMessage_text());
      ps.setLong(3, message.getTime_posted_epoch());
      ps.executeUpdate();
      pkeyResultSet = ps.getGeneratedKeys();

      if (pkeyResultSet.next()) {
        int generated_account_id = (int) pkeyResultSet.getLong(1);
        return new Message(generated_account_id, message.getPosted_by(),message.getMessage_text(), message.getTime_posted_epoch());
      }

    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  
}
