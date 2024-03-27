package committee.nova.mods.assassin.backstab;

import committee.nova.mods.assassin.backstab.data.ModDataGen;
import committee.nova.mods.assassin.backstab.init.config.ModConfig;
import committee.nova.mods.assassin.backstab.init.registries.ModItems;
import committee.nova.mods.assassin.backstab.init.registries.ModTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Const.MOD_ID)
public class AssassinBackStab {

    public AssassinBackStab() {
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.mainSpec);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(ModDataGen::gatherData);

        ModItems.ITEMS.register(modEventBus);
        ModTab.CREATIVE_MODE_TABS.register(modEventBus);

    }



}
