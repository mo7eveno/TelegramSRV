package me.mo7eveno;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static me.mo7eveno.minecraft.MinecraftToPlayerChat.MinecraftPlayerChat;

public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramSRV plugin;
    public TelegramBot(TelegramSRV plugin) {
        this.plugin = plugin;
    }
    static String chatIdFromUpdate;
    public String chatIdFromConfig = TelegramSRV.getInstance().getConfig().getString("chat_id");
    static long idOfSendedMessage;
    @Override
    public void onUpdateReceived(Update update) {
        messageLoader(update);
    }
    void messageLoader(Update update) {
        creatingVariables(update);
        // going check time
        stepCheckTime(update);
    }
    void stepCheckTime(Update update) {
        if (TelegramSRV.PluginStartTime/1000 < update.getMessage().getDate()) {
            // new message, what we need to do
            stepCheckText(update);
        } else {
            // old messages, what we need to skip
        }
    }
    void stepCheckText(Update update) {
        if (update.getMessage().hasText()) {
            sendToMinecraft(update);
        }
    }
    void sendToMinecraft(Update update) {
        // send to minecraft
        String username = getUsernameFromUpdate(update);
        String text = getTextFromUpdate(update);
        MinecraftPlayerChat(username, text);
    }
    String getUsernameFromUpdate (Update update) {
        return update.getMessage().getFrom().getFirstName().replace("ㅤ", "???");
    }
    String getTextFromUpdate (Update update) {
        return update.getMessage().getText();
    }
    void creatingVariables(Update update) {
        chatIdFromUpdate = update.getMessage().getChatId().toString();
        idOfSendedMessage = 0;
    }
    public long botSendMessage(String text) {
        SendMessage message = new SendMessage();

        message.setChatId(chatIdFromConfig);
        message.setText(text);
        try {
            Message sendedMessage = execute(message);
            idOfSendedMessage = sendedMessage.getMessageId();
        } catch (TelegramApiException e) {
            TelegramSRV.getInstance().getServer().getLogger().info("Error #0");
        }
        return idOfSendedMessage;
    }
    @Override
    public String getBotUsername() {
        return TelegramSRV.getInstance().getConfig().getString("username_of_bot");
    }
    @Override
    public String getBotToken() {
        return TelegramSRV.getInstance().getConfig().getString("token");
    }
}
