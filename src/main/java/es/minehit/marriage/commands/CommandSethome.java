package es.minehit.marriage.commands;

import es.minehit.marriage.MData;
import es.minehit.marriage.MPlayer;
import es.minehit.marriage.Marriage;
import es.minehit.marriage.config.Message;
import es.minehit.marriage.config.Settings;

public class CommandSethome extends Command {

    public CommandSethome(Marriage marriage) {
        super(marriage, "sethome");
        setExecutionFee(Settings.PRICE_SETHOME);
        setDescription(Message.COMMAND_SETHOME.toString());
    }

    @Override
    public void execute() {
        MPlayer mPlayer = marriage.getMPlayer(player);
        MData marriage = mPlayer.getMarriage();
        if(marriage == null) {
            reply(Message.NOT_MARRIED);
            return;
        }

        if(!payFee()) return;
        marriage.setHome(player.getLocation().clone());
        reply(Message.HOME_SET);
    }
}