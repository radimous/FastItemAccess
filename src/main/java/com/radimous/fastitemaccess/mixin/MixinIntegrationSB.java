package com.radimous.fastitemaccess.mixin;

import iskallia.vault.integration.IntegrationSB;
import iskallia.vault.util.InventoryUtil;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(value = IntegrationSB.class, remap = false)
public class MixinIntegrationSB {
    @Inject(method = "getBackpackItemAccess", at = @At("HEAD"), cancellable = true)
    private static void fastergetBackpackItemAccess(InventoryUtil.ItemAccess backpackAccess,
                                                    CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir){
        if (!(backpackAccess.getItem() instanceof BackpackItem)){
            cir.setReturnValue(Collections.emptyList());
        }
    }
}
