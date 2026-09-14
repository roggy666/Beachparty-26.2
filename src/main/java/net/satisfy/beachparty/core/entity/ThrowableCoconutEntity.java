package net.satisfy.beachparty.core.entity;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.satisfy.beachparty.core.registry.EntityTypeRegistry;
import net.satisfy.beachparty.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import net.minecraft.server.level.ServerLevel;

public class ThrowableCoconutEntity extends ThrowableItemProjectile {

    public ThrowableCoconutEntity(Level world, LivingEntity owner) {
        super(EntityTypeRegistry.COCONUT, owner, world, new ItemStack(ObjectRegistry.COCONUT));
    }

    public ThrowableCoconutEntity(EntityType<? extends ThrowableCoconutEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ObjectRegistry.COCONUT;
    }

    private ParticleOptions getParticleParameters() {
        return new ItemParticleOption(ParticleTypes.ITEM, ObjectRegistry.COCONUT);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            Level level = this.level();
            for (int i = 0; i < 30; i++) {
                double ox = this.random.nextGaussian() * 0.02D;
                double oy = this.random.nextGaussian() * 0.02D;
                double oz = this.random.nextGaussian() * 0.02D;
                level.addParticle(getParticleParameters(), this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0.0D, 0.0D, 0.0D);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        int damage = 2;
        if (this.level() instanceof ServerLevel serverLevel) {
            target.hurtServer(serverLevel, target.damageSources().thrown(this, this.getOwner()), (float) damage);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        Level level = this.level();
        if (level instanceof ServerLevel serverLevel) {
            level.broadcastEntityEvent(this, (byte) 3);
            this.playSound(SoundEvents.WOOD_FALL, 1.0F, 1.0F);
            this.spawnAtLocation(serverLevel, ObjectRegistry.COCONUT_OPEN);
            this.spawnAtLocation(serverLevel, ObjectRegistry.COCONUT_OPEN);
            if (level.getRandom().nextFloat() < 0.45F) {
                this.spawnAtLocation(serverLevel, ObjectRegistry.PALM_SPROUT);
            }
            this.discard();
        }
    }

}
