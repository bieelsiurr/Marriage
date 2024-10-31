package es.minehit.marriage.internal;

import es.minehit.marriage.PlayerGender;

public class MarriageGender implements PlayerGender {
    private final String identifier;
    private final String displayName;
    private final String chatPrefix;

    public MarriageGender(String identifier, String displayName, String chatPrefix) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.chatPrefix = chatPrefix;
    }

    @Override
    public boolean isMale() {
        return identifier.equalsIgnoreCase("male");
    }

    @Override
    public boolean isFemale() {
        return identifier.equalsIgnoreCase("female");
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getChatPrefix() {
        return chatPrefix;
    }
}
