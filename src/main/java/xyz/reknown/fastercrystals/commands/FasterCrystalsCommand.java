package xyz.reknown.fastercrystals.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import xyz.reknown.fastercrystals.FasterCrystals;

public class FasterCrystalsCommand implements CommandExecutor {

    private final NamespacedKey key;

    public FasterCrystalsCommand(String name) {
        FasterCrystals.getInstance().getCommand(name).setExecutor(this);
        this.key = new NamespacedKey(FasterCrystals.getInstance(), "fastcrystals");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("fastercrystals.reload")) {
                player.sendMessage(Component.text("You don't have permission.", NamedTextColor.RED));
                return true;
            }
            FasterCrystals.getInstance().reloadConfig();
            player.sendMessage(Component.text("Reloaded FasterCrystals config!", NamedTextColor.GREEN));
            return true;
        }

        if (!player.hasPermission("fastercrystals.toggle")) {
            player.sendMessage(Component.text("You don't have permission.", NamedTextColor.RED));
            return true;
        }

        PersistentDataContainer pdc = player.getPersistentDataContainer();
        boolean current = pdc.getOrDefault(key, PersistentDataType.BYTE, (byte) 1) == 1;
        boolean toggle = args.length == 0 ? !current : Boolean.parseBoolean(args[0]);

        pdc.set(key, PersistentDataType.BYTE, (byte) (toggle ? 1 : 0));

        FileConfiguration config = FasterCrystals.getInstance().getConfig();
        String stateKey = "state." + (toggle ? "on" : "off");
        String state = config.getString(stateKey);
        String text = config.getString("text");

        Component component = MiniMessage.miniMessage().deserialize(text, Placeholder.parsed("state", state));
        player.sendMessage(component);
        return true;
    }
}
