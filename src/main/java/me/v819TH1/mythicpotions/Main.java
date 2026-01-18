package me.v819TH1.mythicpotions;

import me.v819TH1.mythicpotions.mechanics.CharmMechanic;
import me.v819TH1.mythicpotions.mechanics.KnockupMechanic;
import me.v819TH1.mythicpotions.mechanics.LifestealMechanic;
import io.lumine.mythic.api.skills.ISkillMechanic;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // イベントリスナーの登録
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("MythicPotions has been enabled!");
    }

    // --- MythicMobsのカスタムメカニックを読み込む設定 ---
    @EventHandler
    public void onMythicMechanicLoad(MythicMechanicLoadEvent event) {
        String mechanicName = event.getMechanicName().toLowerCase();

        switch (mechanicName) {
            case "knockup":
                event.register(new KnockupMechanic(event.getConfig()));
                break;
            case "charm":
                event.register(new CharmMechanic(event.getConfig()));
                break;
            case "lifesteal":
                // ここを修正：new LifestealMechanic(...) の後に .register(event.getContainer()) を付ける
                new LifestealMechanic(event.getConfig()); 
                // もし上の行でエラーが出る場合は、以下のように「event.register」の中にキャストして入れます
                event.register((ISkillMechanic) new LifestealMechanic(event.getConfig()));
                break;
        }
    }

    // --- 吸血（Lifesteal）の実体：ダメージを与えた時に回復する処理 ---
    @EventHandler
    public void onLifestealDamage(EntityDamageByEntityEvent event) {
        // 攻撃者が生きているエンティティ（プレイヤーやMob）か確認
        if (!(event.getDamager() instanceof LivingEntity damager)) return;

        // 攻撃者に「Lifesteal」タグがついているか確認
        if (damager.getScoreboardTags().contains("Lifesteal")) {
            // 実際に与えた最終ダメージを取得
            double damage = event.getFinalDamage();
            // ダメージの50%を回復量にする
            double healAmount = damage * 0.5;

            // 現在の体力に回復分を足す（最大体力を超えないようにする）
            double currentHealth = damager.getHealth();
            @SuppressWarnings("deprecation")
            double maxHealth = damager.getMaxHealth();
            damager.setHealth(Math.min(maxHealth, currentHealth + healAmount));

            // 回復したことがわかるようにハートのパーティクルを出す
            damager.getWorld().spawnParticle(Particle.HEART, damager.getLocation().add(0, 1.5, 0), 5);
        }
    }
}