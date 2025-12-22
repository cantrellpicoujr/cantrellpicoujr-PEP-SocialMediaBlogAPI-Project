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

  public List<Message> getMessages() {

    String sql = "SELECT * FROM message";
    Connection connection;
    List<Message> list = new ArrayList<>();

    try {

      connection = ConnectionUtil.getConnection();
      PreparedStatement ps = connection.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Integer message_id = rs.getInt("message_id");
        Integer posted_by = rs.getInt("posted_by");
        String message_text = rs.getString("message_text");
        Long time_posted_epoch = rs.getLong("time_posted_epoch");

        list.add(new Message(message_id, posted_by, message_text, time_posted_epoch));

      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } 

    return list;

  }

  public Message getMessageById(Integer id) {

    PreparedStatement ps;
    Connection connection;
    String sql = "SELECT * FROM message WHERE message_id = ?";

    try {

      connection = ConnectionUtil.getConnection();
      ps = connection.prepareStatement(sql);
      ps.setInt(1,id);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        Integer message_id = rs.getInt("message_id");
        Integer posted_by = rs.getInt("posted_by");
        String message_text = rs.getString("message_text");
        Long time_posted_epoch = rs.getLong("time_posted_epoch");

        return new Message(message_id, posted_by, message_text, time_posted_epoch);
      }


    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;

  }

  
}
