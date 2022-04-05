package com.fireflyest.gui.core;

import com.fireflyest.gui.Gui;
import com.fireflyest.gui.api.View;
import com.fireflyest.gui.api.ViewGuide;
import com.fireflyest.gui.api.ViewPage;
import com.fireflyest.gui.protocol.ViewProtocol;
import com.fireflyest.gui.view.ErrorView;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Fireflyest
 * 2022/2/15 12:04
 */

public class ViewGuideImpl implements ViewGuide {

    // 所有界面
    public static final Map<String, View<? extends ViewPage>> viewMap = new HashMap<>();

    // 玩家正在浏览的页面
    public static final Map<String, ViewPage> viewUsing = new HashMap<>();

    public static final ErrorView errorView = new ErrorView();

    public ViewGuideImpl() {
        // 错误界面
        viewMap.put(Gui.ERROR_VIEW, errorView);
    }

    @Override
    public void addView(String viewName, View<? extends ViewPage> view) {
        viewMap.put(viewName, view);
    }

    @Override
    public void closeView(String playerName) {
        viewUsing.remove(playerName);
    }

    @Override
    public ViewPage getUsingPage(String playerName) {
        return viewUsing.getOrDefault(playerName, errorView.getFirstPage(ErrorView.NOT_FOUND));
    }

    @Override
    public void nextPage(Player player) {
        String playerName = player.getName();
        ViewPage page = this.getUsingPage(playerName).getNext();
        if (page != null) {
            player.closeInventory();
            viewUsing.put(playerName, page);
            player.openInventory(page.getInventory());
        }
    }

    @Override
    public void prePage(Player player) {
        String playerName = player.getName();
        ViewPage page = this.getUsingPage(playerName).getPre();
        if (page != null) {
            player.closeInventory();
            viewUsing.put(playerName, page);
            player.openInventory(page.getInventory());
        }
    }

    @Override
    public void openView(Player player, String viewName, String target) {
        player.closeInventory();
        String playerName = player.getName();
        // 设置玩家正在浏览的界面
        View<? extends ViewPage> view = viewMap.getOrDefault(viewName, errorView);
        viewUsing.put(playerName, view.getFirstPage(target));
        // 打开容器
        ViewPage page = viewUsing.get(playerName);
        if (page == null) {
            page = errorView.getFirstPage(ErrorView.NOT_FOUND);
        }
        player.openInventory(page.getInventory());
    }

    @Override
    public void refreshPage(String... playerNames) {
        for (String playerName : playerNames) {
            // 获取刷新页面
            ViewPage page = this.getUsingPage(playerName);

            if (page != null) {
                // 刷新物品
                page.refreshPage();
                // 发包
                ViewProtocol.sendItemsPacketAsynchronously(playerName);
            }
        }
    }

    @Override
    public Set<String> getViewers() {
        return viewUsing.keySet();
    }

    @Override
    public boolean isViewer(String playerName) {
        return viewUsing.containsKey(playerName) && viewUsing.get(playerName) != errorView.getFirstPage(ErrorView.NOT_FOUND);
    }
}
