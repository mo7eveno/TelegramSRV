package me.mo7eveno;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TelegramCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        TelegramSRV.getInstance().reloadConfig();
        sender.sendMessage(Component.text("Telegram config reloaded!"));
        return true;
    }
}
