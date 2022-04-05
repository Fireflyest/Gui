package com.fireflyest.gui.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.fireflyest.gui.Gui;
import com.fireflyest.gui.api.ViewGuide;
import com.fireflyest.gui.api.ViewPage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Fireflyest
 * 2022/1/16 18:45
 */

public class ViewProtocol {

    private static ProtocolManager protocolManager;
    private static JavaPlugin plugin;
    private static ViewGuide viewGuide;

    private static final HashMap<String, PacketContainer> packets = new HashMap<>();

    private ViewProtocol(){
    }

    public static void initViewProtocol(){
        protocolManager = ProtocolLibrary.getProtocolManager();
        plugin = Gui.getPlugin();
        viewGuide = Gui.getViewGuide();

        // 监听
        createPacketListener();
    }

    public static void createPacketListener(){
        // 打开界面监听
        protocolManager.addPacketListener(
                new PacketAdapter(plugin,
                        ListenerPriority.NORMAL,
                        PacketType.Play.Server.WINDOW_ITEMS) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() != PacketType.Play.Server.WINDOW_ITEMS) return;

                        String playerName = event.getPlayer().getName();
                        ViewPage page = viewGuide.getUsingPage(playerName);
                        // 判断玩家是否正在浏览界面
                        if (page == null) return;

                        PacketContainer packet = event.getPacket();

                        // 判断是否更新玩家背包
                        if(packet.getIntegers().read(0) != 0 && !packets.containsKey(playerName)){
//                            System.out.println("ViewProtocol.onPacketSending");
                            
                            int size = page.getInventory().getSize();

                            // 放置按钮
                            List<ItemStack> itemStacks = packet.getItemListModifier().read(0);
                            for (Map.Entry<Integer, ItemStack> entry : page.getItemMap().entrySet()) {
                                itemStacks.set(entry.getKey(), entry.getValue());
                            }
                            // 删除背包的物品
                            Iterator<ItemStack> iterator = itemStacks.listIterator(size);
                            do {
                                iterator.next();
                                iterator.remove();
                            } while (iterator.hasNext());

                            packet.getItemListModifier().write(0, itemStacks);

                            // 存包
                            packets.put(playerName, packet);

                            sendItemsPacketAsynchronously(playerName);
                        }
                    }
                });
    }

    public static void sendItemsPacket(String playerName){
//        System.out.println("ViewProtocol.sendItemsPacket");
        ViewPage page = viewGuide.getUsingPage(playerName);
        PacketContainer packet = packets.get(playerName);
        if (packet != null && page != null) {
            List<ItemStack> itemStacks = packet.getItemListModifier().read(0);

            // 替换东西
            for (Map.Entry<Integer, ItemStack> entry : page.getItemMap().entrySet()) {
                itemStacks.set(entry.getKey(), entry.getValue());
            }

            packet.getItemListModifier().write(0, itemStacks);

            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                try {
                    protocolManager.sendServerPacket(player, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendItemsPacketAsynchronously(String playerName){
//        System.out.println("ViewProtocol.sendItemsPacketAsynchronously");
        new BukkitRunnable(){
            @Override
            public void run() {
                ViewPage page = viewGuide.getUsingPage(playerName);
                PacketContainer packet = packets.get(playerName);
                if (packet != null && page != null) {
                    // 替换东西
                    List<ItemStack> itemStacks = packet.getItemListModifier().read(0);
                    for (Map.Entry<Integer, ItemStack> entry : page.getItemMap().entrySet()) {
                        itemStacks.set(entry.getKey(), entry.getValue());
                    }

                    // 写入
                    packet.getItemListModifier().write(0, itemStacks);

                    Player player = Bukkit.getPlayer(playerName);
                    if (player != null) {
                        try {
                            protocolManager.sendServerPacket(player, packet);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.runTaskAsynchronously(Gui.getPlugin());
    }

    public static HashMap<String, PacketContainer> getPackets() {
        return packets;
    }
}
