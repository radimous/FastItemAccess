package com.radimous.fastitemaccess.mixin;

import iskallia.vault.bounty.client.ClientBountyData;
import iskallia.vault.init.ModItems;
import iskallia.vault.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Only calculate lost bounty info if someone asks for it.
 * It will now only run when you're looking at the bounty table instead of hurting perf in vault.
 *
 * Also uses the item check instead of itemstack check to be faster
 */
@Mixin(value = ClientBountyData.class, remap = false)
public class MixinClientBountyData {
    @Shadow private static boolean hasLostBountyInInventory;
    @Unique private static long fastitemaccess$lastLostBountyTick = -1;


    // require = 0 for unobtanium, which has the same injection
    @Redirect(method = "onClientTick", at = @At(value = "INVOKE", target = "Liskallia/vault/util/InventoryUtil;findAllItems(Lnet/minecraft/world/entity/player/Player;)Ljava/util/List;"), require = 0)
    private static List<InventoryUtil.ItemAccess> onClientTick(Player inventoryFn) {
        return List.of();
    }


    @Inject(method = "hasLostBountyInInventory", at = @At("HEAD"))
    private static void hasLostBountyInInventory(CallbackInfoReturnable<Boolean> cir) {
        fastitemaccess$updateLostBounty();
    }


    @Unique private static void fastitemaccess$updateLostBounty() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (player.tickCount != fastitemaccess$lastLostBountyTick) {
            fastitemaccess$lastLostBountyTick = player.tickCount;
            hasLostBountyInInventory = InventoryUtil.findAllItems(player).stream().anyMatch(ia -> ia.getItem() == ModItems.LOST_BOUNTY);
        }
    }
}