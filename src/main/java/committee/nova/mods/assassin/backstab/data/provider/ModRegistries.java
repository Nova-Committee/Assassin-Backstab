package committee.nova.mods.assassin.backstab.data.provider;

import committee.nova.mods.assassin.backstab.Const;
import committee.nova.mods.assassin.backstab.init.registries.ModDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * ModRegistries
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/22 14:20
 */
public class ModRegistries extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap);

    private ModRegistries(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future, BUILDER, Set.of("minecraft", Const.MOD_ID));
    }

    public static void addProviders(DataGenerator generator, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        var pack = generator.getVanillaPack(true);

        pack.addProvider(pOutput -> new ModRegistries(pOutput, provider));
        pack.addProvider(pOutput -> new ModDamageTypeTags(pOutput, provider.thenApply(r -> append(r, BUILDER)), helper));
    }

    private static HolderLookup.Provider append(HolderLookup.Provider original, RegistrySetBuilder builder) {
        return builder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original);
    }
}
