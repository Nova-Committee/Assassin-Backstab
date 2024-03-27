package committee.nova.mods.assassin.backstab.init.config;

import committee.nova.mods.assassin.backstab.Const;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static committee.nova.mods.assassin.backstab.init.config.ModConfig.Main.*;

@Mod.EventBusSubscriber(modid = Const.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfig {

	public static final String GENERAL = "general";
	public static String bonusString = "Adds bonus damage on backstab with a fully charged attack [Default: 0.0]";
	public static String multiplierString = "Changes the damage multiplier [Default: 1.5]";
	public static String degreeString = "Changes the degree needed to backstab [Default: 45]";
	public static String sneakingString = "Requires sneaking to backstab? [Default: false]";
	public static String bypassArmorString = "Backstab damage bypasses armor [Default: false]";
	public static String itemsString = "What items CAN backstab? [Format: modid:itemid,(override multiplier),(override bonus)], if override value is negative or missing, the config multiplier and bonus will be used instead.";
	public static String itemsString2 = "Note this is separate from the tags system where items or item tags can be added to backstab:backstab which will only use default values (whitelist overrides tags)";
	public static String itemsAllString = "Overrides items, will enable everything (including fists) to backstab [Default: false]";
	public static String entitiesString = "What mobs CANNOT be backstabbed? [Format: modid:entityid], [Default: minecraft:ender_dragon] due to messy interactions";
	public static String playersString = " Can players be backstabbed by other players?";
	public static String soundString = "Sound played when the player backstab [Format: Resource Location or /playsound (sound) format, Default: 'minecraft:entity.arrow.hit_player']";
	public static String volumeString = "Changes the volume of the backstab sound [Default: 0.75]";
	public static String pitchString = "Changes the pitch of the backstab sound [Default: 0.4]";



	public static class Main{
		public final DoubleValue specBonus;
		public final DoubleValue specMultiplier;
		public final DoubleValue specDegrees;
		public final BooleanValue specSneaking;
		public final BooleanValue specBypassArmor;
		public final ConfigValue<List<String>> specItems;
		public final BooleanValue specItemsAll;
		public final ConfigValue<List<String>> specEntities;
		public final BooleanValue specPlayers;
		public final ConfigValue<String> specSound;
		public final DoubleValue specVolume;
		public final DoubleValue specPitch;
		public Main(ForgeConfigSpec.Builder builder){
			builder.push("general");
			specBonus = builder
					.comment(bonusString)
					.translation(Const.MOD_ID + ".config.bonus")
					.defineInRange("bonus", 0.0, 0.0, 1000.0);
			specMultiplier = builder
					.comment(multiplierString)
					.translation(Const.MOD_ID + ".config.multiplier")
					.defineInRange("multiplier", 1.5, 0.0, 1000.0);
			specDegrees = builder
					.comment(degreeString)
					.translation(Const.MOD_ID + ".config.degree")
					.defineInRange("degree", 100.0, 0.0, 360.0);
			specSneaking = builder
					.comment(sneakingString)
					.translation(Const.MOD_ID + ".config.sneaking")
					.define("sneaking", false);
			specBypassArmor = builder
					.comment(bypassArmorString)
					.translation(Const.MOD_ID + ".config.armorbypass")
					.define("bypassArmor", false);
			builder.pop();

			builder.push("items");
			specItems = builder
					.comment(itemsString)
					.comment(itemsString2)
					.translation(Const.MOD_ID + ".config.items")
					.define("items",
							Arrays.asList(
									"backstab:wood_dagger,1.5", "backstab:stone_dagger,1.7",
									"backstab:iron_dagger,1.8", "backstab:gold_dagger,1.5",
									"backstab:diamond_dagger,1.8", "backstab:netherite_dagger,1.8,1.0"),
							string -> string instanceof String);
			specItemsAll = builder
					.comment(itemsAllString)
					.translation(Const.MOD_ID + ".config.itemsall")
					.define("itemsall", false);
			builder.pop();

			builder.push("entities");
			specEntities = builder
					.comment(entitiesString)
					.translation(Const.MOD_ID + ".config.entities_blacklist")
					.define("entities_blacklist",
							Arrays.asList("minecraft:ender_dragon"),
							string -> string instanceof String);
			specPlayers = builder
					.comment(playersString)
					.translation(Const.MOD_ID + ".config.players")
					.define("players", true);
			builder.pop();

			builder.push("sounds");
			specSound = builder
					.comment(soundString)
					.translation(Const.MOD_ID + ".config.sound")
					.define("sound", "minecraft:entity.arrow.hit_player");
			specVolume = builder
					.comment(volumeString)
					.translation(Const.MOD_ID + ".config.volume")
					.defineInRange("volume", 0.75, 0.0, 100.0);
			specPitch = builder
					.comment(pitchString)
					.translation(Const.MOD_ID + ".config.pitch")
					.defineInRange("pitch", 0.4, 0.0, 2.0);
			builder.pop();
		}

	}

	public static final ForgeConfigSpec mainSpec;
	public static final Main MAIN;
	static {
		final Pair<Main, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Main::new);
		mainSpec = specPair.getRight();
		MAIN = specPair.getLeft();
	}


	public static double backstabBonus, backstabMultiplier, backstabDegrees;
	public static boolean backstabSneaking, backstabBypassArmor;
	public static boolean backstabItemsAll;
	public static boolean backstabPlayers;
	public static String backstabSound;
	public static double backstabVolume;
	public static double backstabPitch;

	public static List<String> items;
	public static HashMap<String, Double[]> itemsData;
	public static List<String> entities;

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent event)
	{
		bakeConfig();
	}

	public static void bakeConfig(){
		backstabBonus = MAIN.specBonus.get();
		backstabMultiplier = MAIN.specMultiplier.get();
		backstabDegrees = MAIN.specDegrees.get();
		backstabSneaking = MAIN.specSneaking.get();
		backstabBypassArmor = MAIN.specBypassArmor.get();
		backstabItemsAll = MAIN.specItemsAll.get();
		backstabPlayers = MAIN.specPlayers.get();
		backstabSound = MAIN.specSound.get();
		backstabVolume = MAIN.specVolume.get();
		backstabPitch = MAIN.specPitch.get();

		items = MAIN.specItems.get();
		itemsData = new HashMap<>();
		for (String item : items) {
			String[] arr = item.split(",");
			if (arr.length < 1) continue;
			double mult = arr.length > 1 ? Double.parseDouble(arr[1]) : backstabMultiplier;
			double bonus = arr.length > 2 ? Double.parseDouble(arr[2]) : backstabBonus;
			mult = mult < 0.0 ? backstabMultiplier : mult;
			bonus = bonus < 0.0 ? backstabBonus : bonus;
			itemsData.put(arr[0], new Double[]{mult, bonus});
		}

		entities = MAIN.specEntities.get();
	}

}
