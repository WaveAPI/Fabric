package org.waveapi.mixin;

import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.waveapi.content.resources.ResourceProvider;

import java.util.HashSet;
import java.util.Set;


@Mixin(ResourcePackManager.class)
public class MixinResourcePackManager {

    @Mutable
    @Shadow @Final private Set<ResourcePackProvider> providers;

    @Inject(method = "<init>*", at= @At("TAIL"))
    public void init(CallbackInfo info) {
        this.providers = new HashSet(this.providers);
        this.providers.add(new ResourceProvider());
    }

}