package com.radimous.fastitemaccess.mixin;

import com.radimous.fastitemaccess.ExtendedItemAccess;
import com.radimous.fastitemaccess.Fastitemaccess;
import iskallia.vault.init.ModItems;
import iskallia.vault.util.InventoryUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

/**
 * This avoids unnecessary memcopies when traversing the inventory tree by checking for item instead of creating immutable copy of the whole itemstack every time
 * needed info (pointer to the item) is added in {@link MixinItemAccess}
 */
@Mixin(value = InventoryUtil.class, remap = false)
public abstract class MixinInventoryUtil {

    @Shadow private static boolean isShulkerBox(Item item) {return false;}

    @Inject(method = "getBotaniaBaubleBoxAccess", at = @At("HEAD"), cancellable = true)
    private static void fastergetBotaniaBaubleBoxAccess(InventoryUtil.ItemAccess containerAccess, CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir){
        if (Fastitemaccess.doesntMatch(containerAccess,"botania:bauble_box")){
            cir.setReturnValue(new ArrayList<>());
        }
    }

    @Inject(method = "getSupplementariesSackAccess", at = @At("HEAD"), cancellable = true)
    private static void fastergetSupplementariesSackAccess(InventoryUtil.ItemAccess containerAccess, CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir){
        if (Fastitemaccess.doesntMatch(containerAccess,"supplementaries:sack")){
            cir.setReturnValue(new ArrayList<>());
        }
    }

    @Inject(method = "getSupplementariesSafeAccess", at = @At("HEAD"), cancellable = true)
    private static void fastergetSupplementariesSafeAccess(InventoryUtil.ItemAccess containerAccess, CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir){
        if (Fastitemaccess.doesntMatch(containerAccess,"supplementaries:safe")){
            cir.setReturnValue(new ArrayList<>());
        }
    }

    @Inject(method = "getSatchelItemAccess", at = @At("HEAD"), cancellable = true)
    private static void fastergetSatchelItemAccess(InventoryUtil.ItemAccess containerAccess, CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir){
        if (Fastitemaccess.doesntMatch(containerAccess,"thermal:satchel")){
            cir.setReturnValue(new ArrayList<>());
        }
    }

    @Inject(method = "getBundleItemAccess", at = @At("HEAD"), cancellable = true)
    private static void fastergetBundleItemAccess(InventoryUtil.ItemAccess containerAccess, CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir){
        if (Fastitemaccess.doesntMatch(containerAccess, Items.BUNDLE)){
            cir.setReturnValue(new ArrayList<>());
        }
    }

    @Inject(method = "getKeyringItemAccess", at = @At("HEAD"), cancellable = true)
    private static void fastergetKeyringItemAccess(InventoryUtil.ItemAccess containerAccess, CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir){
        if (Fastitemaccess.doesntMatch(containerAccess, ModItems.TREAUSURE_KEYRING)){
            cir.setReturnValue(new ArrayList<>());
        }
    }

    @Inject(method = "getCoinPouchItemAccess", at = @At("HEAD"), cancellable = true)
    private static void fastergetCoinPouchItemAccess(InventoryUtil.ItemAccess containerAccess, CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir){
        if (Fastitemaccess.doesntMatch(containerAccess, ModItems.COIN_POUCH)){
            cir.setReturnValue(new ArrayList<>());
        }
    }

    @Inject(method = "getShulkerBoxAccess", at = @At("HEAD"), cancellable = true)
    private static void fastergetShulkerBoxAccess(InventoryUtil.ItemAccess containerAccess, CallbackInfoReturnable<List<InventoryUtil.ItemAccess>> cir){
        if (containerAccess instanceof ExtendedItemAccess eia && !(isShulkerBox(eia.fastitemaccess$getItem()))){
            cir.setReturnValue(new ArrayList<>());
        }
    }
}
