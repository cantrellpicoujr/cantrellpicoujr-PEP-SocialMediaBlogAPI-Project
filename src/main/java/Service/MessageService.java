package Service;

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
  
}
