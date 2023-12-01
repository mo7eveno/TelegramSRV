package me.mo7eveno.minecraft;

import me.mo7eveno.TelegramSRV;
import net.kyori.adventure.text.Component;

public class MinecraftToPlayerChat {
    public static void MinecraftPlayerChat(String username, String text) {
        checkEnabled(username, text);
    }
    static void checkEnabled(String username, String text) {
        if (TelegramSRV.getInstance().getConfig().getBoolean("telegram_to_minecraft.enabled")) {
            sendMessageToMinecraft(username, text);
        }
    }
    static void sendMessageToMinecraft(String username, String text) {
        TelegramSRV.getInstance().getServer().broadcast(Component.text(formattingMessage(username, text)));
    }
    static String formattingMessage(String username, String text) {
        String formatMessage = TelegramSRV.getInstance().getConfig().getString("telegram_to_minecraft.format_message");
        formatMessage = formatMessage.replace("{username}", username);
        formatMessage = formatMessage.replace("{message}", text);
        // replace symbol for color minecraft
        formatMessage = formatMessage.replace("&", "§");
        return formatMessage;
    }
}
