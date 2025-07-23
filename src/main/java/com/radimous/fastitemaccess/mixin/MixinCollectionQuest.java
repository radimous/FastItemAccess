package com.radimous.fastitemaccess.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.quest.type.CollectionQuest;
import iskallia.vault.util.InventoryUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/*
 reverse filter and map order to avoid expensive getStack call
 FROM
 InventoryUtil.findAllItems(sPlayer).stream().map(InventoryUtil.ItemAccess::getStack).filter((stack) -> itemQuestMap.containsKey(stack.getItem())).forEach((stack) -> {

 TO
 InventoryUtil.findAllItems(sPlayer).stream().filter(ia -> itemQuestMap.containsKey(ia.getItem())).map(InventoryUtil.ItemAccess::getStack).forEach((stack) -> {
 */
@Mixin(value = CollectionQuest.class, remap = false)
public class MixinCollectionQuest {
    @Redirect(method = "checkCollections", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;map(Ljava/util/function/Function;)Ljava/util/stream/Stream;", ordinal = 1))
    private static Stream<ItemStack> checkCollections(Stream<InventoryUtil.ItemAccess> instance, Function<? super InventoryUtil.ItemAccess, ItemStack> function,
                                                      @Local(ordinal = 0) Map<Item, List<CollectionQuest>> itemQuestMap){
        return instance
            .filter(ia -> itemQuestMap.containsKey(ia.getItem()))
            .map(InventoryUtil.ItemAccess::getStack);
    }

}
