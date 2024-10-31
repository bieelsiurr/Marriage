package es.minehit.marriage.commands;

import es.minehit.marriage.MPlayer;
import es.minehit.marriage.Marriage;
import es.minehit.marriage.config.Message;
import es.minehit.marriage.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandDivorce extends Command {

    public CommandDivorce(Marriage marriage) {
        super(marriage, "divorce");
        setDescription(Message.COMMAND_DIVORCE.toString());
        setExecutionFee(Settings.PRICE_DIVORCE);
    }

    @Override
    public void execute() {
        MPlayer mPlayer = marriage.getMPlayer(player);
        MPlayer partner = mPlayer.getPartner();
        if(partner == null) {
            reply(Message.NOT_MARRIED);
            return;
        }

        if(!payFee()) return;
        mPlayer.divorce();

        // Clear metadata
        player.removeMetadata("marriedTo", marriage.getPlugin());
        Player target = Bukkit.getPlayer(partner.getUniqueId());
        if(target != null) {
            target.removeMetadata("marriedTo", marriage.getPlugin());
        }

        broadcast(Message.DIVORCED, player.getName(), Bukkit.getOfflinePlayer(partner.getUniqueId()).getName());
    }
}
