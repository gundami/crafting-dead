package com.craftingdead.mod.entity;

import com.craftingdead.mod.CraftingDead;
import com.craftingdead.mod.entity.monster.AdvancedZombieEntity;
import com.craftingdead.mod.entity.monster.FastZombieEntity;
import com.craftingdead.mod.entity.monster.TankZombieEntity;
import com.craftingdead.mod.entity.monster.WeakZombieEntity;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class ModEntityTypes {

  private static final List<EntityType<?>> toRegister = new ArrayList<>();

  public static EntityType<CorpseEntity> corpse;
  public static EntityType<AdvancedZombieEntity> advancedZombie;
  public static EntityType<FastZombieEntity> fastZombie;
  public static EntityType<TankZombieEntity> tankZombie;
  public static EntityType<WeakZombieEntity> weakZombie;

  public static void initialize() {
    corpse = add("corpse",
        EntityType.Builder.<CorpseEntity>create(CorpseEntity::new, EntityClassification.MISC)
            .setTrackingRange(64)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(false)
            .setCustomClientFactory(CorpseEntity::new));

    advancedZombie = add("advanced_zombie", EntityType.Builder
        .<AdvancedZombieEntity>create(AdvancedZombieEntity::new, EntityClassification.MONSTER)
        .setTrackingRange(64)
        .setUpdateInterval(3)
        .setShouldReceiveVelocityUpdates(false));

    fastZombie = add("fast_zombie", EntityType.Builder
        .<FastZombieEntity>create(FastZombieEntity::new, EntityClassification.MONSTER)
        .setTrackingRange(64)
        .setUpdateInterval(3)
        .setShouldReceiveVelocityUpdates(false));

    tankZombie = add("tank_zombie", EntityType.Builder
        .<TankZombieEntity>create(TankZombieEntity::new, EntityClassification.MONSTER)
        .setTrackingRange(64)
        .setUpdateInterval(3)
        .setShouldReceiveVelocityUpdates(false));

    weakZombie = add("weak_zombie", EntityType.Builder
        .<WeakZombieEntity>create(WeakZombieEntity::new, EntityClassification.MONSTER)
        .setTrackingRange(64)
        .setUpdateInterval(3)
        .setShouldReceiveVelocityUpdates(false));
  }

  public static void register(RegistryEvent.Register<EntityType<?>> event) {
    toRegister.forEach(event.getRegistry()::register);
  }

  private static <T extends Entity> EntityType<T> add(String registryName,
      EntityType.Builder<T> builder) {
    ResourceLocation resourceLocation = new ResourceLocation(CraftingDead.ID, registryName);
    EntityType<T> entityType = builder.build(resourceLocation.toString());
    entityType.setRegistryName(resourceLocation);
    toRegister.add(entityType);
    return entityType;
  }
}