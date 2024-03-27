package committee.nova.mods.assassin.backstab.data;

import committee.nova.mods.assassin.backstab.data.provider.ModBlockTagsProvider;
import committee.nova.mods.assassin.backstab.data.provider.ModItemTagsProvider;
import committee.nova.mods.assassin.backstab.data.provider.ModRegistries;
import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ModDataGen
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/24 1:39
 */
public class ModDataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var registries = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var pack = generator.getVanillaPack(true);

        var blockTagsProvider = pack
                .addProvider(packOutput -> new ModBlockTagsProvider(packOutput, registries, existingFileHelper));

        pack.addProvider(
                packOutput -> new ModItemTagsProvider(packOutput, registries, blockTagsProvider.contentsGetter(),
                        existingFileHelper
                ));

        ModRegistries.addProviders(generator, registries, existingFileHelper);


        pack.addProvider( packOutput -> new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Assassin BackStab Resources"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                Arrays.stream(PackType.values()).collect(Collectors.toMap(Function.identity(), DetectedVersion.BUILT_IN::getPackVersion)))));
    }
}
