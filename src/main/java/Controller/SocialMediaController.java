package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.*;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{id}", this::getMessageByIdHandler);
        app.delete("/messages/{id}", this::deleteMessageByIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        if (addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount)).status(200);
            
        } else {
            ctx.status(400);
        }

    } 

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);

        if (loginAccount != null) {
            ctx.json(mapper.writeValueAsString(loginAccount)).status(200);
        } else {
            ctx.status(401);
        }

    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);

        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage)).status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> allMessages = messageService.getMessages();

        if (allMessages != null) {
            ctx.json(mapper.writeValueAsString(allMessages)).status(200);
        }

    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Integer id = Integer.valueOf(ctx.pathParam("id"));
        Message message = messageService.getMessageById(id);

        if (message != null) {

            ctx.json(mapper.writeValueAsString(message)).status(200);

        }

    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Integer id = Integer.valueOf(ctx.pathParam("id"));
        Message message = messageService.deleteMessageById(id);

        if (message != null) {
            ctx.json(mapper.writeValueAsString(message)).status(200);
        }
    }


}