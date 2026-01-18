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

public class CharmMechanic implements ITargetedEntitySkill {
    protected final int duration;
    protected final double speed;

    public CharmMechanic(MythicLineConfig config) {
        // duration: 効果時間(ticks), speed: 引き寄せ速度(0.1〜0.2が適正)
        this.duration = config.getInteger(new String[]{"duration", "d"}, 40);
        this.speed = config.getDouble(new String[]{"speed", "s"}, 0.12);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (target.isLiving()) {
            LivingEntity victim = (LivingEntity) target.getBukkitEntity();
            LivingEntity caster = (LivingEntity) data.getCaster().getEntity().getBukkitEntity();

            new BukkitRunnable() {
                int elapsed = 0;

                @Override
                public void run() {
                    if (elapsed >= duration || victim.isDead() || caster.isDead()) {
                        this.cancel();
                        return;
                    }

                    // キャスターへの方向を計算
                    Vector direction = caster.getLocation().toVector().subtract(victim.getLocation().toVector()).normalize();
                    
                    // 垂直方向の重力は維持しつつ、水平方向に引き寄せる
                    Vector velocity = direction.multiply(speed);
                    velocity.setY(victim.getVelocity().getY()); 
                    
                    victim.setVelocity(velocity);

                    // 1.21のパーティクル演出（ハート）
                    if (elapsed % 5 == 0) {
                        victim.getWorld().spawnParticle(org.bukkit.Particle.HEART, victim.getEyeLocation().add(0, 0.5, 0), 1, 0.2, 0.2, 0.2, 0.05);
                    }

                    elapsed++;
                }
            }.runTaskTimer(JavaPlugin.getProvidingPlugin(Main.class), 0L, 1L);

            return SkillResult.SUCCESS;
        }
        return SkillResult.CONDITION_FAILED;
    }
}