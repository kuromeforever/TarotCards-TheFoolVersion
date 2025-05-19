package shiroroku.tarotcards.World;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import shiroroku.tarotcards.Configuration;
import shiroroku.tarotcards.Item.Tarot.*;

import java.util.List;
import java.util.function.Supplier;

/**
 * Custom loot modifier that has a chance to choose 1 item from a list.
 * Chance is set by {@link Configuration#default_loot_chance}
 * Is completely disabled when {@link  Configuration#do_loot_generation} is false
 */
public class TarotLootAdditions extends LootModifier {

    public List<Item> items;

    public static final Supplier<Codec<TarotLootAdditions>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
            .and(ForgeRegistries.ITEMS.getCodec()
                    .listOf()
                    .fieldOf("items")
                    .forGetter(v -> v.items))
            .apply(inst, TarotLootAdditions::new)));

    public TarotLootAdditions(LootItemCondition[] conditionsIn, List<Item> items) {
        super(conditionsIn);
        this.items = items;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (Configuration.do_loot_generation.get()) {
            double chance = Configuration.default_loot_chance.get();
            if (items.size() == 1) {
                var item = items.get(0);
                if (item instanceof TheWorldTarot) {
                    chance = 0.25;
                }
                if (item instanceof TheTowerTarot) {
                    chance = 0.3;
                }
                if (item instanceof DeathTarot) {
                    chance = 1;
                }
                // 教皇
                if (item instanceof TheHierophantTarot) {
                    chance = 0.3;
                }
                // 女祭司
                if (item instanceof TheHighPriestessTarot) {
                    chance = 0.25;
                }
                if (item instanceof TheStarTarot) {
                    chance = 0.15;
                }
                // 倒吊者
                if (item instanceof TheHangedManTarot) {
                    chance = 1;
                }
                if (item instanceof StrengthTarot) {
                    chance = 0.25;
                }
                // 节制
                if (item instanceof TemperanceTarot) {
                    chance = 0.15;
                }
            }

            if (context.getRandom().nextFloat() < chance) {
                generatedLoot.add(new ItemStack(items.get(context.getRandom().nextInt(items.size()))));
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends LootModifier> codec() {
        return CODEC.get();
    }
}
