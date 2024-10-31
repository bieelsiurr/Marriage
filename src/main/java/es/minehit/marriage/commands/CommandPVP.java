package es.minehit.marriage.commands;

import es.minehit.marriage.MData;
import es.minehit.marriage.MPlayer;
import es.minehit.marriage.Marriage;
import es.minehit.marriage.config.Message;
import es.minehit.marriage.config.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandPVP extends Command {
    public CommandPVP(Marriage marriage) {
        super(marriage, "pvp");
        setPermission(Permissions.PVP_TOGGLE);
        setDescription(Message.COMMAND_PVP.toString());
        setUsage(Message.ON_OFF.toString());
    }

    @Override
    public void execute() {
        MPlayer mPlayer = marriage.getMPlayer(player);
        MData marriage = mPlayer.getMarriage();
        if(marriage == null) {
            reply(Message.NOT_MARRIED);
            return;
        }

        boolean value = value(marriage);
        marriage.setPVPEnabled(value);
        reply(value ? Message.PVP_ENABLED : Message.PVP_DISABLED);

        Player partner = Bukkit.getPlayer(marriage.getOtherPlayer(player.getUniqueId()));
        if(partner == null) {
            return;
        }

        reply(partner, Message.PARTNER_PVP);
    }

    private boolean value(MData marriage) {
        if(getArgLength() == 0) {
            // Toggle
            return !marriage.isPVPEnabled();
        }

        return getArg(0).equalsIgnoreCase("on");
    }
}
