package com.azoraqua.challenge;

import com.azoraqua.challenge.module.*;
import com.azoraqua.challenge.module.Module;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class ChallengePlugin extends JavaPlugin {
    private final Module[] modules = new Module[]{
            new RecipeModule(),
            new TorchModule(),
            new BowModule(),
            new MotdModule(),
            new ChatModule(),
            new TabModule()
    };

    @Override
    public void onEnable() {
        super.saveDefaultConfig();

        Arrays.stream(modules).filter(Module::isEnabled).forEach(m -> m.initialize(this));
    }
}
