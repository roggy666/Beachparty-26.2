package net.satisfy.beachparty.core.compat.accessories;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.action.ActionResponse;
import io.wispforest.accessories.api.action.ActionResponseBuffer;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.satisfy.beachparty.core.registry.ObjectRegistry;

import java.util.Random;

/**
 * Registers the clothing as Accessories and implements what each piece does while worn in an accessory slot.
 * (Formerly the Trinkets / Curios integrations.)
 */
public class BeachpartyAccessories {
    public static void init() {
        AccessoryRegistry.register(ObjectRegistry.BEACH_HAT, new BeachHatAccessory());
        AccessoryRegistry.register(ObjectRegistry.CROCS, new CrocsAccessory());
        AccessoryRegistry.register(ObjectRegistry.SUNGLASSES, new SunglassesAccessory());
        AccessoryRegistry.register(ObjectRegistry.SWIM_WINGS, new SwimWingsAccessory());
        AccessoryRegistry.register(ObjectRegistry.TRUNKS, new SwimSuitAccessory());
        AccessoryRegistry.register(ObjectRegistry.BIKINI, new SwimSuitAccessory());
        AccessoryRegistry.register(ObjectRegistry.RUBBER_RING_STRIPPED, new RubberRingAccessory());
        AccessoryRegistry.register(ObjectRegistry.RUBBER_RING_PINK, new RubberRingAccessory());
        AccessoryRegistry.register(ObjectRegistry.RUBBER_RING_BLUE, new RubberRingAccessory());
        AccessoryRegistry.register(ObjectRegistry.RUBBER_RING_PELICAN, new RubberRingAccessory());
        AccessoryRegistry.register(ObjectRegistry.RUBBER_RING_AXOLOTL, new RubberRingAccessory());
        // the crocs' ocean walk is applied client side to the local player, see BeachpartyClient
    }

