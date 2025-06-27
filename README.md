the mod speeds up checks that iterate through your inventory

it adds reference to the item in addition to the itemstack to ItemAccess to perform quicker check without copying the stacks  

it is used for checking if you can get container access from item in [MixinInventoryUtil](https://github.com/radimous/FastItemAccess/blob/master/src/main/java/com/radimous/fastitemaccess/mixin/MixinInventoryUtil.java) and [MixinWoldInventoryUtil](https://github.com/radimous/FastItemAccess/blob/master/src/main/java/com/radimous/fastitemaccess/mixin/MixinWoldInventoryUtil.java) (Client & Server)  
speeding up legendary bounty check in [MixinClientBountyData](https://github.com/radimous/FastItemAccess/blob/master/src/main/java/com/radimous/fastitemaccess/mixin/MixinClientBountyData.java) (Client)  
filtering required items for the collection quest instead of checking all stacks (and copying them) in [MixinCollectionQuest](https://github.com/radimous/FastItemAccess/blob/master/src/main/java/com/radimous/fastitemaccess/mixin/MixinCollectionQuest.java) (Server)  
skipping non coin items when checking if player has enough coins and deducting coins in [MixinCoinDefinition](https://github.com/radimous/FastItemAccess/blob/master/src/main/java/com/radimous/fastitemaccess/mixin/MixinCoinDefinition.java) (Client & Server)  

in addition to the faster checking by using the item reference it also does this:  
restrict backpack capability checking to backpack items in [MixinIntegrationSB](https://github.com/radimous/FastItemAccess/blob/master/src/main/java/com/radimous/fastitemaccess/mixin/MixinIntegrationSB.java) (Client & Server)
searches the inventory for legendary bounty only when player is looking at the bounty block once per tick (instead of searching every tick regardless of visible bounty table) in [MixinClientBountyData](https://github.com/radimous/FastItemAccess/blob/master/src/main/java/com/radimous/fastitemaccess/mixin/MixinClientBountyData.java) (Client)  
