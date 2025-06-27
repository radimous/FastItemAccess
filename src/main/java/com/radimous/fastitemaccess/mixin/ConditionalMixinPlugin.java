package com.radimous.fastitemaccess.mixin;

import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;

import java.util.List;
import java.util.Set;

// used to disable lightmancurrency compat when it's not loaded
public class ConditionalMixinPlugin extends RestrictiveMixinConfigPlugin {
    @Override public String getRefMapperConfig() {
        return null;
    }

    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override public List<String> getMixins() {
        return null;
    }
}
