package com.fireflyest.gui.api;

import org.bukkit.entity.Player;

import java.util.Set;

public interface ViewGuide {

    void addView(String viewName, View<? extends ViewPage> view);

    void closeView(String playerName);

    void openView(Player player, String viewName, String target);

    ViewPage getUsingPage(String playerName);

    void nextPage(Player player);

    void prePage(Player player);

    void refreshPage(String... playerNames);

    Set<String> getViewers();

    boolean isViewer(String playerName);

}
