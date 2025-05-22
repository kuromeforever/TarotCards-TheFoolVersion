package shiroroku.tarotcards.Item.Tarot;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import shiroroku.tarotcards.Configuration;
import shiroroku.tarotcards.Item.TarotItem;
import shiroroku.tarotcards.Registry.ItemRegistry;
import shiroroku.tarotcards.TarotCards;

import javax.annotation.Nullable;
import java.util.List;

public class TheEmpressTarot extends TarotItem {

    public static void handleOnPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (hasTarot(player, ItemRegistry.the_empress.get())) {
            player.level().getNearbyEntities(Animal.class, TargetingConditions.forNonCombat(), player, player.getBoundingBox().inflate(Configuration.the_empress_range.get())).forEach(e -> {
                if (e.canFallInLove() && e.getAge() == 0) {
                    TarotCards.LOGGER.debug("{} - Set in love", ItemRegistry.the_empress.get());
                    TarotCards.LOGGER.debug("Animal: {}", e);
                    e.setInLove(player);
                }
                if (e.isBaby()) {
                    TarotCards.LOGGER.debug("{} - Feed baby", ItemRegistry.the_empress.get());
                    TarotCards.LOGGER.debug("Animal: {}", e);
                    e.ageUp(AgeableMob.getSpeedUpSecondsWhenFeeding(-e.getAge()), true);
                }
            });
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.BLUE));
    }
}
