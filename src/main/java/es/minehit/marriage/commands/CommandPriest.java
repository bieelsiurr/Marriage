package es.minehit.marriage.commands;

import es.minehit.marriage.MPlayer;
import es.minehit.marriage.Marriage;
import es.minehit.marriage.config.Message;
import es.minehit.marriage.config.Permissions;
import es.minehit.marriage.config.Settings;
import org.bukkit.entity.Player;

/**
 * Created by Lennart on 7/9/2015.
 */
public class CommandPriest extends Command {

    public CommandPriest(Marriage marriage) {
        super(marriage, "priest");
        setDescription("Set whether or not a player should be priest.");
        setUsage("add/remove <player>");
        setMinArgs(2);
        setPermission(Permissions.ADMIN);

        if(!Settings.ENABLE_PRIEST.value()) {
            setHidden(true);
        }
    }

    @Override
    public void execute() {
        String type = getArg(0);
        Player player = getArgAsPlayer(1);
        if(player == null) {
            reply(Message.PLAYER_NOT_FOUND, getArg(1));
            return;
        }

        MPlayer mp = marriage.getMPlayer(player);
        if(type.equalsIgnoreCase("add")) {
            mp.setPriest(true);
            reply(Message.PRIEST_ADDED);
        } else if(type.equalsIgnoreCase("remove")) {
            mp.setPriest(false);
            reply(Message.PRIEST_REMOVED);
        }
    }
}
