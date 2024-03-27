package committee.nova.mods.assassin.backstab.init.events;

import committee.nova.mods.assassin.backstab.Const;
import committee.nova.mods.assassin.backstab.common.cmds.Command;
import committee.nova.mods.assassin.backstab.common.items.DaggerItem;
import committee.nova.mods.assassin.backstab.init.config.ModConfig;
import committee.nova.mods.assassin.backstab.init.registries.ModDamageTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * ForgeEventBus
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/22 13:55
 */
@Mod.EventBusSubscriber
public class ForgeEventBus {
    private static boolean chargedAttack;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingHurtEvent(LivingHurtEvent event) {//todo 联动属性点
        if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player player) {
            if (!player.getCommandSenderWorld().isClientSide
                    && (event.getSource().getMsgId().equals("player") || event.getSource().getMsgId().equals("mob"))//todo 怪物的被刺
                    && (!ModConfig.backstabSneaking || player.isShiftKeyDown())
                    && Const.isValidItem(player.getMainHandItem())
                    && Const.isValidEntity(event.getEntity())) {
                double targetYaw = event.getEntity().getYHeadRot() % 360;
                //箭的伤害机制很奇怪
                double attackerYaw = event.getSource().getEntity().getYHeadRot() % 360;//获取y轴角度
                if (attackerYaw < 0) attackerYaw += 360;
                if (targetYaw < 0) targetYaw += 360;//正值
                if(Math.abs(targetYaw - attackerYaw) < ModConfig.backstabDegrees || 360 - Math.abs(targetYaw - attackerYaw) < ModConfig.backstabDegrees) {
                    float oldAmount = event.getAmount();
                    player.sendSystemMessage(Component.literal("原伤害" + oldAmount));
                    Double[] data = ModConfig.itemsData.get(ForgeRegistries.ITEMS.getKey(player.getMainHandItem().getItem()).toString());
                    if (data == null) data = new Double[]{ModConfig.backstabMultiplier, ModConfig.backstabBonus};
                    if (chargedAttack) {
                        event.setAmount((float) (event.getAmount() + data[1]));
                        chargedAttack = false;
                    }
                    event.setAmount((float) (event.getAmount() * data[0]));
                    event.setCanceled(true);
                    // 添加 oldAmount，因为取消会减去之前的伤害
                    event.getEntity().hurt(ModDamageTypes.causeDamage(player), event.getAmount() + oldAmount);
                    player.sendSystemMessage(Component.literal("新伤害" + event.getAmount() + oldAmount));
                    player.playNotifySound(getRegisteredSoundEvent(ModConfig.backstabSound), SoundSource.PLAYERS, (float) ModConfig.backstabVolume, (float) ModConfig.backstabPitch);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(final AttackEntityEvent event) {
        if (!event.getEntity().getCommandSenderWorld().isClientSide) {
            chargedAttack = event.getEntity().getAttackStrengthScale(0) == 1.0F;//返回基于冷却时间（0 到 1）的可用攻击力百分比
            if (event.getEntity().getMainHandItem().getItem() instanceof DaggerItem && event.getEntity().onGround()) {
                event.getEntity().setOnGround(false);
            }
        }
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        Command.register(event.getDispatcher(), event.getBuildContext());
    }

    private static SoundEvent getRegisteredSoundEvent(String id) {
        SoundEvent soundevent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(id));
        if (soundevent == null) {
            throw new IllegalStateException("Invalid Sound requested: " + id);
        } else {
            return soundevent;
        }
    }
}
