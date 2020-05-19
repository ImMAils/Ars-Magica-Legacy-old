package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.rituals.AbstractRitual;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModRituals;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Color;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class Light extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.getBlockState(pos).getBlock() == Blocks.AIR) blockFace = null;
        if (blockFace != null) pos = pos.offset(blockFace);
        return world.getBlockState(pos).getBlock() == Blocks.AIR;
//        if (!world.isRemote) world.setBlockState(pos, ModBlocks.MAGE_LIGHT.getDefaultState().withProperty(MageLight.COLOR, EnumDyeColor.byMetadata(getColor(stack))));
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            if (!world.isRemote)
                ((LivingEntity) target).addPotionEffect(new EffectInstance(ModEffects.ILLUMINATION.get(), SpellUtil.modifyIntMul(600, stack, caster, target, world, SpellModifiers.DURATION), SpellUtil.countModifiers(SpellModifiers.BUFF_POWER, stack)));
            return true;
        }
        return false;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.NONE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 50;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.COLOR);
    }

    @Override
    public AbstractRitual getRitual(LivingEntity caster) {
        return ModRituals.PURIFICATION.get();
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 5; i++) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle2", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 0.5, 1);
//                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, rand.nextDouble() * 0.2, rand.nextDouble() * 0.2 - 0.1);
//                particle.setAffectedByGravity();
//                particle.setDontRequireControllers();
//                particle.setMaxAge(5);
//                particle.setParticleScale(0.1f);
//                particle.setRGBColorF(0.6f, 0.2f, 0.8f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
//        }
    }

    private int getColor(ItemStack spell) {
        int dye_color_num = 15;
        if (SpellUtil.hasModifier(SpellModifiers.COLOR, spell)) {
            List<SpellModifier> modifiers = SpellUtil.getModifiers(spell, -1);
            for (SpellModifier mod : modifiers)
                if (mod instanceof Color) dye_color_num = spell.getTag().getInt("Color");
        }
        return 15 - dye_color_num;
    }
}
