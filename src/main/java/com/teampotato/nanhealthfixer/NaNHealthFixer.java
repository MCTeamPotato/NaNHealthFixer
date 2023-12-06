package com.teampotato.nanhealthfixer;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod(NaNHealthFixer.MOD_ID)
public class NaNHealthFixer {
    public static final String MOD_ID = "nanhealthfixer";
    public NaNHealthFixer() {
        final IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener((LivingDeathEvent event) -> validateHealth(event));
        bus.addListener((LivingEvent.LivingTickEvent event) -> validateHealth(event));
        bus.addListener((LivingHurtEvent event) -> {
            validateHealth(event);
            if (Float.isNaN(event.getAmount())) event.setCanceled(true);
        });
    }

    private void validateHealth(@NotNull LivingEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level.isClientSide) return;
        float health = entity.getHealth();
        if (Float.isNaN(health) || health < 0.0F) entity.setHealth(0.0F);
    }
}
