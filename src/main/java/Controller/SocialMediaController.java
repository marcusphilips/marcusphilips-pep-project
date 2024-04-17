package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    private SocialMediaService service;

    public SocialMediaController() {
        this.service = new SocialMediaService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::createUser);
        app.post("/login", this::login);
        app.post("/messages", this::post);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByMessageID);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserID);
        return app;
    }

    private void getAllMessagesByUserID(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        ArrayList<Message> messages = service.getAllByMessagesByUserID(id);
        ctx.json(messages);
        ctx.status(200);
    }

    private void updateMessage(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updated = service.updateMessage(id, message.getMessage_text());
        if (updated != null) {
            ctx.json(updated);
            ctx.status(200);
        } else
            ctx.status(400);
    }

    private void deleteMessage(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = service.deleteMessage(id);
        if (deletedMessage != null)
            ctx.json(deletedMessage);
        ctx.status(200);
    }

    private void getMessageByMessageID(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message m = service.getMessageByMessageID(id);
        if (m != null)
            ctx.json(m);
        ctx.status(200);
    }

    private void getAllMessages(Context ctx) throws JsonProcessingException {
        ArrayList<Message> messages = service.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    private void post(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = service.post(message);
        if (newMessage == null) {
            ctx.status(400);
        } else {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(newMessage));
        }
    }

    private void login(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(ctx.body(), Account.class);
        Account loggedAccount = service.login(user);
        if (loggedAccount == null) {
            ctx.status(401);
        } else {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(loggedAccount));
        }
    }

    private void createUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account newUser = mapper.readValue(ctx.body(), Account.class);
        Account addedUser = service.createAccount(newUser);
        if (addedUser == null) {
            ctx.status(400);
        } else {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addedUser));
        }
    }

}