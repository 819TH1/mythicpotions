package me.v819TH1.mythicpotions.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import org.bukkit.entity.Entity;

public class LifestealMechanic implements ITargetedEntityMechanic {
    protected final int duration;

    public LifestealMechanic(MythicLineConfig config) {
        // スキル側で持続時間を設定できるようにする（デフォルト5秒 = 100tick）
        this.duration = config.getInteger(new String[]{"duration", "d"}, 100);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        Entity bukkitEntity = target.getBukkitEntity();
        
        // 「Lifesteal」というタグを付ける
        bukkitEntity.addScoreboardTag("Lifesteal");

        // 指定した時間（duration）が経過したらタグを消す
        org.bukkit.Bukkit.getScheduler().runTaskLater(org.bukkit.plugin.java.JavaPlugin.getProvidingPlugin(LifestealMechanic.class), () -> {
            bukkitEntity.removeScoreboardTag("Lifesteal");
        }, (long) duration);

        return SkillResult.SUCCESS;
    }
}