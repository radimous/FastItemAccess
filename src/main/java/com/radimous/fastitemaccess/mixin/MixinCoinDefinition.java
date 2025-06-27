package com.radimous.fastitemaccess.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.radimous.fastitemaccess.ExtendedItemAccess;
import iskallia.vault.util.CoinDefinition;
import iskallia.vault.util.InventoryUtil;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CoinDefinition.class, remap = false)
public class MixinCoinDefinition {
    // I would just do
    // if (coinDefinition.coinItem != eia.fastitemaccess$getItem()) continue;
    @WrapOperation(method = "deductCoins", at = @At(value = "INVOKE", target = "Liskallia/vault/util/InventoryUtil$ItemAccess;getStack()Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack deductSkipNonCoin(InventoryUtil.ItemAccess access, Operation<ItemStack> original,
                                @Local(argsOnly = true) CoinDefinition coinDefinition) {
        if (access instanceof ExtendedItemAccess eia) {
            if (coinDefinition.coinItem != eia.fastitemaccess$getItem()) {
                return null;
            }
        }
        return original.call(access);
    }

    // continue would be better here as well
    @WrapOperation(method = "lambda$hasEnoughCurrency$0", at = @At(value = "INVOKE", target = "Liskallia/vault/util/InventoryUtil$ItemAccess;getStack()Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack hasEnoughSkipNonCoin(InventoryUtil.ItemAccess access, Operation<ItemStack> original,
                                               @Local(argsOnly = true) CoinDefinition priceCoinDefinition) {
        if (access instanceof ExtendedItemAccess eia) {
            if (priceCoinDefinition.coinItem != eia.fastitemaccess$getItem()) {
                return null;
            }
        }
        return original.call(access);
    }
}
