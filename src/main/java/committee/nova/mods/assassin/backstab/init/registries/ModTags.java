package committee.nova.mods.assassin.backstab.init.registries;

import committee.nova.mods.assassin.backstab.Const;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * ModTags
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/22 14:05
 */
public class ModTags {
    public static final TagKey<Item> BACK_STAB = ItemTags.create(new ResourceLocation(Const.MOD_ID, "backstab"));
}
