package com.fireflyest.gui.view;

import com.fireflyest.gui.api.ViewPage;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TestPage implements ViewPage {
    @Override
    public @NotNull Map<Integer, ItemStack> getItemMap() {
        return null;
    }

    @Override
    public @NotNull Map<Integer, ItemStack> getButtonMap() {
        return null;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }


    @Override
    public String getTarget() {
        return null;
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
    public void setNext(ViewPage next) {

    }

    @Override
    public void setPre(ViewPage pre) {

    }

    @Override
    public void refreshPage() {

    }
}
