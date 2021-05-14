package dev.blizzardutils.user;

public class User {

    private String playerName;
    private String playerUUID;

    public User(String playerName, String playerUUID) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }
}