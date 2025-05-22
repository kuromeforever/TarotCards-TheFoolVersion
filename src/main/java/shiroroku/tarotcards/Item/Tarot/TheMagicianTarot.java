package shiroroku.tarotcards.Item.Tarot;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import shiroroku.tarotcards.Item.TarotItem;
import shiroroku.tarotcards.Registry.ItemRegistry;
import shiroroku.tarotcards.TarotCards;

import javax.annotation.Nullable;
import java.util.List;

public class TheMagicianTarot extends TarotItem {

	private static final TagKey<Item> goldenItem = ItemTags.create(new ResourceLocation(TarotCards.MODID, "golden"));

    public static boolean handleItemDamage(ItemStack item, Player player) {
        if (hasTarot(player, ItemRegistry.the_magician.get())) {
            if (item.isDamageableItem() && item.getTags().anyMatch(t -> (t.equals(goldenItem)))) {
                TarotCards.LOGGER.debug("{} - Unbreakable gold items", ItemRegistry.the_magician.get());
                TarotCards.LOGGER.debug("For: {}", player);
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.BLUE));
    }
}
