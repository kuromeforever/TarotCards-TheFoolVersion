package shiroroku.tarotcards.Item.Tarot;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import shiroroku.tarotcards.Configuration;
import shiroroku.tarotcards.Item.TarotItem;
import shiroroku.tarotcards.Registry.ItemRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class TheMoonTarot extends TarotItem {

    private static final Supplier<MobEffectInstance> night_vision = () -> new MobEffectInstance(MobEffects.NIGHT_VISION, 311 + Configuration.tick_rate.get(), 0, true, false);

    public static void handleOnPlayerTick(TickEvent.PlayerTickEvent event) {
        if (hasTarot(event.player, ItemRegistry.the_moon.get())) {
            event.player.addEffect(night_vision.get());
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.BLUE));
    }
}
