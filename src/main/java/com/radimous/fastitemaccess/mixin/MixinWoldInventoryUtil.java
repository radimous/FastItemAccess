package com.radimous.fastitemaccess.mixin;

import com.radimous.fastitemaccess.ExtendedItemAccess;
import io.github.lightman314.lightmanscurrency.common.items.WalletItem;
import iskallia.vault.util.InventoryUtil;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.woldsvaults.api.helper.WoldInventoryUtil;

import java.util.Collections;
import java.util.List;
@Restriction(
    require = {
        @Condition(type = Condition.Type.MOD, value = "woldsvaults"),
        @Condition(type = Condition.Type.MOD, value = "lightmanscurrency"),
    }
)
@Mixin(value = WoldInventoryUtil.class, remap = false)
public class MixinWoldInventoryUtil {
    @Inject(method = "getCoinPouchItemAccess", at = @At("HEAD"), cancellable = true)
    private static void getCoinPouchItemAccess(InventoryUtil.ItemAccess access, CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir) {

        if (access instanceof ExtendedItemAccess eia){
            var item = eia.fastitemaccess$getItem();
            if (!(item instanceof WalletItem)){
                cir.setReturnValue(Collections.emptyList());
            }
        }
    }
}
