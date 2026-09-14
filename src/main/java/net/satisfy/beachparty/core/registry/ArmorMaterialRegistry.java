package net.satisfy.beachparty.core.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

import java.util.Map;

/**
 * Armor materials are plain records now: durability, defense, repair tag and the equipment asset that names the
 * model textures in assets/beachparty/equipment. All Beachparty clothing shares the same weak stats.
 */
public enum ArmorMaterialRegistry {
    KELP("repairs_kelp_clothing"),
    STRING("repairs_string_clothing");

    private static final int DURABILITY_MULTIPLIER = 5;
    private static final Map<ArmorType, Integer> DEFENSE = Map.of(
            ArmorType.BOOTS, 1,
            ArmorType.LEGGINGS, 1,
            ArmorType.CHESTPLATE, 1,
            ArmorType.HELMET, 1,
            ArmorType.BODY, 1
    );
    private static final int ENCHANTMENT_VALUE = 15;
    private static final Holder<SoundEvent> EQUIP_SOUND = SoundEvents.ARMOR_EQUIP_LEATHER;

    private final TagKey<Item> repairTag;

    ArmorMaterialRegistry(String repairTag) {
        this.repairTag = TagKey.create(Registries.ITEM, BeachpartyIdentifier.identifier(repairTag));
    }

    public ArmorMaterial material(String assetName) {
        ResourceKey<EquipmentAsset> asset = ResourceKey.create(EquipmentAssets.ROOT_ID, BeachpartyIdentifier.identifier(assetName));
        return new ArmorMaterial(DURABILITY_MULTIPLIER, DEFENSE, ENCHANTMENT_VALUE, EQUIP_SOUND, 0.0F, 0.0F, this.repairTag, asset);
    }
}