    /** Whether the entity wears any of the items, either as armor or in an accessory slot. */
    public static boolean isEquipped(LivingEntity entity, Item... items) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;
            Item worn = entity.getItemBySlot(slot).getItem();
            for (Item item : items) {
                if (worn == item) return true;
            }
        }

        AccessoriesCapability capability = AccessoriesCapability.get(entity);
        if (capability == null) return false;

        for (Item item : items) {
            if (!capability.getEquipped(item).isEmpty()) return true;
        }
        return false;
    }

    /** Shared behaviour: a small burn on equip for "sun protective" items and a conflict check against duplicates. */
    public abstract static class BaseAccessory implements Accessory {
        private final float fireDamageReduction;
        private final Item[] conflictingItems;

        protected BaseAccessory(float fireDamageReduction, Item... conflictingItems) {
            this.fireDamageReduction = fireDamageReduction;
            this.conflictingItems = conflictingItems;
        }

        @Override
        public void onEquip(ItemStack stack, SlotReference reference) {
            if (reference.entity() instanceof Player player && player.level() instanceof ServerLevel level && fireDamageReduction > 0) {
                float damage = Math.max(0, 1 - fireDamageReduction);
                if (damage > 0 && !player.isInvulnerableTo(level, level.damageSources().inFire())) {
                    player.hurtServer(level, level.damageSources().inFire(), damage);
                }
            }
        }

        @Override
        public void canEquip(ItemStack stack, SlotReference reference, ActionResponseBuffer buffer) {
            if (!(reference.entity() instanceof Player player)) {
                buffer.respondWith(ActionResponse.of(false, Component.translatable("tooltip.beachparty.accessory.players_only")));
                return;
            }
            if (conflictingItems.length > 0 && isEquipped(player, conflictingItems)) {
                buffer.respondWith(ActionResponse.of(false, Component.translatable("tooltip.beachparty.accessory.already_worn")));
            }
        }
    }

    public static class BeachHatAccessory extends BaseAccessory {
        public BeachHatAccessory() {
            super(0.10f, ObjectRegistry.BEACH_HAT);
        }
    }

    public static class CrocsAccessory extends BaseAccessory {
        public CrocsAccessory() {
            super(0);
        }
    }

    public static class SunglassesAccessory extends BaseAccessory {
        public SunglassesAccessory() {
            super(0.12f, ObjectRegistry.SUNGLASSES);
        }
    }

    public static class SwimSuitAccessory extends BaseAccessory {
        public SwimSuitAccessory() {
            super(0, ObjectRegistry.TRUNKS, ObjectRegistry.BIKINI);
        }

        @Override
        public void tick(ItemStack stack, SlotReference reference) {
            if (reference.entity() instanceof Player player && player.isInWater() && player.isSwimming()) {
                player.setDeltaMovement(player.getDeltaMovement().multiply(1.08, 1, 1.08));
            }
        }
    }

    public static class SwimWingsAccessory extends BaseAccessory {
        public SwimWingsAccessory() {
            super(0, ObjectRegistry.SWIM_WINGS);
        }

        @Override
        public void tick(ItemStack stack, SlotReference reference) {
            LivingEntity entity = reference.entity();
            if (entity.level().isClientSide() || !(entity instanceof Player player) || player.tickCount <= 20) return;

            if (!player.getCooldowns().isOnCooldown(stack) && !player.onGround() && player.getDeltaMovement().y < -0.1F && player.fallDistance > 3.0F) {
                player.fallDistance *= 0.5F;
                player.getCooldowns().addCooldown(stack, 2400);
            }
        }
    }

    public static class RubberRingAccessory extends BaseAccessory {
        private static final Random RANDOM = new Random();

        public RubberRingAccessory() {
            super(0, ObjectRegistry.RUBBER_RING_AXOLOTL, ObjectRegistry.RUBBER_RING_PELICAN, ObjectRegistry.RUBBER_RING_BLUE, ObjectRegistry.RUBBER_RING_STRIPPED, ObjectRegistry.RUBBER_RING_PINK);
        }

        @Override
        public void tick(ItemStack stack, SlotReference reference) {
            if (!(reference.entity() instanceof Player player) || !player.isInWater()) return;
            if (isJumpKeyPressed(player)) return;

            Vec3 motion = player.getDeltaMovement();
            double newY = Math.min(motion.y + 0.01, 0.3);
            Vec3 newMotion = motion.multiply(1.17, 1, 1.17).with(Direction.Axis.Y, newY);

            double maxSpeed = 0.17 * 1.17;
            if (newMotion.horizontalDistance() > maxSpeed) {
                newMotion = newMotion.normalize().scale(maxSpeed).with(Direction.Axis.Y, newY);
            }

            player.setDeltaMovement(newMotion);

            if (player.level().isLoaded(player.blockPosition())) {
                try {
                    BlockHitResult hitResult = player.level().clip(new ClipContext(player.position(), player.position().subtract(0, 256, 0), ClipContext.Block.OUTLINE, ClipContext.Fluid.SOURCE_ONLY, player));

                    if (hitResult.getType() != HitResult.Type.MISS) {
                        BlockPos pos = hitResult.getBlockPos();
                        double targetY = pos.getY();
                        if (player.getY() > targetY + 1.0 && !player.isUnderWater()) {
                            player.setPos(player.getX(), targetY + 1.0, player.getZ());
                        }
                    }
                } catch (Exception ignored) {
                }
            }

            Vec3 look = player.getLookAngle();
            Vec3 lookJ = new Vec3(look.x, 0, look.z);
            if (lookJ.lengthSqr() == 0) lookJ = new Vec3(1, 0, 0);
            Vec3 left = lookJ.cross(new Vec3(0, 1, 0)).normalize();
            double offset = 0.5;
            Vec3 leftPos = player.position().add(left.scale(offset)).add(0, 0.8, 0);
            Vec3 rightPos = player.position().subtract(left.scale(offset)).add(0, 0.8, 0);

            spawnWaterParticles(player, leftPos, rightPos);
        }

        private static boolean isJumpKeyPressed(Player player) {
            if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) return false;
            return player instanceof LocalPlayer && Minecraft.getInstance().options.keyJump.isDown();
        }

        private void spawnWaterParticles(Player player, Vec3 leftPos, Vec3 rightPos) {
            double spread = 0.2;
            double motionThreshold = 0.005;

            if (player.getDeltaMovement().lengthSqr() > motionThreshold) {
                for (int i = 0; i < 3; i++) {
                    spawnParticlePair(player, ParticleTypes.SPLASH, leftPos, rightPos, spread);
                    spawnParticlePair(player, ParticleTypes.BUBBLE_POP, leftPos, rightPos, spread);
                }
            } else if (player.level().getGameTime() % 20L == 0L) {
                for (int i = 0; i < 5; i++) {
                    spawnRandomSplash(player, spread);
                }
            }
        }

        private void spawnParticlePair(Player player, ParticleOptions particle, Vec3 leftPos, Vec3 rightPos, double spread) {
            spawnParticle(player, particle, leftPos, spread);
            spawnParticle(player, particle, rightPos, spread);
        }

        private void spawnParticle(Player player, ParticleOptions particle, Vec3 pos, double spread) {
            player.level().addParticle(particle, pos.x + (RANDOM.nextDouble() * spread - spread / 2), pos.y + (RANDOM.nextDouble() * spread - spread / 2), pos.z + (RANDOM.nextDouble() * spread - spread / 2), 0, 0, 0);
        }

        private void spawnRandomSplash(Player player, double spread) {
            double offset = 0.25;
            double angle = RANDOM.nextDouble() * Math.PI * 2;
            double xOffset = Math.cos(angle) * offset;
            double zOffset = Math.sin(angle) * offset;

            double x = player.getX() + xOffset;
            double y = player.getY() + player.getEyeHeight() - 0.5 + (RANDOM.nextDouble() * spread - spread / 2);
            double z = player.getZ() + zOffset;

            player.level().addParticle(ParticleTypes.SPLASH, x, y, z, 0, 0, 0);
        }
    }
}
