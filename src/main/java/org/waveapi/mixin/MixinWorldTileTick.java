package org.waveapi.mixin;

import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.waveapi.ticker.DeltaTickManager;
import org.waveapi.ticker.DeltaTicker;

import java.lang.reflect.InvocationTargetException;


@Mixin(World.class)
public abstract class MixinWorldTileTick {

    @Shadow public abstract Profiler getProfiler();

    public DeltaTicker ticker;

    @Inject(method = "<init>", at=@At("RETURN"))
    public void init(CallbackInfo ci) {
        try {
            ticker = DeltaTickManager.ticker.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Inject(method = "tickBlockEntities", at=@At("RETURN"))
    public void onTick(CallbackInfo ci) {
        Profiler profiler = getProfiler();
        profiler.push("deltaBlockEntities");

        ticker.tick();

        profiler.pop();
    }
}
