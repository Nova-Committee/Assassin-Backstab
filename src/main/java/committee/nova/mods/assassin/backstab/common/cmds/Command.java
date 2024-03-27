package committee.nova.mods.assassin.backstab.common.cmds;

import committee.nova.mods.assassin.backstab.Const;
import committee.nova.mods.assassin.backstab.init.config.ModConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.*;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.ForgeRegistries;

public class Command {
	
	@SuppressWarnings("unused")
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
		dispatcher.register(Commands.literal(Const.MOD_ID).requires(context -> context.hasPermission(context.getServer().getOperatorUserPermissionLevel())).then(
			Commands.literal("config").then(
					Commands.literal("bonus_damage").then(
							Commands.argument("damage", DoubleArgumentType.doubleArg(0.0, 1000.0))
							.executes(context -> {
								ModConfig.MAIN.specBonus.set(DoubleArgumentType.getDouble(context, "damage"));
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("Bonus damage on backstab with fully charged attack is set to " + ModConfig.backstabBonus), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("Bonus damage on backstab with fully charged attack is currently " + ModConfig.backstabBonus), false);
						return 0;
					})
			).then(
					Commands.literal("damage_multiplier").then(
							Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.0, 1000.0))
							.executes(context -> {
								ModConfig.MAIN.specMultiplier.set(DoubleArgumentType.getDouble(context, "multiplier"));
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("Damage multiplier on backstab is set to " + ModConfig.backstabMultiplier), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("Damage multiplier on backstab is currently " + ModConfig.backstabMultiplier), false);
						return 0;
					})
			).then(
					Commands.literal("degrees").then(
							Commands.argument("degrees", DoubleArgumentType.doubleArg(0.0, 360.0))
							.executes(context -> {
								ModConfig.MAIN.specDegrees.set(DoubleArgumentType.getDouble(context, "degrees"));
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("Degrees to backstab is set to " + ModConfig.backstabDegrees), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("Degrees to backstab is currently " + ModConfig.backstabDegrees), false);
						return 0;
					})
			).then(
					Commands.literal("requires_sneaking").then(
							Commands.argument("true/false", BoolArgumentType.bool())
							.executes(context -> {
								ModConfig.MAIN.specSneaking.set(BoolArgumentType.getBool(context, "true/false"));
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("Sneaking required to backstab? " + ModConfig.backstabSneaking), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("Sneaking required backstab? " + ModConfig.backstabSneaking), false);
						return 0;
					})
			).then(
					Commands.literal("bypass_armor").then(
							Commands.argument("true/false", BoolArgumentType.bool())
							.executes(context -> {
								ModConfig.MAIN.specBypassArmor.set(BoolArgumentType.getBool(context, "true/false"));
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("Backstab damage bypass armor? " + ModConfig.backstabBypassArmor), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("Backstab damage bypass armor? " + ModConfig.backstabSneaking), false);
						return 0;
					})
			).then(
					Commands.literal("items_whitelist").then(
						Commands.literal("add").then(
								Commands.argument("item_id", ItemArgument.item(buildContext))
								.executes(context -> {
									String id = ForgeRegistries.ITEMS.getKey(ItemArgument.getItem(context, "item_id").getItem()).toString();
									if (!ModConfig.itemsData.containsKey(id)) {
										ModConfig.items.add(id);
										ModConfig.MAIN.specItems.set(ModConfig.items);
										context.getSource().sendSuccess( () ->Component.translatable("ItemID " + id + " can now backstab"), true);
									} else {
										context.getSource().sendSuccess( () ->Component.translatable("ItemID " + id + " can already backstab"), true);
									}
									return 0;
								}).then(Commands.argument("multiplier", DoubleArgumentType.doubleArg(0.0, 1000.0))
									.executes(context -> {
										String id = ForgeRegistries.ITEMS.getKey(ItemArgument.getItem(context, "item_id").getItem()).toString();
										if (ModConfig.itemsData.containsKey(id)) {
											ModConfig.items.removeIf(item -> item.startsWith(id));
											context.getSource().sendSuccess( () ->Component.translatable("Replacing ItemID " + id + "'s data"), true);
										}
										ModConfig.items.add(id + "," + DoubleArgumentType.getDouble(context, "multiplier"));
										ModConfig.MAIN.specItems.set(ModConfig.items);
										ModConfig.bakeConfig();
										context.getSource().sendSuccess( () ->Component.translatable("ItemID " + id + " can now backstab"), true);
										return 0;
								}).then(Commands.argument("bonus", DoubleArgumentType.doubleArg(0.0, 1000.0))
									.executes(context -> {
										String id = ForgeRegistries.ITEMS.getKey(ItemArgument.getItem(context, "item_id").getItem()).toString();
										if (ModConfig.itemsData.containsKey(id)) {
											ModConfig.items.removeIf(item -> item.startsWith(id));
											context.getSource().sendSuccess( () ->Component.translatable("Replacing ItemID " + id + "'s data"), true);
										}
										ModConfig.items.add(id + "," + DoubleArgumentType.getDouble(context, "multiplier") + "," + DoubleArgumentType.getDouble(context, "bonus"));
										ModConfig.MAIN.specItems.set(ModConfig.items);
										ModConfig.bakeConfig();
										context.getSource().sendSuccess( () ->Component.translatable("ItemID " + id + " can now backstab"), true);
										return 0;
									})
								))
						)).then(Commands.literal("remove").then(
										Commands.argument("item_id", ItemArgument.item(buildContext))
										.executes(context -> {
											String id = ForgeRegistries.ITEMS.getKey(ItemArgument.getItem(context, "item_id").getItem()).toString();
											if (ModConfig.items.removeIf(item -> item.startsWith(id))) {
												ModConfig.MAIN.specItems.set(ModConfig.items);
												context.getSource().sendSuccess( () ->Component.translatable("ItemID " + id + " can no longer backstab"), true);
												return 0;
											}
											context.getSource().sendSuccess( () ->Component.translatable("ItemID " + id + " was not found in list"), false);
											return 0;
										})
								)
						).executes(context -> {
							context.getSource().sendSuccess( () ->Component.translatable("List of items that can backstab: " + ModConfig.items.toString()), false);
							return 0;
						})
			).then(
					Commands.literal("all_items").then(
							Commands.argument("true/false", BoolArgumentType.bool())
							.executes(context -> {
								ModConfig.MAIN.specItemsAll.set(BoolArgumentType.getBool(context, "true/false"));
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("All items can backstab? " + ModConfig.backstabItemsAll), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("All items can backstab? " + ModConfig.backstabItemsAll), false);
						return 0;
					})
			).then(
					Commands.literal("players").then(
							Commands.argument("true/false", BoolArgumentType.bool())
							.executes(context -> {
								ModConfig.MAIN.specPlayers.set(BoolArgumentType.getBool(context, "true/false"));
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("Can backstab Players? " + ModConfig.backstabItemsAll), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("Can backstab Players? " + ModConfig.backstabItemsAll), false);
						return 0;
					})
			).then(
					Commands.literal("entities_blacklist").then(
							Commands.literal("add").then(
									Commands.argument("entity_id", ResourceArgument.resource(buildContext, Registries.ENTITY_TYPE)).suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
									.executes(context -> {
										String id = ResourceArgument.getSummonableEntityType(context,"entity_id").toString();
										if (!ModConfig.entities.contains(id)) {
											ModConfig.entities.add(id);
											ModConfig.MAIN.specEntities.set(ModConfig.entities);
											context.getSource().sendSuccess( () ->Component.translatable("EntityID " + id + " can no longer be backstabed"), true);
										} else {
											context.getSource().sendSuccess( () ->Component.translatable("EntityID " + id + " is already blacklisted"), true);
										}
										return 0;
									})
							)
					).then(
							Commands.literal("remove").then(
									Commands.argument("entity_id", ResourceArgument.resource(buildContext, Registries.ENTITY_TYPE))
									.executes(context -> {
										String id = ResourceArgument.getSummonableEntityType(context,"entity_id").toString();
										if (ModConfig.entities.remove(id)) {
											ModConfig.MAIN.specEntities.set(ModConfig.entities);
											context.getSource().sendSuccess( () ->Component.translatable("EntityID " + id + " can now be backstabed"), true);
											return 0;
										}
										context.getSource().sendSuccess( () ->Component.translatable("EntityID " + id + " was not found in list"), false);
										return 0;
									})
							)
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("List of entities that cannot be backstabed: " + ModConfig.entities.toString()), false);
						return 0;
					})
			).then(
					Commands.literal("sound").then(
							Commands.argument("sound_id", ResourceLocationArgument.id()).suggests(SuggestionProviders.AVAILABLE_SOUNDS)
							.executes(context -> {
								ModConfig.MAIN.specSound.set(ResourceLocationArgument.getId(context, "sound_id").toString());
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("SoundID " + ResourceLocationArgument.getId(context, "sound_id") + " is now played when someone backstabs"), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("SoundID played currently when backstabbing: " + ModConfig.backstabSound), false);
						return 0;
					})
			).then(
					Commands.literal("sound_pitch").then(
							Commands.argument("pitch", DoubleArgumentType.doubleArg(0.0, 100.0))
							.executes(context -> {
								ModConfig.MAIN.specPitch.set(DoubleArgumentType.getDouble(context, "pitch"));
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("Pitch of backstab sound is set to " + ModConfig.backstabPitch), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("Pitch of backstabs sound is currently " + ModConfig.backstabPitch), false);
						return 0;
					})
			).then(
					Commands.literal("sound_volume").then(
							Commands.argument("volume", DoubleArgumentType.doubleArg(0.0, 2.0))
							.executes(context -> {
								ModConfig.MAIN.specVolume.set(DoubleArgumentType.getDouble(context, "volume"));
								ModConfig.bakeConfig();
								context.getSource().sendSuccess( () ->Component.translatable("Volume of backstab sound is set to " + ModConfig.backstabVolume), true);
								return 0;
							})
					).executes(context -> {
						context.getSource().sendSuccess( () ->Component.translatable("Volume of backstabs sound is currently " + ModConfig.backstabVolume), false);
						return 0;
					})
			)
		));
	}
}
