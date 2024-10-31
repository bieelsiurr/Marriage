package es.minehit.marriage;

import es.minehit.marriage.commands.Command;
import es.minehit.marriage.events.PlayerMarryEvent;
import es.minehit.marriage.internal.Dependencies;
import es.minehit.marriage.misc.BConfig;
import es.minehit.marriage.misc.ListQuery;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * @deprecated Use {@link MarriageAPI} instead.
 */
@Deprecated
public interface Marriage {
    /**
     * Return a {@link BConfig} from a YAML file.
     *
     * @param file File name.
     * @return Bukkit configuration instance.
     */
    BConfig getBukkitConfig(String file);

    /**
     * Return a {@link MPlayer MPlayer} instance of a player.
     * If the requested player is not online, their data will be loaded from the database, but it will NOT be cached.
     *
     * @param uuid Unique user id of the wanted player
     * @return {@link MPlayer MPlayer} of the wanted player
     */
    MPlayer getMPlayer(UUID uuid);

    /**
     * Return a {@link MPlayer MPlayer} instance of an online player.
     *
     * @param player Player who'se data is being requested, must be online
     * @return {@link MPlayer MPlayer}, or null if not online
     */
    MPlayer getMPlayer(Player player);

    /**
     * Get a list of all married players.
     * Note: This is IO, so please put it on an async task.
     *
     * @param scale Amount of results per page.
     * @param page  The page you want to fetch
     * @return Fetched page of marriages list
     */
    ListQuery getMarriageList(int scale, int page);

    /**
     * Marry 2 players with each other.
     *
     * @param player1 Player 1
     * @param player2 Player 2
     * @return The marriage data, null if cancelled via {@link PlayerMarryEvent PlayerMarryEvent}
     */
    MData marry(MPlayer player1, MPlayer player2);

    /**
     * Marry 2 players with each other.
     *
     * @param player1 Player 1
     * @param player2 Player 2
     * @param priest  Priest that married the players
     * @return Marriage data, null if cancelled via {@link PlayerMarryEvent PlayerMarryEvent}
     */
    MData marry(MPlayer player1, MPlayer player2, MPlayer priest);

    /**
     * Register a {@link org.bukkit.event.Listener} to this plugin.
     *
     * @param listener Listener to be registered.
     */
    void register(Listener listener);

    /**
     * Register a subcommand to the /marry command.
     *
     * @param commandClass Class of the sub command to be registered.
     * @param commandClasses Additional command classes
     */
    void register(Class<? extends Command> commandClass, Class<? extends Command>... commandClasses);

    /**
     * Get the plugin logger instance.
     *
     * @return Plugin logger.
     */
    Logger getLogger();

    /**
     * Get the plugin instance.
     *
     * @return Plugin instance.
     */
    Plugin getPlugin();

    /**
     * Plugin dependencies.
     * - Vault economy
     *
     * @return Dependencies
     */
    Dependencies dependencies();
}