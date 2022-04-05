package com.fireflyest.gui.api;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface ViewPage {

    Map<Integer, ItemStack> getItemMap();

    Map<Integer, ItemStack> getButtonMap();

    Inventory getInventory();

    String getTarget();

    int getPage();

    ViewPage getNext();

    ViewPage getPre();

    void setPre(ViewPage pre);

    void setNext(ViewPage pre);

    void refreshPage();

}
