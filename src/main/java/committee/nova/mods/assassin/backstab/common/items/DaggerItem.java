package committee.nova.mods.assassin.backstab.common.items;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/**
 * DaggerItem
 *
 * @author cnlimiter
 * @version 1.0
 * @description
 * @date 2024/3/22 13:53
 */
public class DaggerItem extends SwordItem {

    public DaggerItem(Tier tier, int damage, Properties properties) {
        super(tier, 0, -1F, properties);
    }
}
