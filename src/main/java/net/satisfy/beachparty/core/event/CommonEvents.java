package net.satisfy.beachparty.core.event;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.phys.Vec3;
import net.satisfy.beachparty.core.block.BeachParasolBlock;
import net.satisfy.beachparty.core.block.BeachSunLounger;
import net.satisfy.beachparty.core.block.BeachTowelBlock;
import net.satisfy.beachparty.core.registry.ObjectRegistry;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

import java.util.Set;

public class CommonEvents {
    /** Vanilla chest loot tables that get a Beachparty pool appended (data/beachparty/loot_table/chests/<name>.json). */
    private static final Set<String> INJECTED_CHESTS = Set.of(
            "desert_pyramid", "buried_treasure", "shipwreck_supply", "shipwreck_treasure",
            "simple_dungeon", "underwater_ruin_big", "underwater_ruin_small", "woodland_mansion"
    );

    public static void init() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin() || !key.identifier().getNamespace().equals("minecraft")) return;

            String path = key.identifier().getPath();
            if (!path.startsWith("chests/")) return;

            String name = path.substring("chests/".length());
            if (INJECTED_CHESTS.contains(name)) {
                ResourceKey<LootTable> extra = ResourceKey.create(Registries.LOOT_TABLE, BeachpartyIdentifier.identifier("chests/" + name));
                tableBuilder.withPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(registries.lookupOrThrow(Registries.LOOT_TABLE).getOrThrow(extra))));
            }
        });

        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            ItemStack itemInHand = player.getItemInHand(hand);

            if (itemInHand.getItem() == ObjectRegistry.POOL_NOODLE) {
                Vec3 knockbackDirection = new Vec3(entity.getX() - player.getX(), 0.6, entity.getZ() - player.getZ()).normalize().scale(1.5);

                entity.push(knockbackDirection.x, knockbackDirection.y, knockbackDirection.z);

                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PARROT_IMITATE_SLIME, SoundSource.PLAYERS, 1.0F, 1.5F);

                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });

        // standing under a parasol shaves 4% off fire damage
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseAmount, amount, blocked) -> {
            if (!blocked && isFireDamage(source) && isNearBeachParasol(entity)) {
                entity.setHealth(entity.getHealth() + amount * 0.04f);
            }
        });

        EntitySleepEvents.ALLOW_SETTING_SPAWN.register((player, sleepingPos) -> {
            BlockState blockState = player.level().getBlockState(sleepingPos);
            return !(blockState.getBlock() instanceof BeachTowelBlock || blockState.getBlock() instanceof BeachSunLounger);
        });
    }

    private static boolean isFireDamage(DamageSource source) {
        return source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.LAVA);
    }

    private static boolean isNearBeachParasol(LivingEntity entity) {
        Level level = entity.level();
        BlockPos entityPos = entity.blockPosition();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos checkPos = entityPos.offset(x, y, z);
                    if (level.getBlockState(checkPos).getBlock() instanceof BeachParasolBlock) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
