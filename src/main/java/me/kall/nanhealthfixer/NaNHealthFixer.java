package me.kall.nanhealthfixer;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.NotNull;

@Mod(NaNHealthFixer.MOD_ID)
public final class NaNHealthFixer {
    public static final String MOD_ID = "nanhealthfixer";

    public NaNHealthFixer(IEventBus modBus, Dist dist, ModContainer container) {
        final IEventBus bus = NeoForge.EVENT_BUS;
        bus.addListener((LivingDeathEvent event) -> validateHealth(event.getEntity()));
        bus.addListener((EntityTickEvent.Pre event) -> {
            if (event.getEntity() instanceof LivingEntity entity) {
                validateHealth(entity);
            }
        });
        bus.addListener((LivingDamageEvent.Pre event) -> validateHealth(event.getEntity()));
        bus.addListener((LivingDamageEvent.Post event) -> validateHealth(event.getEntity()));
    }

    private void validateHealth(@NotNull LivingEntity entity) {
        if (entity.level().isClientSide()) return;
        float health = entity.getHealth();
        if (Float.isNaN(health) || health < 0.0F) entity.setHealth(0.0F);
    }
}
