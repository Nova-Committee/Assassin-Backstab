package committee.nova.mods.assassin.backstab.data.provider;

import committee.nova.mods.assassin.backstab.Const;
import committee.nova.mods.assassin.backstab.init.registries.ModDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Name: Avaritia-forge / ModDamageTypeTags
 * Author: cnlimiter
 * CreateTime: 2023/9/10 0:39
 * Description:
 */

public class ModDamageTypeTags extends TagsProvider<DamageType> {

    public ModDamageTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(output, Registries.DAMAGE_TYPE, future, Const.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(ModDamageTypes.BACK_STAB);
        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(ModDamageTypes.BACK_STAB);
    }
}
