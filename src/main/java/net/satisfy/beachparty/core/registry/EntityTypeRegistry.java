package net.satisfy.beachparty.core.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.beachparty.core.block.entity.*;
import net.satisfy.beachparty.core.entity.BeachBallEntity;
import net.satisfy.beachparty.core.entity.ChairEntity;
import net.satisfy.beachparty.core.entity.ThrowableCoconutEntity;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

import java.util.Set;
import java.util.function.Supplier;

import static net.satisfy.beachparty.core.registry.ObjectRegistry.*;

public class EntityTypeRegistry {
    public static final EntityType<ChairEntity> CHAIR = registerEntity("chair", EntityType.Builder.of(ChairEntity::new, MobCategory.MISC).sized(0.001F, 0.001F));
    public static final EntityType<ThrowableCoconutEntity> COCONUT = registerEntity("coconut", EntityType.Builder.<ThrowableCoconutEntity>of(ThrowableCoconutEntity::new, MobCategory.MISC).sized(0.25f, 0.25f));
    public static final EntityType<BeachBallEntity> BEACH_BALL = registerEntity("beach_ball", EntityType.Builder.of(BeachBallEntity::new, MobCategory.MISC).sized(0.4f, 0.4f));

    // Boats are plain vanilla boats since 1.21.2; the item supplier is lazy because the items reference these types
    public static final EntityType<Boat> PALM_BOAT = registerBoat("palm_boat", () -> ObjectRegistry.PALM_BOAT);
    public static final EntityType<ChestBoat> PALM_CHEST_BOAT = registerChestBoat("palm_chest_boat", () -> ObjectRegistry.PALM_CHEST_BOAT);
    public static final EntityType<Boat> FLOATY_BOAT = registerBoat("floaty_boat", () -> ObjectRegistry.FLOATY_BOAT);
    public static final EntityType<ChestBoat> FLOATY_CHEST_BOAT = registerChestBoat("floaty_chest_boat", () -> ObjectRegistry.FLOATY_CHEST_BOAT);

    public static final BlockEntityType<PalmSignBlockEntity> BEACHPARTY_SIGN = registerBlockEntity("beachparty_sign", PalmSignBlockEntity::new, PALM_SIGN, PALM_WALL_SIGN);
    public static final BlockEntityType<PalmHangingSignBlockEntity> BEACHPARTY_HANGING_SIGN = registerBlockEntity("beachparty_hanging_sign", PalmHangingSignBlockEntity::new, PALM_HANGING_SIGN, PALM_WALL_HANGING_SIGN);
    public static final BlockEntityType<MiniFridgeBlockEntity> MINI_FRIDGE_BLOCK_ENTITY = registerBlockEntity("mini_fridge", MiniFridgeBlockEntity::new, MINI_FRIDGE);
    public static final BlockEntityType<PalmCabinetBlockEntity> CABINET_BLOCK_ENTITY = registerBlockEntity("cabinet", PalmCabinetBlockEntity::new, PALM_CABINET);
    public static final BlockEntityType<PalmBarBlockEntity> PALM_BAR_BLOCK_ENTITY = registerBlockEntity("palm_bar", PalmBarBlockEntity::new, PALM_BAR);
    public static final BlockEntityType<BeachGoalBlockEntity> BEACH_GOAL_BLOCK_ENTITY = registerBlockEntity("beach_goal", BeachGoalBlockEntity::new, BEACH_GOAL);
    public static final BlockEntityType<WetHayBaleBlockEntity> WET_HAY_BALE_BLOCK_ENTITY = registerBlockEntity("wet_hay_bale", WetHayBaleBlockEntity::new, WET_HAY_BLOCK);
    public static final BlockEntityType<CompletionistBannerEntity> BEACHPARTY_BANNER = registerBlockEntity("beachparty_banner", CompletionistBannerEntity::new, ObjectRegistry.BEACHPARTY_BANNER, ObjectRegistry.BEACHPARTY_WALL_BANNER);
    public static final BlockEntityType<RadioBlockEntity> RADIO_BLOCK_ENTITY = registerBlockEntity("radio", RadioBlockEntity::new, RADIO);

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String path, BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, BeachpartyIdentifier.identifier(path), new BlockEntityType<>(factory, Set.of(blocks)));
    }

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> registerEntity(String path, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, BeachpartyIdentifier.identifier(path));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    private static EntityType<Boat> registerBoat(String path, Supplier<Item> dropItem) {
        return registerEntity(path, EntityType.Builder.<Boat>of((type, level) -> new Boat(type, level, dropItem), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
    }

    private static EntityType<ChestBoat> registerChestBoat(String path, Supplier<Item> dropItem) {
        return registerEntity(path, EntityType.Builder.<ChestBoat>of((type, level) -> new ChestBoat(type, level, dropItem), MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(BEACH_BALL, BeachBallEntity.createMobAttributes());
    }
}
