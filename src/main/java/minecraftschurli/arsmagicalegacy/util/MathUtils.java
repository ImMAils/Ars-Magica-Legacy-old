package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public final class MathUtils {
    public static double normalizeRotation(double yaw) {
        while (yaw < 0) yaw += 360;
        while (yaw > 359) yaw -= 360;
        return yaw;
    }

    public static Vec3d extrapolateEntityLook(LivingEntity entity, double range) {
        float pitch = entity.rotationPitch;
        float yaw = entity.rotationYaw;
        double x = entity.getPosX();
        double y = entity.getPosY() + 1.6 - entity.getYOffset();
        double z = entity.getPosZ();
        Vec3d vec = new Vec3d(x, y, z);
        float f1 = MathHelper.cos(-yaw * 0.017453292f - (float) Math.PI);
        float f2 = MathHelper.sin(-yaw * 0.017453292f - (float) Math.PI);
        float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return vec.add(f2 * f3 * range, f4 * range, f1 * f3 * range);
    }
}
