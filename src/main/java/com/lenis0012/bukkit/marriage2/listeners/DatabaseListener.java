package com.lenis0012.bukkit.marriage2.listeners;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lenis0012.bukkit.marriage2.MPlayer;
import com.lenis0012.bukkit.marriage2.internal.MarriageCore;
import com.lenis0012.bukkit.marriage2.internal.MarriagePlugin;
import com.lenis0012.bukkit.marriage2.internal.data.DataManager;
import com.lenis0012.bukkit.marriage2.internal.data.MarriagePlayer;
import com.lenis0012.bukkit.marriage2.misc.ListQuery;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class DatabaseListener implements Listener {
    private final Cache<UUID, MarriagePlayer> cache = CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.SECONDS).build();
    private final MarriagePlugin plugin;
    private final MarriageCore core;

    public DatabaseListener(MarriagePlugin plugin, MarriageCore core) {
        this.plugin = plugin;
        this.core = core;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        if(event.getLoginResult() == Result.ALLOWED) {
            MarriagePlayer player = core.getDataManager().loadPlayer(event.getUniqueId());
            player.setLastName(event.getName());
            cache.put(event.getUniqueId(), player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID userId = player.getUniqueId();
        MarriagePlayer mplayer = cache.getIfPresent(userId);
        if(mplayer != null) {
            loadPartnerName(mplayer, player);
            core.setMPlayer(userId, mplayer);
            return;
        }

        // Something went wrong (unusually long login?)
        plugin.getLogger().log(Level.WARNING, "Player " + event.getPlayer().getName() + " was not in cache, and will be loaded on the main thread.");
        plugin.getLogger().log(Level.INFO, "If this message shows often, report to dev");
        mplayer = core.getDataManager().loadPlayer(userId);
        mplayer.setLastName(player.getName());
        loadPartnerName(mplayer, player);
        core.setMPlayer(userId, mplayer);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        core.unloadPlayer(player.getUniqueId());
        if(player.hasMetadata("marriedTo")) {
            player.removeMetadata("marriedTo", plugin);
        }
    }

    private void loadPartnerName(final MPlayer mplayer, final Player player) {
        if(!mplayer.isMarried()) return;
        DataManager.getExecutorService().execute(() -> {
            final String partner = ListQuery.getName(core.getDataManager(), mplayer.getMarriage().getOtherPlayer(player.getUniqueId()));
            if(partner == null) {
                return;
            }

            Bukkit.getScheduler().runTask(plugin, () -> player.setMetadata("marriedTo", new FixedMetadataValue(plugin, partner)));
        });
    }
}