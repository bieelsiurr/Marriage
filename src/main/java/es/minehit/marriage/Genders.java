package es.minehit.marriage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * A container for the available genders for the players to chose from.
 */
public class Genders {
    private static final LinkedHashMap<String, PlayerGender> GENDER_OPTIONS = new LinkedHashMap<>();

    /**
     * Get a collection of all possible gender options configured.
     *
     * @return Ordered collection of genders
     */
    public static Collection<PlayerGender> getOptions() {
        return Collections.unmodifiableCollection(GENDER_OPTIONS.values());
    }

    /**
     * Get a gender by its identifying configuration key.
     *
     * @param identifier The configuration key associated with this gender
     * @return Gender option with the given identifier, or null if none exists
     */
    public static @Nullable PlayerGender getGender(@NotNull String identifier) {
        return GENDER_OPTIONS.get(identifier.toLowerCase(Locale.ROOT));
    }

    /**
     * Add a new gender option for the player.
     * Note: Using this method may be unadvised, as it may cause issues with configuration reloads.
     *
     * @param gender The gender to add
     */
    public static void addGenderOption(@NotNull PlayerGender gender) {
        GENDER_OPTIONS.put(gender.getIdentifier().toLowerCase(Locale.ROOT), gender);
    }

    /**
     * Remove a gender option for the player.
     * Note: Using this method may be unadvised, as it may cause issues with configuration reloads.
     *
     * @param identifier The configuration key identifying the gender
     */
    public static void removeGenderOption(@NotNull String identifier) {
        GENDER_OPTIONS.remove(identifier.toLowerCase(Locale.ROOT));
    }
}
