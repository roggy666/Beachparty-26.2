package net.satisfy.beachparty.core.registry;

import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.beachparty.core.block.*;
import net.satisfy.beachparty.core.item.*;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;
import net.satisfy.beachparty.core.util.BeachpartyWoodType;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Every block and item of the mod. Registration happens directly into the vanilla registries while this class
 * is initialised, so the declaration order matters where one entry references another (stairs -> planks etc.).
 */
public class ObjectRegistry {
    public static final Item COCONUT_OPEN = registerItem("coconut_open", Item::new, settings().food(Foods.CARROT).compostable(ContextIntProviders.COMPOSTABLE_LOW));
    public static final Item RAW_MUSSEL_MEAT = registerItem("raw_mussel_meat", Item::new, settings().food(Foods.POTATO).compostable(ContextIntProviders.COMPOSTABLE_LOW));
    public static final Item COOKED_MUSSEL_MEAT = registerItem("cooked_mussel_meat", Item::new, settings().food(Foods.BAKED_POTATO).compostable(ContextIntProviders.COMPOSTABLE_LOW));

    public static final Item BEACH_HAT = registerItem("beach_hat", p -> new BeachpartyArmorItem(p, BeachpartyIdentifier.identifier("textures/models/armor/beach_hat.png")), settings().rarity(Rarity.EPIC).humanoidArmor(ArmorMaterialRegistry.KELP.material("beach_hat"), ArmorType.HELMET));
    public static final Item SUNGLASSES = registerItem("sunglasses", p -> new BeachpartyArmorItem(p, BeachpartyIdentifier.identifier("textures/models/armor/sunglasses.png")), settings().rarity(Rarity.RARE).humanoidArmor(ArmorMaterialRegistry.KELP.material("sunglasses"), ArmorType.HELMET));
    public static final Item RUBBER_RING_BLUE = registerItem("rubber_ring_blue", p -> new BeachpartyArmorItem(p, BeachpartyIdentifier.identifier("textures/models/armor/rubber_ring_blue.png")), settings().rarity(Rarity.UNCOMMON).humanoidArmor(ArmorMaterialRegistry.KELP.material("rubber_ring_blue"), ArmorType.CHESTPLATE));
    public static final Item RUBBER_RING_PINK = registerItem("rubber_ring_pink", p -> new BeachpartyArmorItem(p, BeachpartyIdentifier.identifier("textures/models/armor/rubber_ring_pink.png")), settings().rarity(Rarity.UNCOMMON).humanoidArmor(ArmorMaterialRegistry.KELP.material("rubber_ring_pink"), ArmorType.CHESTPLATE));
    public static final Item RUBBER_RING_STRIPPED = registerItem("rubber_ring_stripped", p -> new BeachpartyArmorItem(p, BeachpartyIdentifier.identifier("textures/models/armor/rubber_ring_stripped.png")), settings().rarity(Rarity.UNCOMMON).humanoidArmor(ArmorMaterialRegistry.KELP.material("rubber_ring_stripped"), ArmorType.CHESTPLATE));
    public static final Item RUBBER_RING_PELICAN = registerItem("rubber_ring_pelican", p -> new BeachpartyArmorItem(p, BeachpartyIdentifier.identifier("textures/models/armor/rubber_ring_pelican.png")), settings().rarity(Rarity.RARE).humanoidArmor(ArmorMaterialRegistry.KELP.material("rubber_ring_pelican"), ArmorType.CHESTPLATE));
    public static final Item RUBBER_RING_AXOLOTL = registerItem("rubber_ring_axolotl", p -> new BeachpartyArmorItem(p, BeachpartyIdentifier.identifier("textures/models/armor/rubber_ring_axolotl.png")), settings().rarity(Rarity.RARE).humanoidArmor(ArmorMaterialRegistry.KELP.material("rubber_ring_axolotl"), ArmorType.CHESTPLATE));
    public static final Item POOL_NOODLE = registerItem("pool_noodle", PoolNoodleItem::new, settings());
    public static final Item TRUNKS = registerItem("trunks", p -> new DyeableBeachpartyArmorItem(p, 16715535, BeachpartyIdentifier.identifier("textures/models/armor/trunks.png"), BeachpartyIdentifier.identifier("textures/models/armor/trunks_overlay.png")), settings().rarity(Rarity.UNCOMMON).humanoidArmor(ArmorMaterialRegistry.STRING.material("trunks"), ArmorType.LEGGINGS));
    public static final Item BIKINI = registerItem("bikini", p -> new DyeableBeachpartyArmorItem(p, 987135, BeachpartyIdentifier.identifier("textures/models/armor/bikini.png")), settings().rarity(Rarity.UNCOMMON).humanoidArmor(ArmorMaterialRegistry.STRING.material("bikini"), ArmorType.CHESTPLATE));
    public static final Item CROCS = registerItem("crocs", p -> new DyeableBeachpartyArmorItem(p, 1048335, BeachpartyIdentifier.identifier("textures/models/armor/crocs.png")), settings().rarity(Rarity.EPIC).humanoidArmor(ArmorMaterialRegistry.KELP.material("crocs"), ArmorType.BOOTS));
    public static final Item SWIM_WINGS = registerItem("swim_wings", p -> new DyeableBeachpartyArmorItem(p, 0xFF5800, BeachpartyIdentifier.identifier("textures/models/armor/swim_wings.png")), settings().humanoidArmor(ArmorMaterialRegistry.KELP.material("swim_wings"), ArmorType.CHESTPLATE));

