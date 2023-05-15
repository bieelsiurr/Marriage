package com.lenis0012.bukkit.marriage2.internal.data;

import com.lenis0012.bukkit.marriage2.*;
import com.lenis0012.bukkit.marriage2.config.Settings;
import com.lenis0012.bukkit.marriage2.events.PlayerDivorceEvent;
import com.lenis0012.bukkit.marriage2.internal.MarriageCore;
import com.lenis0012.bukkit.marriage2.internal.MarriagePlugin;
import com.lenis0012.bukkit.marriage2.misc.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MarriagePlayer implements MPlayer {
    private final Cooldown<UUID> requests;
    private final UUID uuid;
    private String lastName;
    private Relationship marriage;
    private PlayerGender gender = null;
    private boolean inChat;
    private boolean chatSpy;
    private boolean priest;
    private long lastLogin;
    private long lastLogout;

    public MarriagePlayer(UUID uuid, ResultSet data) throws SQLException {
        this.uuid = uuid;
        if(data.next()) {
            this.lastName = data.getString("last_name");
            if (data.getString("gender") != null) {
                this.gender = Genders.getGender(data.getString("gender").toLowerCase(Locale.ROOT));
            }
            this.priest = data.getBoolean("priest");
            this.lastLogout = data.getLong("lastlogin");
        }
        this.lastLogin = System.currentTimeMillis();
        this.requests = new Cooldown<>(Settings.REQUEST_EXPIRY.value(), TimeUnit.SECONDS);
    }

    public void addMarriage(MarriageData data) {
        this.marriage = data;
    }

    void save(PreparedStatement ps) throws SQLException {
        ps.setString(1, uuid.toString());
        ps.setString(2, lastName);
        ps.setString(3, gender != null ? gender.getIdentifier() : null);
        ps.setBoolean(4, priest);
        ps.setLong(5, System.currentTimeMillis());
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public void requestMarriage(UUID from) {
        requests.set(from);
    }

    @Override
    public boolean isMarriageRequested(UUID from) {
        return requests.isCached(from);
    }

    @Override
    public Optional<PlayerGender> getChosenGender() {
        return Optional.ofNullable(gender);
    }

    @Override
    public void setChosenGender(@Nullable PlayerGender gender) {
        this.gender = gender;
        ((MarriageCore) MarriagePlugin.getCore()).savePlayer(this);
    }

    @Override
    public Relationship getMarriage() {
        return marriage;
    }

    @Nullable
    @Override
    public Relationship getActiveRelationship() {
        return marriage;
    }

    @Override
    public boolean isMarried() {
        return marriage != null;
    }

    @Override
    public boolean isInChat() {
        return inChat;
    }

    @Override
    public void setInChat(boolean inChat) {
        this.inChat = inChat;
    }

    @Override
    public MPlayer getPartner() {
        MarriageCore core = MarriagePlugin.getCore();
        if(marriage != null) {
            UUID id = uuid.equals(marriage.getPlayer1Id()) ? marriage.getPllayer2Id() : marriage.getPlayer1Id();
            return core.getMPlayer(id);
        }

        return null;
    }

    @Override
    public void divorce() {
        if(marriage == null) {
            return;
        }

        PlayerDivorceEvent event = new PlayerDivorceEvent(this, marriage);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled()) {
            return;
        }

        ((MarriageCore) MarriagePlugin.getCore()).removeMarriage(marriage);
        MarriagePlayer partner = (MarriagePlayer) getPartner();
        partner.marriage = null;
        this.marriage = null;
    }

    @Override
    public boolean isPriest() {
        return priest;
    }

    @Override
    public void setPriest(boolean priest) {
        this.priest = priest;
    }

    @Override
    public long getLastLogin() {
        return lastLogin;
    }

    @Override
    public long getLastLogout() {
        return lastLogout;
    }

    @Override
    public boolean isChatSpy() {
        return chatSpy;
    }

    @Override
    public void setChatSpy(boolean chatSpy) {
        this.chatSpy = chatSpy;
    }
}