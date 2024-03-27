package committee.nova.mods.assassin.backstab.data.provider;

import committee.nova.mods.assassin.backstab.Const;
import committee.nova.mods.assassin.backstab.init.registries.ModItems;
import committee.nova.mods.assassin.backstab.init.registries.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * ModItemTagsProvider
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/24 23:39
 */
public class ModItemTagsProvider extends ItemTagsProvider {


    public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Const.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        ModItems.ITEMS.getEntries().forEach(item -> tag(ModTags.BACK_STAB).add(item.get()));
    }
}
