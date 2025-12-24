package Service;

import java.util.*;

import Model.Message;
import DAO.MessageDAO;

public class MessageService {

  private MessageDAO messageDAO;

  public MessageService() {
    messageDAO = new MessageDAO();
  }

  public Message addMessage(Message message) {

    if (message.getMessage_text() != "" && message.getMessage_text().length() < 255) {
      return messageDAO.addMessage(message);
    }

    return null;
  }

  public List<Message> getMessages() {
    return messageDAO.getMessages();
  }

  public Message getMessageById(Integer id) {
    return messageDAO.getMessageById(id);
  }

  public Message deleteMessageById(Integer id) {
    return messageDAO.deleteMessageById(id);
  }

  public Message updateMessageById(Integer id, Message message) {

      if (message.getMessage_text() != "" && message.getMessage_text().length() < 255) {
        return messageDAO.updateMessageById(id, message);
      }

      return null;

  }

  public List<Message> getMessageByAccountId(Integer account_id) {

    return messageDAO.getMessageByAccountId(account_id);

  }
  
}

