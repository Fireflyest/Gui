package com.fireflyest.gui.listener;

import com.fireflyest.gui.Gui;
import com.fireflyest.gui.api.ViewGuide;
import com.fireflyest.gui.api.ViewPage;
import com.fireflyest.gui.event.ViewClickEvent;
import com.fireflyest.gui.protocol.ViewProtocol;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fireflyest
 * 2022/2/17 20:25
 */

public class ViewEventListener implements Listener {

    public final ViewGuide guide;

    public ViewEventListener(){
        this.guide = Gui.getViewGuide();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        String playerName = event.getWhoClicked().getName();
        if (guide.isViewer(playerName)){
            // 取消点击
            event.setCancelled(true);
            // 获取点击信息
            ViewPage page = guide.getUsingPage(playerName);
            // 物品
            Map<Integer, ItemStack> itemMap = new HashMap<>();
            itemMap.putAll(page.getItemMap());
            itemMap.putAll(page.getButtonMap());

            ItemStack clickItem = itemMap.get(event.getRawSlot());
            // 事件
            ViewClickEvent clickEvent = new ViewClickEvent(event.getView(), event.getClick(), event.getSlot(), clickItem);
            Bukkit.getPluginManager().callEvent(clickEvent);
            //
            guide.refreshPage(playerName);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        String playerName = event.getWhoClicked().getName();
        if (guide.isViewer(playerName)){
            // 取消点击
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        String playerName = event.getPlayer().getName();
        if (guide.isViewer(playerName)){
            // 取消点击
            if(guide.getUsingPage(playerName) != null) {
                guide.closeView(playerName);
            }
            ViewProtocol.getPackets().remove(playerName);
        }
    }

}
