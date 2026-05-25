package net.succ.create_diesel_skyislands;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(Create_diesel_skyislands.MODID)
public class Create_diesel_skyislands {
    public static final String MODID = "create_diesel_skyislands";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredBlock<Block> SKY_OIL_DEPOSIT = BLOCKS.registerSimpleBlock(
        "sky_oil_deposit",
        BlockBehaviour.Properties.of()
            .strength(5.0f, 6.0f)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
    );

    public static final DeferredItem<BlockItem> SKY_OIL_DEPOSIT_ITEM =
        ITEMS.registerSimpleBlockItem(SKY_OIL_DEPOSIT, new Item.Properties());

    public Create_diesel_skyislands(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
