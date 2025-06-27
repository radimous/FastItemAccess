package com.radimous.fastitemaccess.mixin;

import com.radimous.fastitemaccess.ExtendedItemAccess;
import iskallia.vault.util.InventoryUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/**
 * Add item obj to the access to be able to speed up some checks
 *
 * used for :
 * checking if you can get container access from item in {@link MixinInventoryUtil} and {@link MixinWoldInventoryUtil}<br>
 *
 * speeding up legendary bounty check in {@link MixinClientBountyData} (the mixin also limits when it is checked)<br>
 * filtering required items for the collection quest instead of checking all stacks (and copying them) in {@link MixinCollectionQuest}<br>
 * skipping non coin items when checking if player has enough coins and deducting coins in {@link MixinCoinDefinition}<br>
 *
 */
@Mixin(value = InventoryUtil.ItemAccess.class, remap = false)
public class MixinItemAccess implements ExtendedItemAccess {
    @Unique private Item fastitemaccess$item;

    public Item fastitemaccess$getItem() {
        return fastitemaccess$item;
    }

    @Inject(method = "<init>(Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;)V", at = @At("TAIL"))
    private void captureItemOnInit(ItemStack stack, Consumer<ItemStack> setter, CallbackInfo ci) {
        if (stack != null) { // might not be necessary, idk if passing null stack is actually valid or not
            fastitemaccess$item = stack.getItem();
        }
    }

    @Inject(method = "setStack", at = @At("TAIL"))
    private void captureItemOnsetStack(ItemStack stack, CallbackInfo ci) {
        if (stack != null) { // might not be necessary, idk if passing null stack is actually valid or not
            fastitemaccess$item = stack.getItem();
        }
    }
}
