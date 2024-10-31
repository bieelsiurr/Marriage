package es.minehit.marriage.commands;

import es.minehit.marriage.Marriage;
import es.minehit.marriage.config.Message;
import es.minehit.marriage.config.Permissions;
import es.minehit.marriage.internal.MarriageCore;
import es.minehit.marriage.internal.MarriagePlugin;

public class CommandReload extends Command {

    public CommandReload(Marriage marriage) {
        super(marriage, "reload");

        // Command options
        setDescription("Reload configuration settings");
        setPermission(Permissions.RELOAD);
        setAllowConsole(true);
        setHidden(true);
    }

    @Override
    public void execute() {
        ((MarriageCore) MarriagePlugin.getCore()).reloadSettings();
        reply(Message.CONFIG_RELOAD);
    }
}
