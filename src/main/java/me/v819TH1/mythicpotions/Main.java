package me.v819TH1.mythicpotions;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import me.v819TH1.mythicpotions.mechanics.CharmMechanic;
import me.v819TH1.mythicpotions.mechanics.KnockupMechanic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // イベント登録
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("mythicpotions has been enabled!");
    }

    @EventHandler
public void onMechanicLoad(MythicMechanicLoadEvent event) {
    if (event.getMechanicName().equalsIgnoreCase("knockup")) {
        event.register(new KnockupMechanic(event.getConfig()));
    }
    // ここを追加！
    if (event.getMechanicName().equalsIgnoreCase("charm")) {
        event.register(new CharmMechanic(event.getConfig()));
    }
}
}