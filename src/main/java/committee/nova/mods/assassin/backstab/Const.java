package committee.nova.mods.assassin.backstab;

import com.mojang.logging.LogUtils;
import committee.nova.mods.assassin.backstab.init.config.ModConfig;
import committee.nova.mods.assassin.backstab.init.registries.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

/**
 * Const
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/22 13:55
 */
public class Const {

    public static final String MOD_ID = "assassin_backstab";
    public static final Logger LOGGER = LogUtils.getLogger();


    public static boolean isValidItem(ItemStack stack) {
        return ModConfig.backstabItemsAll || ModConfig.itemsData.containsKey(ForgeRegistries.ITEMS.getKey(stack.getItem()).toString()) || stack.is(ModTags.BACK_STAB);
    }

    public static boolean isValidEntity(LivingEntity entity) {
        return (entity instanceof Player) ? ModConfig.backstabPlayers : !ModConfig.entities.contains(entity.getEncodeId());
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
