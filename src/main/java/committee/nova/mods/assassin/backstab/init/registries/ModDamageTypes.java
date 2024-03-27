package committee.nova.mods.assassin.backstab.init.registries;

import committee.nova.mods.assassin.backstab.Const;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Author cnlimiter
 * CreateTime 2023/6/15 0:39
 * Name ModDamageTypes
 * Description
 */

public class ModDamageTypes {

    public static final ResourceKey<DamageType> BACK_STAB = createKey("backstab");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(BACK_STAB, new DamageType("backstab", DamageScaling.ALWAYS, 0.1F));
    }

    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Const.MOD_ID, name));
    }

    public static DamageSource causeDamage(LivingEntity attacker) {
        return new ModDamageSource(attacker.getCommandSenderWorld().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(BACK_STAB), attacker);
    }


    public static class ModDamageSource extends DamageSource {
        public ModDamageSource(Holder<DamageType> damageTypeHolder, @Nullable Entity entity1, @Nullable Entity entity2, @Nullable Vec3 from) {
            super(damageTypeHolder, entity1, entity2, from);
        }

        public ModDamageSource(Holder<DamageType> damageTypeHolder, @Nullable Entity entity1, @Nullable Entity entity2) {
            super(damageTypeHolder, entity1, entity2);
        }

        public ModDamageSource(Holder<DamageType> damageTypeHolder, Vec3 from) {
            super(damageTypeHolder, from);
        }

        public ModDamageSource(Holder<DamageType> damageTypeHolder, @Nullable Entity entity) {
            super(damageTypeHolder, entity);
        }

        public ModDamageSource(Holder<DamageType> p_270475_) {
            super(p_270475_);
        }

        @Override
        public @NotNull Component getLocalizedDeathMessage(LivingEntity attacked) {
            LivingEntity livingentity = attacked.getKillCredit();
            String s = "death.attack." + this.getMsgId();
            String s1 = "death.attack." + this.getMsgId() + ".item";
            return livingentity != null ? Component.translatable(s1, attacked.getDisplayName(), livingentity.getDisplayName()) : Component.translatable(s, attacked.getDisplayName());
        }
    }
}
