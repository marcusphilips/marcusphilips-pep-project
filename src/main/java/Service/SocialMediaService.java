package Service;

import java.util.ArrayList;

import DAO.SocialMediaDAO;
import Model.Account;
import Model.Message;

public class SocialMediaService {
    private SocialMediaDAO socialMediaDAO;
    public SocialMediaService(){
        this.socialMediaDAO = new SocialMediaDAO();
    }
    public SocialMediaService(SocialMediaDAO socialMediaDAO){
        this.socialMediaDAO = socialMediaDAO;
    }
    public Account createAccount(Account account){
        if (account.getPassword().length() >= 4 && account.getUsername().length() >= 1){
            // does the username already exist?
            if (socialMediaDAO.findAccountByUsername(account) == null){
                return socialMediaDAO.createAccount(account);
            }
        }
        return null;
    }
    public Account login(Account account){
        return socialMediaDAO.login(account);
    }
    public Message post(Message message){
        if (message.getMessage_text().length() != 0 && message.getMessage_text().length() <= 255){
            if (socialMediaDAO.findAccountByAccountID(message.getPosted_by()) != null){
                return socialMediaDAO.post(message);
            }
        }
        return null;
    }
    public ArrayList<Message> getAllMessages(){
        return socialMediaDAO.getAllMessages();
    }
    public Message getMessageByMessageID(int message_id){
        return socialMediaDAO.getMessageByMessageID(message_id);
    }
    public Message deleteMessage(int message_id){
        Message toDelete = socialMediaDAO.getMessageByMessageID(message_id);
        if (toDelete != null){
            socialMediaDAO.deleteMessage(message_id);
        }
        return toDelete;
    }
    public Message updateMessage(int message_id, String body){
        if (body.length() != 0 && body.length() <= 255){
            socialMediaDAO.updateMessage(message_id, body);
            return socialMediaDAO.getMessageByMessageID(message_id);
        }
        return null;
    }
    public ArrayList<Message> getAllByMessagesByUserID(int user_id){
        return socialMediaDAO.getAllMessagesByUserID(user_id);
    }
}