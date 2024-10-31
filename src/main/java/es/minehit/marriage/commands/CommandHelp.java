package es.minehit.marriage.commands;

import es.minehit.marriage.Marriage;
import es.minehit.marriage.config.Message;
import es.minehit.marriage.internal.MarriageBase;
import es.minehit.marriage.internal.MarriageCommandExecutor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;

public class CommandHelp extends Command {

    public CommandHelp(Marriage marriage) {
        super(marriage, "help");
        setMinArgs(-1);
        setHidden(true);
        setPermission(null);
        setAllowConsole(true);
    }

    @Override
    public void execute() {
        MarriageCommandExecutor commandExecutor = ((MarriageBase) marriage).getCommandExecutor();
        reply("&r");
        reply("&8&m---------------&r &fAyuda: &b&lBodas &8&m---------------");
        reply("&r");
        for(Command command : commandExecutor.getSubCommands()) {
            if(command.isHidden()) {
                continue;
            }

            String alias = command instanceof CommandMarry ? "" : command.getAliases()[0] + " ";
            String text = "&e/marry " + alias + command.getUsage() + " &8- &7" + command.getDescription();
            if(command.getExecutionFee() == 0.0 || !marriage.dependencies().isEconomyEnabled() || player == null) {
                reply(text);
                continue;
            }
            ComponentBuilder builder = new ComponentBuilder("/marry " + alias + command.getUsage()).color(ChatColor.GREEN)
                    .event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Cost: "
                            + marriage.dependencies().getEconomyService().format(command.getExecutionFee())).create()))
                    .append(" - ").color(ChatColor.WHITE)
                    .event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Cost: "
                            + marriage.dependencies().getEconomyService().format(command.getExecutionFee())).create()))
                    .append(command.getDescription()).color(ChatColor.GRAY)
                    .event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Cost: "
                            + marriage.dependencies().getEconomyService().format(command.getExecutionFee())).create()));
            player.spigot().sendMessage(builder.create());
            reply("&f");
        }

        String status = Message.SINGLE.toString();
        if(player != null && player.hasMetadata("marriedTo")) {
            String partner = player.getMetadata("marriedTo").get(0).asString();
            status = String.format(Message.MARRIED_TO.toString(), partner);
        }
        reply(Message.STATUS, status);
        reply("&r");
        reply("&8&m--------------------------------------------");
        reply("&r");
    }
}
