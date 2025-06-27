package com.radimous.fastitemaccess;

import com.mojang.logging.LogUtils;
import iskallia.vault.util.InventoryUtil;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod("fastitemaccess")
public class Fastitemaccess {

    public static final Logger LOGGER = LogUtils.getLogger();
    public Fastitemaccess() {
    }

    /**
     * We are certain that the container doesn't match, so we can return early
     * */
    public static boolean doesntMatch(InventoryUtil.ItemAccess containerAccess, String wantedId){
        if (containerAccess instanceof ExtendedItemAccess eia){
            // this would be so nice in kotlin
            var item = eia.fastitemaccess$getItem();
            if (item == null){
                return false;
            }
            var regName = item.getRegistryName();
            if (regName == null){
                return false;
            }
            return !regName.toString().equals(wantedId); // we got the rl and it was different -> return early
        }
        return false;
    }

    public static boolean doesntMatch(InventoryUtil.ItemAccess containerAccess, Item wantedItem){
        if (containerAccess instanceof ExtendedItemAccess eia){
            var item = eia.fastitemaccess$getItem();
            return item != null && item != wantedItem;
        }
        return false;
    }
}
