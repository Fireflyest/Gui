package com.fireflyest.gui;

import com.fireflyest.gui.api.ViewGuide;
import com.fireflyest.gui.core.ViewGuideImpl;
import com.fireflyest.gui.listener.ViewEventListener;
import com.fireflyest.gui.protocol.ViewProtocol;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Fireflyest
 * 2022/1/18 20:48
 */

public class Gui extends JavaPlugin {

    public static final String ERROR_VIEW = "error";

    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin(){
        return plugin;
    }

    private static ViewGuideImpl viewGuide;

    public static ViewGuideImpl getViewGuide() {
        return viewGuide;
    }

    @Override
    public void onEnable() {
        plugin = this;

        // 新建导航
        viewGuide = new ViewGuideImpl();

        // 注册监听
        this.getServer().getPluginManager().registerEvents( new ViewEventListener(), this);

        // 注册服务
        this.getServer().getServicesManager().register(ViewGuide.class, viewGuide, plugin, ServicePriority.Normal);

        // 初始化监听
        ViewProtocol.initViewProtocol();
    }

    @Override
    public void onDisable() {

    }
}
