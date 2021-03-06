package com.fireflyest.gui.event;

import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Fireflyest
 * 2022/2/17 20:26
 */

public class ViewClickEvent extends InventoryInteractEvent {

    private static final HandlerList handlers = new HandlerList();

    private final ClickType click;
    private final int whichSlot;
    private final int rawSlot;
    private ItemStack current;

    public ViewClickEvent(@NotNull InventoryView view, @NotNull ClickType click, int slot, ItemStack current) {
        super(view);
        this.click = click;
        this.rawSlot = slot;
        this.whichSlot = view.convertSlot(slot);
        this.current = current;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public ItemStack getCurrentItem() {
        return this.current ;
    }

    public boolean isRightClick() {
        return this.click.isRightClick();
    }

    public boolean isLeftClick() {
        return this.click.isLeftClick();
    }

    public boolean isShiftClick() {
        return this.click.isShiftClick();
    }

    public void setCurrent(ItemStack current) {
        this.current = current;
    }

    @Nullable
    public Inventory getClickedInventory() {
        return this.getView().getInventory(this.rawSlot);
    }

    public int getSlot() {
        return this.whichSlot;
    }

    public int getRawSlot() {
        return this.rawSlot;
    }

    @NotNull
    public ClickType getClick() {
        return this.click;
    }

    @Override
    public String toString() {
        return "ViewClickEvent{" +
                "click=" + click +
                ", whichSlot=" + whichSlot +
                ", rawSlot=" + rawSlot +
                ", current=" + current +
                '}';
    }
}
