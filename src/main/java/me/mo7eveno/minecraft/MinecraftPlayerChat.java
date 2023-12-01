package me.mo7eveno.minecraft;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mo7eveno.TelegramSRV;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class MinecraftPlayerChat implements Listener {
    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerChat(PlayerChatEvent e) {
        preIntizialize(e);
    }
    void preIntizialize(PlayerChatEvent e) {
        nextStepCheckEnabled(e);
    }
    void nextStepCheckEnabled(PlayerChatEvent e) {
        if (TelegramSRV.getInstance().getConfig().getBoolean("minecraft_to_telegram.enabled")) {
          startSendingMessageToTelegram(e);
        }
    }
    void startSendingMessageToTelegram(PlayerChatEvent e) {
        String message_to_telegram = intizializeMessage(e);
        TelegramSRV.bot.botSendMessage(message_to_telegram);
    }
    String intizializeMessage(PlayerChatEvent e) {
        return textReplacer(e);
    }
    String textReplacer(PlayerChatEvent e) {
        String formatOfMessage = TelegramSRV.getInstance().getConfig().getString("minecraft_to_telegram.format_message");
        String textOfMessage = e.getMessage();
        formatOfMessage = formatOfMessage.replace("{message}", textOfMessage);
        String message = PlaceholderAPI.setPlaceholders(e.getPlayer(), formatOfMessage);
        return message;
    }
}