    public static final Block WET_HAY_BLOCK = registerWithItem("wet_hay_block", WetHayBaleBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK));
    public static final Block THATCH = registerWithItem("thatch", HayBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK));
    public static final Block THATCH_STAIRS = registerWithItem("thatch_stairs", p -> new StairBlock(THATCH.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(THATCH).sound(SoundType.GRASS));
    public static final Block THATCH_SLAB = registerWithItem("thatch_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK));
    public static final Block PALM_LEAVES = registerWithItem("palm_leaves", PalmLeavesBlock::new, (block, p) -> new BlockItem(block, p.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final SaplingBlock PALM_SPROUT = registerWithItem("palm_sprout", PalmSproutBlock::new, (block, p) -> new BlockItem(block, p.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM)), PalmSproutBlock.sproutProperties());
    public static final Block STRIPPED_PALM_LOG = registerWithItem("stripped_palm_log", RotatedPillarBlock::new, logProperties());
    public static final Block STRIPPED_PALM_WOOD = registerWithItem("stripped_palm_wood", RotatedPillarBlock::new, logProperties());
    public static final Block PALM_LOG = registerWithItem("palm_log", RotatedPillarBlock::new, logProperties());
    public static final Block PALM_WOOD = registerWithItem("palm_wood", RotatedPillarBlock::new, logProperties());
    public static final Block PALM_PLANKS = registerWithItem("palm_planks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block PALM_FLOORBOARD = registerWithItem("palm_floorboard", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block PALM_STAIRS = registerWithItem("palm_stairs", p -> new StairBlock(PALM_PLANKS.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(PALM_PLANKS));
    public static final Block PALM_SLAB = registerWithItem("palm_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(2.0F).sound(SoundType.WOOD).explosionResistance(3.0F));
    public static final Block PALM_FENCE = registerWithItem("palm_fence", FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final Block PALM_FENCE_GATE = registerWithItem("palm_fence_gate", p -> new FenceGateBlock(WoodType.BAMBOO, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final Block PALM_BUTTON = registerWithItem("palm_button", p -> new ButtonBlock(BlockSetType.BAMBOO, 30, p), BlockBehaviour.Properties.of().noCollision().strength(0.5F).pushReaction(PushReaction.POPPED));
    public static final Block PALM_PRESSURE_PLATE = registerWithItem("palm_pressure_plate", p -> new PressurePlateBlock(BlockSetType.BAMBOO, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE));
    public static final Block PALM_DOOR = registerWithItem("palm_door", p -> new DoorBlock(BlockSetType.BAMBOO, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR));
    public static final Block PALM_TRAPDOOR = registerWithItem("palm_trapdoor", p -> new TrapDoorBlock(BlockSetType.BAMBOO, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));
    public static final Block PALM_GLASS = registerWithItem("palm_glass", PalmGlassBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Block PALM_GLASS_PANE = registerWithItem("palm_glass_pane", PalmGlassPaneBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final Block PALM_TABLE = registerWithItem("palm_table", PalmTableBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block PALM_BAR = registerWithItem("palm_bar", PalmBarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block PALM_CABINET = registerWithItem("palm_cabinet", p -> new PalmCabinetBlock(p, () -> SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN, () -> SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE), BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block PALM_CHAIR = registerWithItem("palm_chair", PalmChairBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).pushReaction(PushReaction.IGNORE_ENTITY));
    public static final Block BEACH_CHAIR = registerWithItem("beach_chair", BeachChairBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block HOODED_BEACH_CHAIR = registerWithItem("hooded_beach_chair", HoodedBeachChair::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block BEACH_SUN_LOUNGER = registerWithItem("beach_sun_lounger", p -> new BeachSunLounger(DyeColor.WHITE, p), BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).pushReaction(PushReaction.IGNORE_ENTITY).instabreak().mapColor(DyeColor.WHITE).bounceRestitution(0.25F));
    public static final Block PALM_BAR_STOOL = registerWithItem("palm_bar_stool", PalmBarStoolBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block BEACH_PARASOL = registerWithHintItem("beach_parasol", BeachParasolBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block BEACH_TOWEL = registerWithHintItem("beach_towel", p -> new BeachTowelBlock(DyeColor.WHITE, p), BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.red()).pushReaction(PushReaction.IGNORE_ENTITY).instabreak().mapColor(DyeColor.WHITE).bounceRestitution(0.25F));
    public static final Block MINI_FRIDGE = registerWithHintItem("mini_fridge", MiniFridgeBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.COPPER));
    public static final Block RADIO = registerWithHintItem("radio", RadioBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block MESSAGE_IN_A_BOTTLE = registerWithoutItem("message_in_a_bottle", p -> new MessageInABottleBlock(p, Block.box(4.0f, 0.0f, 4.0f, 12.0f, 6.0f, 12.0f)), BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final Item MESSAGE_IN_A_BOTTLE_ITEM = registerItem("message_in_a_bottle", p -> new MessageInABottleItem(MESSAGE_IN_A_BOTTLE, p), settings().useBlockDescriptionPrefix());
    public static final Block SEASHELL_BLOCK = registerWithoutItem("seashell_block", SeashellBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).instabreak().noCollision());
    public static final Item SEASHELL = registerItem("seashell", p -> new SeashellItem(SEASHELL_BLOCK, p), settings());
    public static final Block SAND_BUCKET_BLOCK_FILLED = registerWithoutItem("sand_bucket_block_filled", SandBucketBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT));
    public static final Item SAND_BUCKET_FILLED = registerItem("sand_bucket_filled", p -> new SandBucketItem(SAND_BUCKET_BLOCK_FILLED, p), settings().stacksTo(1).useBlockDescriptionPrefix());
    public static final Block SAND_BUCKET_BLOCK_EMPTY = registerWithoutItem("sand_bucket_block_empty", SandBucketBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART));
    public static final Item SAND_BUCKET_EMPTY = registerItem("sand_bucket_empty", p -> new SandBucketItem(SAND_BUCKET_BLOCK_EMPTY, p), settings().useBlockDescriptionPrefix());
    public static final Block SANDCASTLE = registerWithoutItem("sandcastle", SandBucketBlock.SandCastleBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SAND));
    public static final Block SAND_PILE = registerWithoutItem("sand_pile", p -> new SandBucketBlock.SandPileBlock(new ColorRGBA(14406560), p), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.SAND));
    public static final Item COCONUT = registerItem("coconut", CoconutItem::new, settings().compostable(ContextIntProviders.COMPOSTABLE_LOW));
    public static final Block COCONUT_COCKTAIL = registerCocktail("coconut_cocktail", MobEffects.STRENGTH, CocktailBlock.COCONUT_COCKTAIL_SHAPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).noOcclusion().instabreak());
    public static final Block SWEETBERRIES_COCKTAIL = registerCocktail("sweetberries_cocktail", MobEffects.ABSORPTION, CocktailBlock.SWEETBERRIES_COCKTAIL_SHAPE);
    public static final Block COCOA_COCKTAIL = registerCocktail("cocoa_cocktail", MobEffects.REGENERATION, CocktailBlock.COCOA_COCKTAIL_SHAPE);
    public static final Block PUMPKIN_COCKTAIL = registerCocktail("pumpkin_cocktail", MobEffects.FIRE_RESISTANCE, CocktailBlock.PUMPKIN_COCKTAIL_SHAPE);
    public static final Block HONEY_COCKTAIL = registerCocktail("honey_cocktail", MobEffects.HASTE, CocktailBlock.HONEY_COCKTAIL_SHAPE);
    public static final Block MELON_COCKTAIL = registerCocktail("melon_cocktail", MobEffects.LUCK, CocktailBlock.MELON_COCKTAIL_SHAPE);
    public static final Item BEACH_BALL = registerItem("beach_ball", BeachBallItem::new, settings());
    public static final Block BEACH_GOAL = registerWithHintItem("beach_goal", BeachGoalBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS));
    public static final Block PALM_TORCH = registerWithoutItem("palm_torch", p -> new TorchBlock(ParticleTypes.FLAME, p), BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).noCollision().instabreak().lightLevel((state) -> 14).sound(SoundType.WOOD));
    public static final Block PALM_WALL_TORCH = registerWithoutItem("palm_wall_torch", p -> new WallTorchBlock(ParticleTypes.FLAME, p), BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).noCollision().instabreak().lightLevel((state) -> 14).sound(SoundType.WOOD).overrideLootTable(PALM_TORCH.getLootTable()));
    public static final Item PALM_TORCH_ITEM = registerItem("palm_torch_item", p -> new StandingAndWallBlockItem(PALM_TORCH, PALM_WALL_TORCH, Direction.DOWN, p), settings().overrideDescription("block.beachparty.palm_torch"));
    public static final Block TALL_PALM_TORCH = registerWithItem("tall_palm_torch", p -> new TallPalmTorchBlock(p, ParticleTypes.FLAME), BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).noCollision().instabreak().lightLevel((state) -> 14).sound(SoundType.WOOD));
    public static final Block HANGING_COCONUT = registerWithoutItem("hanging_coconut", HangingCoconutBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO));
    public static final Block SANDWAVES = registerWithItem("sandwaves", p -> new ColoredFallingBlock(new ColorRGBA(14406560), p), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SAND));
    public static final Block PALM_SIGN = registerWithoutItem("palm_sign", p -> new PalmStandingSignBlock(p, BeachpartyWoodType.PALM), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN));
    public static final Block PALM_WALL_SIGN = registerWithoutItem("palm_wall_sign", p -> new PalmWallSignBlock(p, BeachpartyWoodType.PALM), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN));
    public static final Block PALM_HANGING_SIGN = registerWithoutItem("palm_hanging_sign", p -> new PalmCeilingHangingSignBlock(p, BeachpartyWoodType.PALM), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN));
    public static final Block PALM_WALL_HANGING_SIGN = registerWithoutItem("palm_wall_hanging_sign", p -> new PalmWallHangingSignBlock(p, BeachpartyWoodType.PALM), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN));
    public static final Item PALM_SIGN_ITEM = registerItem("palm_sign", p -> new StandingAndWallBlockItem(PALM_SIGN, PALM_WALL_SIGN, Direction.DOWN, p), settings().stacksTo(16).signText().useBlockDescriptionPrefix());
    public static final Item PALM_HANGING_SIGN_ITEM = registerItem("palm_hanging_sign", p -> new HangingSignItem(PALM_HANGING_SIGN, PALM_WALL_HANGING_SIGN, p), settings().stacksTo(16).signText().useBlockDescriptionPrefix());
    public static final Block BEACHPARTY_BANNER = registerWithItem("beachparty_banner", CompletionistBannerBlock::new, (block, p) -> new TooltipBlockItem(block, p, "tooltip.beachparty.banner.thankyou_1", "", "tooltip.beachparty.banner.thankyou_2", "tooltip.beachparty.banner.thankyou_4", "", "tooltip.beachparty.banner.thankyou_3"), BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollision().sound(SoundType.WOOD));
    public static final Block BEACHPARTY_WALL_BANNER = registerWithoutItem("beachparty_wall_banner", CompletionistWallBannerBlock::new, BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollision().sound(SoundType.WOOD));
    // Boats come after every block the entity registry needs, since referencing EntityTypeRegistry initialises it
    public static final Item PALM_BOAT = registerItem("palm_boat", p -> new BoatItem(EntityTypeRegistry.PALM_BOAT, p), settings().stacksTo(1));
    public static final Item PALM_CHEST_BOAT = registerItem("palm_chest_boat", p -> new BoatItem(EntityTypeRegistry.PALM_CHEST_BOAT, p), settings().stacksTo(1));
    public static final Item FLOATY_BOAT = registerItem("floaty_boat", p -> new BoatItem(EntityTypeRegistry.FLOATY_BOAT, p), settings().stacksTo(1));
    public static final Item FLOATY_CHEST_BOAT = registerItem("floaty_chest_boat", p -> new BoatItem(EntityTypeRegistry.FLOATY_CHEST_BOAT, p), settings().stacksTo(1));
    public static final Item OVERGROWN_DISC = registerItem("overgrown_disc", Item::new, settings().stacksTo(1).jukeboxPlayable(SoundEventRegistry.OVER_THE_RAINBOW).rarity(Rarity.RARE));
    public static final Item MUSIC_DISC_BEACHPARTY = registerItem("music_disc_beachparty", Item::new, settings().stacksTo(1).jukeboxPlayable(SoundEventRegistry.BEACHPARTY).rarity(Rarity.RARE));
    public static final Item MUSIC_DISC_CARIBBEAN_BEACH = registerItem("music_disc_caribbean_beach", Item::new, settings().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(SoundEventRegistry.CARIBBEAN_BEACH));
    public static final Item MUSIC_DISC_PRIDELANDS = registerItem("music_disc_pridelands", Item::new, settings().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(SoundEventRegistry.PRIDELANDS));
    public static final Item MUSIC_DISC_VOCALISTA = registerItem("music_disc_vocalista", Item::new, settings().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(SoundEventRegistry.VOCALISTA));
    public static final Item MUSIC_DISC_WILD_VEINS = registerItem("music_disc_wild_veins", Item::new, settings().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(SoundEventRegistry.WILD_VEINS));

    static Item.Properties settings() {
        return new Item.Properties();
    }

    private static BlockBehaviour.Properties logProperties() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(2.0F).sound(SoundType.WOOD);
    }

    // Food effects live on the Consumable component since 1.21.2
    private static Item.Properties cocktailSettings(Holder<MobEffect> effect) {
        FoodProperties food = new FoodProperties.Builder().nutrition(1).saturationModifier(1).build();
        Consumable consumable = Consumables.defaultDrink()
                .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(effect, 900), 1.0f))
                .build();
        // every cocktail used to be registered as 30% compostable
        // items of blocks keep their block translation keys (26.x items default to item.<id>)
        return settings().food(food, consumable).compostable(ContextIntProviders.COMPOSTABLE_LOW).useBlockDescriptionPrefix();
    }

    public static void init() {
        // Registration is driven by the static initialiser; touching the class is enough
    }

    private static Block registerCocktail(String name, Holder<MobEffect> effect, VoxelShape shape) {
        return registerCocktail(name, effect, shape, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion().instabreak());
    }

    private static Block registerCocktail(String name, Holder<MobEffect> effect, VoxelShape shape, BlockBehaviour.Properties properties) {
        Block block = registerWithoutItem(name, p -> new CocktailBlock(p, effect.value(), 600, () -> shape), properties);
        registerItem(name, p -> new DrinkBlockItem(block, p), cocktailSettings(effect));
        return block;
    }

    public static <T extends Block> T registerWithItem(String name, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties properties) {
        return registerWithItem(name, factory, BlockItem::new, properties);
    }

    /** Block whose item shows the "can be placed" hint. */
    private static <T extends Block> T registerWithHintItem(String name, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties properties) {
        return registerWithItem(name, factory, (block, p) -> new TooltipBlockItem(block, p, TooltipBlockItem.CAN_BE_PLACED), properties);
    }

    public static <T extends Block> T registerWithItem(String name, Function<BlockBehaviour.Properties, T> factory, BiFunction<T, Item.Properties, Item> itemFactory, BlockBehaviour.Properties properties) {
        T block = registerWithoutItem(name, factory, properties);
        registerItem(name, p -> itemFactory.apply(block, p), settings().useBlockDescriptionPrefix());
        return block;
    }

    public static <T extends Block> T registerWithoutItem(String name, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties properties) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, BeachpartyIdentifier.identifier(name));
        return Registry.register(BuiltInRegistries.BLOCK, key, factory.apply(properties.setId(key)));
    }

    public static <T extends Item> T registerItem(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, BeachpartyIdentifier.identifier(name));
        return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(properties.setId(key)));
    }
}
