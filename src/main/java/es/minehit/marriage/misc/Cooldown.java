package es.minehit.marriage.misc;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class Cooldown<T> {
    private final Cache<T, Boolean> cache;

    public Cooldown(long cooldownTime, TimeUnit unit) {
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(cooldownTime, unit).build();
    }

    /**
     * Check whether a key is in cool-down.
     *
     * @param key to check
     * @return True if in cooldown, False otherwise
     */
    public boolean isCached(T key) {
        return cache.getIfPresent(key) != null;
    }

    /**
     * Set key in cool-down
     *
     * @param key to set in cool-down
     */
    public void set(T key) {
        cache.put(key, false);
    }

    /**
     * Simple in-line check method to check if someone is in cool-down, if not set them to be.
     *
     * @param key to check
     * @return Whether key is in cool-down or not
     */
    public boolean performCheck(T key) {
        if(isCached(key)) return false;
        set(key);
        return true;
    }
}
