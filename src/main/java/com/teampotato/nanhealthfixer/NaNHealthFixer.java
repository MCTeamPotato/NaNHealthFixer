package com.teampotato.nanhealthfixer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "nanhealthfixer", useMetadata = true)
public class NaNHealthFixer {
    public NaNHealthFixer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        validateHealth(event);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        validateHealth(event);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        validateHealth(event);
        if (Float.isNaN(event.getAmount())) event.setCanceled(true);
    }

    private void validateHealth(LivingEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        float health = entity.getHealth();
        if (Float.isNaN(health) || health < 0.0F) entity.setHealth(0.0F);
    }
}
