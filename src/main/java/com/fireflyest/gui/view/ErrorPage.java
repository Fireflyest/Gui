package com.fireflyest.gui.view;

import com.fireflyest.gui.api.ViewPage;
import com.fireflyest.gui.util.ItemUtils;
import com.fireflyest.gui.view.ErrorView;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Fireflyest
 * 2022/2/16 17:24
 */

public class ErrorPage implements ViewPage {

    private final Inventory inventory;

    private final Map<Integer, ItemStack> itemMap = new HashMap<>();

    public ErrorPage() {
        this.inventory = Bukkit.createInventory(null, 9, "页面未找到(NON_FOUND)");

        this.refreshPage();
    }

    @Override
    public @NotNull Map<Integer, ItemStack> getItemMap() {
        Map<Integer, ItemStack> itemStackMap = new HashMap<>(itemMap);
        itemStackMap.put(0, new ItemStack(Material.STONE));
        return itemStackMap;
    }

    @Override
    public @NotNull Map<Integer, ItemStack> getButtonMap() {
        return new HashMap<>(itemMap);
    }

    @Override
    @NotNull
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String getTarget() {
        return ErrorView.NOT_FOUND;
    }

    @Override
    public int getPage() {
        return 0;
    }

    @Override
    public ViewPage getNext() {
        return null;
    }

    @Override
    public ViewPage getPre() {
        return null;
    }

    @Override
    public void setPre(ViewPage pre) { }

    @Override
    public void setNext(ViewPage pre) {
    }

    @Override
    public void refreshPage() {
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemUtils.setDisplayName(close, "§c关闭");
        ItemUtils.addLore(close, "§f点击关闭界面");

        itemMap.put(8, close);
    }
}
