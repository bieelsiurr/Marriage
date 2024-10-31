package es.minehit.marriage.commands;

import es.minehit.marriage.MPlayer;
import es.minehit.marriage.Marriage;
import es.minehit.marriage.config.Message;
import es.minehit.marriage.config.Permissions;

public class CommandChatSpy extends Command {
    public CommandChatSpy(Marriage marriage) {
        super(marriage, "chatspy");
        setDescription("Enable admin chat spy.");
        setPermission(Permissions.CHAT_SPY);
        setHidden(true);
    }

    @Override
    public void execute() {
        MPlayer mPlayer = marriage.getMPlayer(player);
        boolean mode = !mPlayer.isChatSpy();
        mPlayer.setChatSpy(mode);
        reply(mode ? Message.CHAT_SPY_ENABLED : Message.CHAT_SPY_DISABLED);
    }
}
