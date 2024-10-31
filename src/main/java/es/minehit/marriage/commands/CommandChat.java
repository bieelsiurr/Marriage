package es.minehit.marriage.commands;

import es.minehit.marriage.MPlayer;
import es.minehit.marriage.Marriage;
import es.minehit.marriage.config.Message;

public class CommandChat extends Command {

    public CommandChat(Marriage marriage) {
        super(marriage, "chat");
        setDescription(Message.COMMAND_CHAT.toString());
    }

    @Override
    public void execute() {
        MPlayer mPlayer = marriage.getMPlayer(player);
        if(!mPlayer.isMarried()) {
            reply(Message.NOT_MARRIED);
            return;
        }

        boolean value = !mPlayer.isInChat();
        mPlayer.setInChat(value);
        reply(value ? Message.CHAT_ENABLED : Message.CHAT_DISABLED);
    }
}
