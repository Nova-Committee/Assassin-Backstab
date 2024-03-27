package committee.nova.mods.assassin.backstab.init.registries;

import committee.nova.mods.assassin.backstab.Const;
import committee.nova.mods.assassin.backstab.common.items.DaggerItem;
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
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Const.MOD_ID);

    public static final RegistryObject<DaggerItem> wooddagger = ITEMS.register("wood_dagger", () -> new DaggerItem(Tiers.WOOD, 2, new Item.Properties()));
    public static final RegistryObject<DaggerItem> stonedagger = ITEMS.register("stone_dagger", () -> new DaggerItem(Tiers.STONE, 3, new Item.Properties()));
    public static final RegistryObject<DaggerItem> irondagger = ITEMS.register("iron_dagger", () -> new DaggerItem(Tiers.IRON, 4, new Item.Properties()));
    public static final RegistryObject<DaggerItem> golddagger = ITEMS.register("gold_dagger", () -> new DaggerItem(Tiers.GOLD, 2, new Item.Properties()));
    public static final RegistryObject<DaggerItem> diamonddagger = ITEMS.register("diamond_dagger", () -> new DaggerItem(Tiers.DIAMOND, 5, new Item.Properties()));
    public static final RegistryObject<DaggerItem> netheritedagger = ITEMS.register("netherite_dagger", () -> new DaggerItem(Tiers.NETHERITE, 6, new Item.Properties()));

}
