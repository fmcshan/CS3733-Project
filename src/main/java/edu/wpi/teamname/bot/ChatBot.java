package edu.wpi.teamname.bot;

import com.google.api.client.util.Maps;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.*;
import edu.wpi.teamname.Algo.Node;
import edu.wpi.teamname.Database.DatabaseThread;
import edu.wpi.teamname.Database.Submit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatBot {

    private static final ChatBot instance = new ChatBot();
    private final String chatId = UUID.randomUUID().toString().replace("-", "");

    private ChatBot() {
    }

    public static synchronized ChatBot getInstance() {
        return instance;
    }

    public String getChatId() {
        return chatId;
    }

    public void sendMessage(String _msg) {
        Submit.getInstance().sendChatMessage(chatId, _msg);
    }

}