package dev.blizzardutils.hikari;

import java.util.Map;

public interface ICache<K> {

    void define(Map<String, Integer> cacheType, String playerName, int amount);

    void undefine(Map<String, Integer> cacheType, String playerName);

    boolean isDefined(Map<String, Integer> cacheType, String playerName);


}
