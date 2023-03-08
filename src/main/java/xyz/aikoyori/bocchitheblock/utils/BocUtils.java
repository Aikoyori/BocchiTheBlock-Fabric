package xyz.aikoyori.bocchitheblock.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BocUtils {
    public static float getDegreesToRotateFromTwoVector3(Vec3d source, Vec3d target)
    {
        Vec3d distance = target.add(source.multiply(-1));
        return (float) (MathHelper.atan2(distance.getZ(),distance.getX()) * 180/MathHelper.PI) + 90;

    }
    public static <T extends Entity> T getClosestEntity(List<? extends T> entityList, double x, double y, double z) {
        double d = -1.0;
        Entity livingEntity = null;
        for (Entity livingEntity2 : entityList) {;
            double e = livingEntity2.squaredDistanceTo(x, y, z);
            if (d != -1.0 && !(e < d)) continue;
            d = e;
            livingEntity = livingEntity2;
        }
        return (T)livingEntity;
    }

}
