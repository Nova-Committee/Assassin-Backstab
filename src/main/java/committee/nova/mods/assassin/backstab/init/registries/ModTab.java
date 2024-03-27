package committee.nova.mods.assassin.backstab.init.registries;

import committee.nova.mods.assassin.backstab.Const;
import committee.nova.mods.assassin.backstab.common.items.DaggerItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * ModItems
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/22 13:54
 */
public class ModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Const.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("back_stab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("group.assassin_backstab"))
            .icon(() -> ModItems.netheritedagger.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> {
                    output.accept(itemRegistryObject.get().asItem());
                });
            }).build());
}
