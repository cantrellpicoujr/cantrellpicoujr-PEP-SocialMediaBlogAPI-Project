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
  
}
