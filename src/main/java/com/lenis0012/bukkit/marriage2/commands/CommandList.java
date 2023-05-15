package com.lenis0012.bukkit.marriage2.commands;

import com.lenis0012.bukkit.marriage2.config.Message;
import com.lenis0012.bukkit.marriage2.internal.MarriageCore;
import com.lenis0012.bukkit.marriage2.misc.ListQuery;
import org.bukkit.Bukkit;

public class CommandList extends Command {

    public CommandList(MarriageCore marriage) {
        super(marriage, "list");
        setDescription(Message.COMMAND_LIST.toString());
        setUsage("[page]");

        setAllowConsole(true);
    }

    @Override
    public void execute() {
        reply(Message.FETCHING_LIST);
        final int page = getArgLength() > 0 ? getArgAsInt(0) : 1;
        if(page < 1) {
            reply(Message.NEGATIVE_NUMBER);
        }

        Bukkit.getScheduler().runTaskAsynchronously(marriage.getPlugin(), new Runnable() {
            @Override
            public void run() {
                final ListQuery list = marriage.getMarriageList(10, page - 1);
                list.send(sender);
            }
        });
    }
}
