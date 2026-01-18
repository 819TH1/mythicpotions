package me.v819TH1.mythicpotions.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;

public interface ITargetedEntityMechanic {

    SkillResult castAtEntity(SkillMetadata data, AbstractEntity target);

}
