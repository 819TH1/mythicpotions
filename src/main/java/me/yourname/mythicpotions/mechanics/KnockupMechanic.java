package me.yourname.mythicpotions.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;
import me.yourname.mythicpotions.Main;

public class KnockupMechanic implements ITargetedEntitySkill {
    protected final double power;
    protected final int duration;

    public KnockupMechanic(MythicLineConfig config) {
        this.power = config.getDouble(new String[]{"power", "p"}, 0.75);
        this.duration = config.getInteger(new String[]{"duration", "d"}, 20);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (target.isLiving()) {
            LivingEntity entity = (LivingEntity) target.getBukkitEntity();

            // 1. 上方向に打ち上げる (Velocity)
            // 既存の横方向の慣性を少し残しつつ、垂直に飛ばす
            Vector currentVel = entity.getVelocity();
            entity.setVelocity(new Vector(currentVel.getX() * 0.2, power, currentVel.getZ() * 0.2));

            // 2. 滞空中の制御不能状態（LoLのノックアップ再現）
            new BukkitRunnable() {
                int elapsed = 0;
                @Override
                public void run() {
                    // 地面に着くか、死ぬか、時間が経てば終了
                    if (elapsed >= duration || entity.isDead() || (elapsed > 5 && entity.isOnGround())) {
                        this.cancel();
                        return;
                    }

                    // 空中で水平移動（WASDなど）を封じる
                    Vector v = entity.getVelocity();
                    entity.setVelocity(new Vector(0, v.getY(), 0));
                    
                    elapsed += 1;
                }
            }.runTaskTimer(JavaPlugin.getProvidingPlugin(Main.class), 1L, 1L);

            return SkillResult.SUCCESS;
        }
        return SkillResult.CONDITION_FAILED;
    }
}