package es.minehit.marriage.commands;

import es.minehit.marriage.MData;
import es.minehit.marriage.MPlayer;
import es.minehit.marriage.Marriage;
import es.minehit.marriage.config.Message;
import org.bukkit.Location;

public class CommandHome extends Command {

    public CommandHome(Marriage marriage) {
        super(marriage, "home");
        setDescription(Message.COMMAND_HOME.toString());
    }

    @Override
    public void execute() {
        MPlayer mPlayer = marriage.getMPlayer(player);
        MData marriage = mPlayer.getMarriage();
        if(marriage == null) {
            reply(Message.NOT_MARRIED);
            return;
        }

        Location home = marriage.getHome();
        if(home == null) {
            reply(Message.HOME_NOT_SET);
            return;
        }

        player.teleport(home);
        reply(Message.HOME_TELEPORT);
    }
}